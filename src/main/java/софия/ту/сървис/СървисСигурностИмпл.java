package софия.ту.сървис;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class СървисСигурностИмпл implements СървисСигурност {
    @Autowired
    private AuthenticationManager автентикация;

    @Autowired
    private UserDetailsService сървисПотребителскиДетайли;

    private static final Logger logger = LoggerFactory.getLogger(СървисСигурностИмпл.class);

    @Override
    public String намериИметоНаТекущиятПотребител() {
	Object потребителскиДетайли = SecurityContextHolder.getContext()
		.getAuthentication()
		.getDetails();
	if (потребителскиДетайли instanceof UserDetails) {
	    return ((UserDetails) потребителскиДетайли).getUsername();
	}

	return null;
    }

    @Override
    public void автоЛогване(String потребителскоИме, String парола) {
	UserDetails потребителскиДетайли = сървисПотребителскиДетайли
		.loadUserByUsername(потребителскоИме);
	UsernamePasswordAuthenticationToken символ = new UsernamePasswordAuthenticationToken(
		потребителскиДетайли, парола, потребителскиДетайли.getAuthorities());

	автентикация.authenticate(символ);

	if (символ.isAuthenticated()) {
	    SecurityContextHolder.getContext().setAuthentication(символ);
	    logger.debug(String.format("Auto login %s successfully!", потребителскоИме));
	}
    }
}
