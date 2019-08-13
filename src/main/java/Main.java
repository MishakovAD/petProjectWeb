import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Для запуска приложения без использования tomcat (в Spring втроенный)
 * используем данный класс.
 * ВАЖНО: ComponentScan использовать вместе с пакетом, в противном случае он ищет слишком много и не поднимается.
 */
@SpringBootApplication
@ComponentScan({"com.project", "com.project.CinemaTickets"})
public class Main extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    }
}
