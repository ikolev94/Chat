package софия.ту;

import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import софия.ту.сървис.СървисАктивниПотребители;

/**
 * Осигурява обновяване на информация за това кои потребители са активни в
 * момента.
 * 
 * @author Иван Колев
 *
 */
public class ТърсачАктивниПотребители {

    private SimpMessagingTemplate шаблон;
    private СървисАктивниПотребители сървисАктивниПотребители;

    public ТърсачАктивниПотребители(SimpMessagingTemplate шаблон, СървисАктивниПотребители сървисАктивниПотребители) {
	this.шаблон = шаблон;
	this.сървисАктивниПотребители = сървисАктивниПотребители;
    }

    /**
     * Грижи се списъка с активни потребители да се обновява на всеки две секунди.
     */
    @Scheduled(fixedDelay = 2000)
    public void pingUsers() {
	Set<String> активниПотребители = сървисАктивниПотребители.вземиАктивнитеПотребители();
	шаблон.convertAndSend("/topic/active", активниПотребители);
    }

}
