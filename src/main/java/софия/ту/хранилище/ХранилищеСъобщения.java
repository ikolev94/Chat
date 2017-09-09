package софия.ту.хранилище;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import софия.ту.модел.ЧатСъобщение;

/**
 * Предоставя методи, които могат да бъдат използвани за получаване и
 * модифициране на информация, сързана с съобщения. Която се съхранява в базата
 * данни.
 * 
 * @author Иван Колев
 *
 */
public interface ХранилищеСъобщения extends JpaRepository<ЧатСъобщение, Long> {

    /**
     * Открива всички съобщения в базата данни които са били изпратени между двама
     * потребителя, имената на които се подават като параметри.
     * 
     * @param потребител1
     * @param потребител2
     * @return Колекция от съобщения.
     */
    @Query(value = "SELECT * FROM chatmessages " + "WHERE recipient = ?1 AND sender = ?2 "
	    + "OR recipient = ?2 AND sender=?1", nativeQuery = true)
    List<ЧатСъобщение> findMessagesBetweenThoUsers(String потребител1, String потребител2);

}
