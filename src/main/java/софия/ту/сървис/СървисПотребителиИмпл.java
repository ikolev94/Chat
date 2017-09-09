package софия.ту.сървис;

import софия.ту.модел.Потребител;
import софия.ту.хранилище.ХранилищеПотребители;
import софия.ту.хранилище.ХранилищеРоли;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class СървисПотребителиИмпл implements СървисПотребители {
    @Autowired
    private ХранилищеПотребители хранилищеПотребители;
    @Autowired
    private ХранилищеРоли хранилищеРоли;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void запази(Потребител потребител) {
	потребител.запазиПарола(bCryptPasswordEncoder.encode(потребител.вземиПарола()));
	потребител.запазиРоли(new HashSet<>(хранилищеРоли.findAll()));
	хранилищеПотребители.save(потребител);
    }

    @Override
    public Потребител намериПоИме(String потребителскоИме) {
	return хранилищеПотребители.търсиПоПотребителскоИме(потребителскоИме);
    }
}
