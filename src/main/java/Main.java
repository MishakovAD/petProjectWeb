import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//закомментировано. для того. чтобы разбить пакет на отдельные приложения. а не запускать сразу все.
// Для запуска одного целого - раскомментировать и закомментить "стартеры" отдельных приложений
//Данный класс запускает абсолютно все проекты. которые находятся в com.project
/**
 * Для запуска приложения без использования tomcat (в Spring втроенный)
 * используем данный класс.
 */
//@SpringBootApplication
//@ComponentScan({"com.project", "com.project.CinemaTickets"})
//@ComponentScan(basePackages = {"com.project"})
public class Main extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Main.class);
//    }
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
//    }
}
