package be.ovam.art46.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.naming.directory.DirContext;
import java.util.Collection;
import java.util.List;

@Component("loginAuthenticationProvider")
public class AuthenticationProvider extends
        AbstractUserDetailsAuthenticationProvider {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private LdapTemplate ldapTemplate;

    @Value("${ovam.security.isInTest}")
    private boolean isInTest;

    @Value("${ovam.security.usersPath}")
    private String basePath;

    @Value("${ovam.security.rollenPath}")
    private String basePathRollen;

    /**
     * Property.
     */
    private static final String SEARCHATTRIBUTE = "memberUid";

    /**
     * Property.
     */
    private static final String ROLEATTRIBUTE = "cn";

    private String getString(Object o) {
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> getAuthorities(String username) {
        final List<GrantedAuthority> result;
        result = ldapTemplate.search(basePathRollen, new EqualsFilter(
            SEARCHATTRIBUTE, username).encode(),
            new ParameterizedContextMapper<GrantedAuthority>() {
                public GrantedAuthority mapFromContext(final Object ctx) {
                    final DirContextAdapter adapter = (DirContextAdapter) ctx;
                    final String role = adapter.getStringAttribute(ROLEATTRIBUTE);

                    final String transformedRole =  "ROLE_"	+ ((role != null) ? role : "");
                    final GrantedAuthority result = new SimpleGrantedAuthority(transformedRole);
                    return result;
                }
            });
			//result.add(new SimpleGrantedAuthority("ROLE_USER"));

        return result;
    }

    private boolean ldapLookupPerson(String username) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supports(Class authenticationClass) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authenticationClass));
    }

    private boolean authenticate(String personUid, String credentials) {
        DirContext ctx = null;

        String dn = "uid=" + personUid + "," + basePath;

        try {
            ctx = ldapTemplate.getContextSource().getContext(dn, credentials);
            log.info(dn + " is ingelogd");
            return true;
        } catch (Exception e) {
            // Context creation failed - authentication did not succeed
            return false;
        } finally {
            LdapUtils.closeContext(ctx);
        }
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePathRollen() {
        return basePathRollen;
    }

    public void setBasePathRollen(String basePathRollen) {
        this.basePathRollen = basePathRollen;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
		// TODO Auto-generated method stub

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        String password = getString(authentication.getCredentials());

        boolean authenticated = false;
        if (isInTest) {
            authenticated = true;
        } else {
            authenticated = authenticate(username, password);
        }

        if (authenticated) {
            return new User(username, password, getAuthorities(username));
        } else {
            throw new UsernameNotFoundException("Bad credentials");
        }
    }

}
