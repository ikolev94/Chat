package софия.ту.уеб;

import софия.ту.валидатор.ПотребителскиВалидатор;
import софия.ту.модел.Потребител;
import софия.ту.сървис.СървисПотребители;
import софия.ту.сървис.СървисСигурност;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Приема и обработва заявки свързани с регистрация и вход на потребители.
 * 
 * @author Иван Колев
 *
 */
@Controller
public class ПотребителскиКонтролер {
    @Autowired
    private СървисПотребители сървисПотребители;

    @Autowired
    private СървисСигурност сървисСигурност;

    @Autowired
    private ПотребителскиВалидатор потребителскиВалидатор;

    /**
     * Изпълнява се след GET заявка към точката за регистриране на потребители и
     * връща уеб страница с формуляр който е необходимо да бъде попълнена за да се
     * регистрира нов потребител.
     * 
     * @param формуляр
     * @return HTML страница.
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String регистрация(Model формуляр) {
	формуляр.addAttribute("userForm", new Потребител());

	return "registration";
    }

    /**
     * Изпълнява се след POST заявка към точката за регистриране на потребители,
     * приема попълнен регистрационен формуляр и на негова база регистрира нов
     * потребител. След което препраща текущият потребител към самото чат приложение
     * при успешна регистрация или визоализира грешка при не усшешна.
     * 
     * @param формуляр
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") Потребител формуляр,
	    BindingResult bindingResult, Model model) {
	потребителскиВалидатор.validate(формуляр, bindingResult);

	if (bindingResult.hasErrors()) {
	    return "registration";
	}

	сървисПотребители.запази(формуляр);

	сървисСигурност
		.автоЛогване(формуляр.вземиПотребителскотоИме(), формуляр.вземиПотвърденаПарола());

	return "redirect:/index";
    }

    /**
     * Изпълнява се след GET заявка към точката за логване на потребители и връща
     * уеб страница с формуляр (съдържащт потребителско име и парола) който е
     * необходимо да бъде попълнена влезне даден потребител в приложението.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
	if (error != null)
	    model.addAttribute("error", "Your username and password is invalid.");

	if (logout != null)
	    model.addAttribute("message", "You have been logged out successfully.");

	return "login";
    }

    /**
     * Изпълнява се след GET заявка към точката за достъп на самото чат приложение и
     * връща уеб (HTML) страница, която предоставя възможност дадения потребител да
     * комуникира с други потребители, посредством текстови съобщения.
     */
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String welcome(Model model) {
	return "index";
    }
}
