package be.ovam.art46.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component(value = "userDetailsService")
public class UserDetailService implements AuthenticationUserDetailsService {

	private static final String SEARCHATTRIBUTE = "memberUid";
	private static final String ROLEATTRIBUTE = "cn";

	@Autowired
	private LdapTemplate ldapTemplate;

	@Value("${ovam.security.usersPath}")
	private String basePath;

	@Value("${ovam.security.rollenPath}")
	private String basePathRollen;
	
	@Autowired
	private AuthenticationProvider ldapAuthenticator;

    private static Logger LOGGER = Logger.getLogger(LdapTemplate.class.getName());

	//@Override
	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {

        System.out.println("pre auth met pre authentication token..." );
		Object principal = token.getPrincipal();
        // komt van de response van de headerAuthenticationFilter .getPreAuthenticatedPrincipal

		if (principal instanceof Map) { 
			Map principalMap = (Map) principal;
			String username = (String) principalMap.get("username");
            String rollen = (String) principalMap.get("rollen");

			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			if (username!=null) {
                if (rollen != null) {
                    String[] splitRollen = rollen.split("/");
                    for (String dn : splitRollen) {
                        if (dn.endsWith(basePathRollen) ) {
                            String[] splitdn = rollen.split(",");
                            String eersteDeel = splitdn[0];
                            String rol = eersteDeel.replaceAll("cn=", "");

                            authorities.add(new SimpleGrantedAuthority(rol));
                        }
                    }
                } else {
                    authorities = ldapAuthenticator.getAuthorities(username);
                }
                return new User(username, "", authorities);
			}
		}
		return null; 
	}
}
