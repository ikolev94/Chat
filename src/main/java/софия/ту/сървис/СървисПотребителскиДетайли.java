package софия.ту.сървис;

import софия.ту.модел.Потребител;
import софия.ту.модел.Роля;
import софия.ту.хранилище.ХранилищеПотребители;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class СървисПотребителскиДетайли implements UserDetailsService {
    @Autowired
    private ХранилищеПотребители хранилищеПотребители;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String потребителскоИме)
	    throws UsernameNotFoundException {
	Потребител потребител = хранилищеПотребители.търсиПоПотребителскоИме(потребителскоИме);

	Set<GrantedAuthority> поравомощия = new HashSet<>();
	for (Роля роля : потребител.вземиРоли()) {
	    поравомощия.add(new SimpleGrantedAuthority(роля.вземиИме()));
	}

	return new org.springframework.security.core.userdetails.User(
		потребител.вземиПотребителскотоИме(), потребител.вземиПарола(), поравомощия);
    }
}
