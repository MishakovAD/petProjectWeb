import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Для запуска приложения без использования tomcat (в Spring втроенный)
 * используем данный класс.
 * ВАЖНО: ComponentScan использовать вместе с пакетом, в противном случае он ищет слишком много и не поднимается.
 */
@EnableAutoConfiguration
@ComponentScan("com.pakage")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
