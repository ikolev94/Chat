package софия.ту.модел;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Класът съдържа информацията за потребителска роля. Той е репрезентация на
 * таблица роля в базата данни.
 *
 * @author Иван Колев
 */
@Entity
@Table(name = "role")
public class Роля {

	/**
	 * Уникален идентификатор.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ид;

	/**
	 * Име на ролята.
	 */
	private String име;

	/**
	 * Множество от потребители които изпълняват ролята.
	 */
	private Set<Потребител> потребители;

	/**
	 * Връща ид на ролята.
	 * 
	 * @return ид
	 */
	public Long вземиИд() {
		return ид;
	}

	/**
	 * Запазва ид на ролята.
	 */
	public void запазиИд(Long ид) {
		this.ид = ид;
	}

	/**
	 * Връща името на ролята.
	 * 
	 * @return име
	 */
	public String вземиИме() {
		return име;
	}

	/**
	 * Запазва име на ролята.
	 */
	public void сложиИме(String име) {
		this.име = име;
	}

	/**
	 * Връща множество от потребители които изпълняват ролята.
	 * 
	 * @return потребители
	 */
	@ManyToMany(mappedBy = "roles")
	public Set<Потребител> вземиПотребители() {
		return потребители;
	}

	/**
	 * Запазва множество от потребители които изпълняват ролята.
	 */
	public void сложиПотребители(Set<Потребител> потребители) {
		this.потребители = потребители;
	}
}
