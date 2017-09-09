package софия.ту;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Нулира на съществуващите график конфигурации за да може Спринг STOMP график
 * да рабити коректно.
 * 
 * @author Иван Колев
 *
 */
@Configuration
@EnableScheduling
public class ЧатГрафикКонфигуратор implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar регистър) {
	регистър.setTaskScheduler(new ThreadPoolTaskScheduler());
    }
}
