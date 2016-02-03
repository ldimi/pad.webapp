package be.ovam.art46.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//@Component("headerAuthenticationFilter")
public class HeaderAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private String principalRequestHeader = "SSO_USER";
	private String rollenRequestHeader = "SSO_ROLLEN";

	@Value("${ovam.security.reverseProxyIp}")
	private String reverseProxyIp;

	/**
	 * Configure a value in the applicationContext-security for local tests.
	 */
	private String testUserId = null;
	/**
	 * Configure whether a missing SSO header is an exception.
	 */
	private boolean exceptionIfHeaderMissing = false;

	/**
	 * Read and return header named by <tt>principalRequestHeader</tt> from
	 * Request
	 * 
	 * @throws PreAuthenticatedCredentialsNotFoundException
	 *             if the header is missing and
	 *             <tt>exceptionIfHeaderMissing</tt> is set to <tt>true</tt>.
	 */
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

		// TODO: check of request wel van juiste server komt ... remoteIP van
		// OPENAM anders null returnen...

		String principalString = request.getHeader(principalRequestHeader);
		String rollen = request.getHeader(rollenRequestHeader);
		String ipAddress = request.getRemoteAddr();
		Map<String, String> result = new HashMap<String, String>();

		Boolean reverseProxyIpNietLeeg = !reverseProxyIp.isEmpty();
		Boolean requestNietAfkomstigVanOpenAM = !ipAddress.equals(reverseProxyIp);

		if (principalString == null || (reverseProxyIpNietLeeg && requestNietAfkomstigVanOpenAM)) {
			if (exceptionIfHeaderMissing) {
				throw new PreAuthenticatedCredentialsNotFoundException(principalRequestHeader + " header not found in request.");
			} else {
				// belangrijk want anders gaat dit door naar de
				// authenticationUserDetailService ...
				// indien geen user header, skippen preauthentication
				return null;
			}
		}
		// also set it into the session, sometimes that's easier for jsp/faces
		// to get at..
		request.getSession().setAttribute("session_user", principalString);

		result.put("username", principalString);
		result.put("rollen", rollen);

		return result;
	}

	/**
	 * Credentials aren't applicable here for OAM WebGate SSO.
	 */
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "password_not_applicable";
	}

	public void setPrincipalRequestHeader(String principalRequestHeader) {
		this.principalRequestHeader = principalRequestHeader;
	}

	/**
	 * Exception if the principal header is missing. Default <tt>false</tt>
	 * 
	 * @param exceptionIfHeaderMissing
	 */
	public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
		this.exceptionIfHeaderMissing = exceptionIfHeaderMissing;
	}

}