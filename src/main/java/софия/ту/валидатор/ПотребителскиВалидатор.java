package софия.ту.валидатор;

import софия.ту.модел.Потребител;
import софия.ту.сървис.СървисПотребители;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Предоставя валидация на данните които се въвеждат при регистрацията на нов
 * потребител.
 * 
 * @author Иван Колев
 *
 */
@Component
public class ПотребителскиВалидатор implements Validator {
    @Autowired
    private СървисПотребители сървисПотребители;

    @Override
    public boolean supports(Class<?> aClass) {
	return Потребител.class.equals(aClass);
    }

    /**
     * Позволява да се регистрират само потребители на които входните данни
     * отговарят на следните критерий:
     * 
     * Потребителското име да е с дължина между 3 и 32 символа.
     * 
     * Потребителското име да не е било регистрирано до сега.
     * 
     * Паролата да е с дължина между 4 и 32 символа.
     * 
     * Паролата да да съвпада с потвърдената парола.
     */
    @Override
    public void validate(Object o, Errors errors) {
	Потребител потребител = (Потребител) o;

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
	if (потребител.вземиПотребителскотоИме().length() < 3
		|| потребител.вземиПотребителскотоИме().length() > 32) {
	    errors.rejectValue("username", "Size.userForm.username");
	}
	if (сървисПотребители.намериПоИме(потребител.вземиПотребителскотоИме()) != null) {
	    errors.rejectValue("username", "Duplicate.userForm.username");
	}

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	if (потребител.вземиПарола().length() < 4 || потребител.вземиПарола().length() > 32) {
	    errors.rejectValue("password", "Size.userForm.password");
	}

	if (!потребител.вземиПотвърденаПарола().equals(потребител.вземиПарола())) {
	    errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	}
    }
}
