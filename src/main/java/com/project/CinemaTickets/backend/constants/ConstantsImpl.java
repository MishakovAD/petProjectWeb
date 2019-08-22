package com.project.CinemaTickets.backend.constants;

import com.project.CinemaTickets.backend.config.ConfigBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

@Component
public class ConstantsImpl implements Constants {
    private Logger logger = LoggerFactory.getLogger(ConstantsImpl.class);

    public static boolean ruCaptchaEnable = true;
    public static  String userKey = "";
    public static  String loginDatabase = "";
    public static  String passwordDatabase = "";
    public static  String urlDatabase = "";

    private boolean ruCaptchaEnableProp;
    private String userKeyRuCaptcha;
    private String loginDB;
    private String passwordDB;
    private String urlDB;

    @Override
    @PostConstruct
    public void initPropertiesConst() {
        try {
            ruCaptchaEnableProp = configBackend.isRuCaptchaEnable();
            userKeyRuCaptcha = configBackend.getRuCaptchaUserKey();
            loginDB = configBackend.getDbUser();
            passwordDB = configBackend.getDbPassword();
            urlDB = configBackend.getDbUrl();
        } catch (IOException e) {
            logger.error("ERROR in initPropertiesConst", e);
        }
        ruCaptchaEnable = ruCaptchaEnableProp;
        userKey = userKeyRuCaptcha;
        loginDatabase = loginDB;
        passwordDatabase = passwordDB;
        urlDatabase = urlDB;
    }

    //-----------------------------------------------------------
    private ConfigBackend configBackend;

    @Inject
    public void setConfigBackend(ConfigBackend configBackend) {
        this.configBackend = configBackend;
    }
}
