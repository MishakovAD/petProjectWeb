package com.project.CinemaTickets.backend.ServerLogic.Worker;

import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ServerLogic.DAO.DAOHelperUtils.ConverterTo;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils.HibernateDao;
import com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.PliHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.project.CinemaTickets.backend.Utils.HelperUtils.createIdCinemas;

@Component
public class WorkerImpl extends Thread implements Worker {
    private Logger logger = LoggerFactory.getLogger(WorkerImpl.class);
    private boolean workerRunning = true;

    public void run() {
        logger.info("Start method run() at " + LocalDateTime.now());
        List<Cinema> cinemasList = new ArrayList<>();
        ArrayList<String> idCinemasList = createIdCinemas(); //TODO:сделать в дальнешем умный апдейт данного листа. То ест ьудалять айдишники, кинотеатров которых нет.
        Set<String> notValidIdSet = new HashSet<>(); //TODO: Добавить в БД Новую таблицу и туда засунуть все невалидные айди
        LocalDate[] dateArray = initWeekArray();
        while (isWorkerRunning()) {
            idCinemasList.forEach(kinopoiskId -> {
                Arrays.stream(dateArray).forEach(date -> {
                    Document document = new Document("");
                    String urlToKinopoisk;
                    urlToKinopoisk = "https://www.kinopoisk.ru/afisha/city/1/cinema/" + kinopoiskId
                            + "/day_view/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/";
                    try {
                        document = pliHttpClient.getDocumentFromInternet(urlToKinopoisk);
                    } catch (IOException ex) {
                        logger.info("ERROR in the method run() at " + LocalDateTime.now(), ex);
                    }

                    if (StringUtils.containsIgnoreCase(document.text(), "Файл не найден. Ошибка 404.")) {
                        notValidIdSet.add(kinopoiskId);
                    } else {
                        Cinema cinema = pliParserKinopoisk.getCinemaFromDocument(document);
                        cinema.setUrlToKinopoisk(urlToKinopoisk.substring(0, urlToKinopoisk.indexOf("/day_view")+1));
                        cinemasList.add(cinema);

                        List<CinemaMovieSession> cinemaMovieSessionList = converterTo.getCinemaMovieSessionListCinemasList(cinemasList);
                        hibernateDao.saveCinemaMovieSessionObj(cinemaMovieSessionList);

                        cinemasList.remove(cinema);
                    }
                });
            });
            //hibernateDao.saveNotValidSet(notValidIdSet) if not exist //Сделать метод. И потом уллучшить метод создания айдишников кинотеатров.
        }
        logger.info("End of method getAllCinemasFromKinopoisk() at " + LocalDateTime.now());
    }

    public String start(StringBuilder capthaDocument) {
        StringBuilder url = new StringBuilder("https://www.kinopoisk.ru/checkcaptcha?key=");
        Document captchaDoc = Jsoup.parse(capthaDocument.toString());
        String key = captchaDoc.getElementsByAttributeValue("name", "key")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A");
        String retpath = captchaDoc.getElementsByAttributeValue("name", "retpath")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A")
                .replaceAll("\\?", "%3F");
        String srcImg = captchaDoc.getElementsByAttributeValue("class", "image form__captcha")
                .attr("src");
        System.out.println(srcImg);
        url.append(key).append("&retpath=").append(retpath).append("&rep=");
        String answer = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = URLEncoder.encode(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        url.append(answer);
        return url.toString();
    }

    public boolean isWorkerRunning() {
        return workerRunning;
    }

    public void setWorkerRunning(boolean workerRunning) {
        this.workerRunning = workerRunning;
    }

    private LocalDate[] initWeekArray() {
        LocalDate[] dateArray = new LocalDate[7];
        for (int i = 0; i < 7; i++) {
            dateArray[i] = LocalDate.now().plusDays(i);
        }
        return dateArray;
    }


    //------------------Injection part----------------------
    private PliHttpClient pliHttpClient;
    private PliParserKinopoisk pliParserKinopoisk;
    private ConverterTo converterTo;
    private HibernateDao hibernateDao;

    @Inject
    public void setPliParserKinopoisk (PliParserKinopoisk pliParserKinopoisk1) {
        this.pliParserKinopoisk = pliParserKinopoisk1;
    }

    @Inject
    public void setConverterTo(ConverterTo converterTo) {
        this.converterTo = converterTo;
    }

    @Inject
    public void setHibernateDao (HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }

    @Inject
    public void setPliHttpClient(PliHttpClient pliHttpClient) {
        this.pliHttpClient = pliHttpClient;
    }

    public static void main(String[] args) {
        String str = "https://www.kinopoisk.ru/afisha/city/1/cinema/5/day_view/55555/";
        str.substring(0, str.indexOf("/day"));
        WorkerImpl w = new WorkerImpl();
        w.initWeekArray();
        StringBuilder sb = new StringBuilder("<!DOCTYPE html><html class=\"i-ua_js_no i-ua_css_standard\" lang=\"ru\"><head><meta charset=\"utf-8\"/><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/><title>КиноПоиск.ru</title><script>;(function(d,e,c,r){e=d.documentElement;c=\"className\";r=\"replace\";e[c]=e[c][r](\"i-ua_js_no\",\"i-ua_js_yes\");if(d.compatMode!=\"CSS1Compat\")e[c]=e[c][r](\"i-ua_css_standart\",\"i-ua_css_quirks\")})(document);;(function(d,e,c,n,w,v,f){e=d.documentElement;c=\"className\";n=\"createElementNS\";f=\"firstChild\";w=\"http://www.w3.org/2000/svg\";e[c]+=\" i-ua_svg_\"+(!!d[n]&&!!d[n](w,\"svg\").createSVGRect?\"yes\":\"no\");v=d.createElement(\"div\");v.innerHTML=\"<svg/>\";e[c]+=\" i-ua_inlinesvg_\"+((v[f]&&v[f].namespaceURI)==w?\"yes\":\"no\");})(document);</script><script src=\"captcha.error-counter.js\"></script><!--[if gt IE 8]><!--><link rel=\"stylesheet\" href=\"captcha.min.css\"/><!--<![endif]--><!--[if IE 8]><link rel=\"stylesheet\" href=\"captcha.ie.min.css\"/><![endif]--></head><body class=\"b-page b-page_type_kinopoisk b-page__body i-ua i-global i-bem\" data-bem='{\"i-ua\":{},\"i-global\":{\"lang\":\"ru\",\"tld\":\"ru\",\"content-region\":\"ru\",\"click-host\":\"//clck.yandex.ru\",\"passport-host\":\"https://passport.yandex.ru\",\"pass-host\":\"https://pass.yandex.ru\",\"social-host\":\"https://social.yandex.ru\",\"export-host\":\"https://export.yandex.ru\",\"login\":\"\",\"lego-static-host\":\"https://yastatic.net/lego/2.10-142\"}}'><div class=\"i-expander__gap\"></div><div class=\"i-expander__content\"><div class=\"island island_type_fly\"><div class=\"logo-wrapper\"><a class=\"logo logo_type_link logo_name_kinopoisk\" href=\"https://www.kinopoisk.ru\"></a></div><div class=\"content\"><h1 class=\"title\"></h1><div class=\"text\"><p>Если вы&nbsp;видите эту страницу, значит с&nbsp;вашего IP-адреса поступило необычно много запросов. Система защиты от&nbsp;роботов решила, что c&nbsp;данного IP&nbsp;запросы отправляются автоматически, и&nbsp;ограничила доступ.</p><p>Чтобы&nbsp;продолжить, пожалуйста, введите символы с&nbsp;картинки в&nbsp;поле ввода и&nbsp;нажмите &laquo;Отправить&raquo;.</p><p class=\"b-default\"><i class=\"icon icon_alert_yes\" aria-hidden=\"true\"></i></p></div><div class=\"form form_state_image form_error_no form_help_yes form_audio_yes i-bem\" data-bem='{\"form\":{\"captchaSound\":\"https://www.kinopoisk.ru/captcha/voice?aHR0cHM6Ly9leHQuY2FwdGNoYS55YW5kZXgubmV0L3ZvaWNlP2tleT0wMDFidFdBN0k3WXd1VjBFV2tETzNmMUZRV1hTa1JGUA,,_0/1565935286/d41d8cd98f00b204e9800998ecf8427e_d4d9a8d40a937cf7704cfe3da40b50c7\",\"introSound\":\"https://www.kinopoisk.ru/captcha/voiceintro?aHR0cHM6Ly9leHQuY2FwdGNoYS55YW5kZXgubmV0L3N0YXRpYy9pbnRyby1ydS5tcDM,_0/1565935286/d41d8cd98f00b204e9800998ecf8427e_5acc4dbcfc9108422963025c2a3d549a\",\"buttonPlay\":\"Произнести\",\"buttonPlaying\":\"Воспроизводится\",\"messageError\":\"Неверно, попробуйте ещё раз.\",\"messageHelp\":\"Используйте строчные и&nbsp;прописные буквы, знаки препинания. Между словами поставьте пробел.\"}}'><form class=\"form__inner\" method=\"get\" action=\"/checkcaptcha\" onsubmit=\"ym(10630330, 'reachGoal', 'enter_captcha_value', { 'req_id': '' }); return true;\"><input class=\"form__key\" type=\"hidden\" name=\"key\" value=\"001btWA7I7YwuV0EWkDO3f1FQWXSkRFP_0/1565935286/d41d8cd98f00b204e9800998ecf8427e_0438cd7a4bdba0b2aeeacf412025a665\"/><input class=\"form__retpath\" type=\"hidden\" name=\"retpath\" value=\"https://www.kinopoisk.ru/afisha/city/1/cinema/280699/day_view/2019-08-18?_b6bba565e275269c6945796118251919\"/><div class=\"form__trigger\" title=\"Изображение &harr; Звук\" role=\"button\" tabindex=\"0\" aria-label=\"Изображение &harr; Звук\"></div><span class=\"link form__refresh i-bem\" title=\"Показать другую картинку\" tabindex=\"0\" role=\"button\" aria-label=\"Показать другую картинку\" data-bem='{\"link\":{}}'></span><img class=\"image form__captcha\" src=\"https://www.kinopoisk.ru/captchaimg?aHR0cHM6Ly9leHQuY2FwdGNoYS55YW5kZXgubmV0L2ltYWdlP2tleT0wMDFidFdBN0k3WXd1VjBFV2tETzNmMUZRV1hTa1JGUCZzZXJ2aWNlPWtpbm9wb2lzaw,,_0/1565935286/d41d8cd98f00b204e9800998ecf8427e_0e4e205b619f2826cff3364dfa096816\" alt=\"\" style=\"background: #cfcfcf;\"/><div class=\"form__audio\"><button class=\"button2 button2_size_m button2_theme_normal button2_action_play form__play i-bem\" type=\"button\" autocomplete=\"off\" tabindex=\"0\" data-bem='{\"button2\":{}}'><span class=\"button2__text\">Произнести</span></button></div><div class=\"form__arrow\">→</div><span class=\"input input_size_m input_autofocus_yes input_clear_no input_keyboard_yes input_theme_normal form__input i-bem\" data-bem='{\"input\":{\"live\":false}}'><span class=\"input__box\"><i class=\"icon icon_size_m icon_type_keyboard input__icon input__icon_side_right\" aria-hidden=\"true\"></i><input class=\"input__control\" id=\"rep\" name=\"rep\" placeholder=\"\" aria-labelledby=\"labeluniq15276162116831 hintuniq15276162116831\" autocorrect=\"off\" spellcheck=\"false\" autocomplete=\"off\"/></span></span><button class=\"button2 button2_size_m button2_theme_normal button2_type_submit button2_pin_clear-round form__submit i-bem\" type=\"submit\" autocomplete=\"off\" tabindex=\"0\" data-bem='{\"button2\":{}}'><span class=\"button2__text\">Отправить</span></button></form></div></div><div class=\"why\" style=\"\"><h2 class=\"why__title\" style=\"display: none\"></h2><p>Если у&nbsp;вас возникли проблемы или вы&nbsp;хотите задать вопрос нашей службе поддержки, пожалуйста, воспользуйтесь <a class=\"link\" href=\"//yandex.ru/support/captcha/\" onclick=\"ym(10630330, 'reachGoal', 'support_complain', { 'req_id': '' }); return true;\">формой обратной связи</a>.</p></div><div class=\"note\" style=\"display: none\"><p></p></div></div></div><div class=\"keyboard-wrapper keyboard-wrapper_visible_no i-bem\" data-bem='{\"keyboard-wrapper\":{}}'><ul class=\"keyboard i-bem\" data-bem='{\"keyboard\":{\"lang\":\"ru\",\"target\":\"rep\"}}'><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">ё</span></li><li class=\"keyboard__button keyboard__button_type_symbol\">1</li><li class=\"keyboard__button keyboard__button_type_symbol\">2</li><li class=\"keyboard__button keyboard__button_type_symbol\">3</li><li class=\"keyboard__button keyboard__button_type_symbol\">4</li><li class=\"keyboard__button keyboard__button_type_symbol\">5</li><li class=\"keyboard__button keyboard__button_type_symbol\">6</li><li class=\"keyboard__button keyboard__button_type_symbol\">7</li><li class=\"keyboard__button keyboard__button_type_symbol\">8</li><li class=\"keyboard__button keyboard__button_type_symbol\">9</li><li class=\"keyboard__button keyboard__button_type_symbol\">0</li><li class=\"keyboard__button keyboard__button_type_symbol\"></li><li class=\"keyboard__button keyboard__button_type_symbol\"></li><li class=\"keyboard__button keyboard__button_type_delete keyboard__button_last_yes\">&larr;</li><li class=\"keyboard__button keyboard__button_type_tab\"><i class=\"icon keyboard__tab\" aria-title=\"tab\"></i></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">q</span><span class=\"keyboard__ru\">й</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">w</span><span class=\"keyboard__ru\">ц</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">e</span><span class=\"keyboard__ru\">у</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">r</span><span class=\"keyboard__ru\">к</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">t</span><span class=\"keyboard__ru\">е</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">y</span><span class=\"keyboard__ru\">н</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">u</span><span class=\"keyboard__ru\">г</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">i</span><span class=\"keyboard__ru\">ш</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">o</span><span class=\"keyboard__ru\">щ</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">p</span><span class=\"keyboard__ru\">з</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">х</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">ъ</span></li><li class=\"keyboard__button keyboard__button_type_symbol keyboard__button_last_yes\"></li><li class=\"keyboard__button keyboard__button_type_capslock\"><i class=\"icon keyboard__capslock\" aria-title=\"capslock\"></i></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">a</span><span class=\"keyboard__ru\">ф</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">s</span><span class=\"keyboard__ru\">ы</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">d</span><span class=\"keyboard__ru\">в</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">f</span><span class=\"keyboard__ru\">а</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">g</span><span class=\"keyboard__ru\">п</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">h</span><span class=\"keyboard__ru\">р</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">j</span><span class=\"keyboard__ru\">о</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">k</span><span class=\"keyboard__ru\">л</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">l</span><span class=\"keyboard__ru\">д</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">ж</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">э</span></li><li class=\"keyboard__button keyboard__button_type_return keyboard__button_last_yes\"><i class=\"icon keyboard__return\" aria-title=\"return\"></i></li><li class=\"keyboard__button keyboard__button_type_lshift\"><i class=\"icon keyboard__shift\" aria-title=\"shift\"></i></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">z</span><span class=\"keyboard__ru\">я</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">x</span><span class=\"keyboard__ru\">ч</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">c</span><span class=\"keyboard__ru\">с</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">v</span><span class=\"keyboard__ru\">м</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">b</span><span class=\"keyboard__ru\">и</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">n</span><span class=\"keyboard__ru\">т</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\">m</span><span class=\"keyboard__ru\">ь</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">б</span></li><li class=\"keyboard__button keyboard__button_type_letter\"><span class=\"keyboard__en\"></span><span class=\"keyboard__ru\">ю</span></li><li class=\"keyboard__button keyboard__button_type_symbol\"></li><li class=\"keyboard__button keyboard__button_type_rshift keyboard__button_last_yes\"><i class=\"icon keyboard__shift\" aria-title=\"shift\"></i></li><li class=\"keyboard__button keyboard__button_type_lang\"><span class=\"keyboard__en\"><i class=\"icon icon_lang_en\" aria-hidden=\"true\"></i>En</span><span class=\"keyboard__ru\"><i class=\"icon icon_lang_ru\" aria-hidden=\"true\"></i>Ру</span></li><li class=\"keyboard__button keyboard__button_type_space keyboard__button_last_yes\">&nbsp;</li></ul></div><div class=\"popup2 popup2_target_anchor popup2_theme_normal popup2_autoclosable_yes i-bem\" data-bem='{\"popup2\":{}}'><div class=\"popup2__tail\"></div><div class=\"popup2__content\"></div></div><!--[if IE 8]><script src=\"https://yastatic.net/es5-shims/0.0.2/es5-shims.min.js\"></script><script src=\"https://yastatic.net/jquery/1.12.3/jquery.min.js\"></script><![endif]--><!--[if gt IE 8]><!--><script src=\"https://yastatic.net/jquery/2.2.3/jquery.min.js\"></script><!--<![endif]--><script src=\"captcha.min.js\"></script><script src=\"https://yastatic.net/howler/2.0.0/howler.min.js\"></script><!-- Yandex.Metrika counter --><script type=\"text/javascript\" >   (function(m,e,t,r,i,k,a){m[i]=m[i]||function(){(m[i].a=m[i].a||[]).push(arguments)};   m[i].l=1*new Date();k=e.createElement(t),a=e.getElementsByTagName(t)[0],k.async=1,k.src=r,a.parentNode.insertBefore(k,a)})   (window, document, \"script\", \"https://mc.yandex.ru/metrika/tag.js\", \"ym\");   ym(10630330, \"init\", {        clickmap:true,        trackLinks:true,        accurateTrackBounce:true,        webvisor:true,        ut:\"noindex\",        params:{ \"service\": \"kinopoisk\", \"req_id\": \"\", \"ident_type\": \"1\" }   });</script><noscript><div><img src=\"https://mc.yandex.ru/watch/10630330?ut=noindex\" style=\"position:absolute; left:-9999px;\" alt=\"\" /></div></noscript><!-- /Yandex.Metrika counter --></body></html>");
        w.start(sb);
    }
}
