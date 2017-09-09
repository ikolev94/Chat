package софия.ту;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Уеб приложение предоставящо възможност за комуникация между потребителите му
 * чрез текстови съобщения в реално време.
 * 
 * @author Иван Колев
 * @version 1.0
 * @since 20.08.17
 * 
 */
@SpringBootApplication
public class УебПриложение extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(УебПриложение.class);
    }

    /**
     * Главен метод. Чрез него се стартира приложението. 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	SpringApplication.run(УебПриложение.class, args);
    }
}
