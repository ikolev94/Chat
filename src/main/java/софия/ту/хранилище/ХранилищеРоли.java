package софия.ту.хранилище;

import org.springframework.data.jpa.repository.JpaRepository;

import софия.ту.модел.Роля;

/**
 * Предоставя методи, които могат да бъдат използвани за извличане, добавяне, изтриване и
 * модифициране на информация, сързана с потребителските роли. Която се съхранява в базата
 * данни.
 * 
 * @author Иван Колев
 *
 */
public interface ХранилищеРоли extends JpaRepository<Роля, Long>{
}
