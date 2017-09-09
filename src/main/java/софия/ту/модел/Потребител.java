package софия.ту.модел;

import javax.persistence.*;
import java.util.Set;

/**
 * Класът съдържа информацията за потребител. Той е репрезентация на таблица
 * потребител в базата данни.
 *
 * @author Иван Колев
 */
@Entity
@Table(name = "user")
public class Потребител {

    /**
     * Уникален идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ид;

    /**
     * Име на потребителя.
     */
    private String потребителскоИме;

    /**
     * Парала на потребителя.
     */
    private String парола;

    /**
     * Потвърждение на паролата.
     */
    private String потвърденаПарола;

    /**
     * Множество от роли които потребителя може да изпълнява.
     */
    private Set<Роля> роли;

    /**
     * Връща ид на потребителя.
     */
    public Long вземиИд() {
	return ид;
    }

    /**
     * Запазва ид на потребителя.
     * 
     * @param ид
     */
    public void запазиИд(Long ид) {
	this.ид = ид;
    }

    /**
     * Връша потребителското име.
     * 
     * @return потребителскоИме
     */
    public String вземиПотребителскотоИме() {
	return потребителскоИме;
    }

    /**
     * Запазва потребителското име.
     */
    public void запазиИме(String потребителскоИме) {
	this.потребителскоИме = потребителскоИме;
    }

    /**
     * Връща потребителската парола.
     * 
     * @return парола
     */
    public String вземиПарола() {
	return парола;
    }

    /**
     * Запазва парола на потребителя
     * 
     * @param парола
     */
    public void запазиПарола(String парола) {
	this.парола = парола;
    }

    /**
     * Връща потвърдената парола на потребителя
     * 
     * @return потвърденаПарола
     */
    @Transient
    public String вземиПотвърденаПарола() {
	return потвърденаПарола;
    }

    /**
     * Запазва потвърдената парола на потребителя
     * 
     * @param потвърденаПарола
     */
    public void запазиПотвърденаПарола(String потвърденаПарола) {
	this.потвърденаПарола = потвърденаПарола;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Роля> вземиРоли() {
	return роли;
    }

    public void запазиРоли(Set<Роля> роли) {
	this.роли = роли;
    }
}
