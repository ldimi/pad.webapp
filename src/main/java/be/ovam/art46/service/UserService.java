package be.ovam.art46.service;

import be.ovam.art46.dao.UserDAO;
import be.ovam.art46.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserService extends BaseService {

    private static Log log = LogFactory.getLog(UserService.class);

	public User authenticateUser(String user_id, String password) {
		try {
			User user = getUserDao().findByUserId(user_id.trim().toLowerCase());
			// if (user.getPaswoord().trim().equals(password.toUpperCase())) {
			log.debug("Authentication successfull for user: " + user.getUser_id());
			return user;
			// }
			// log.debug("Incorrect password for user " + user.getUser_id() +
			// ": " + password);
		} catch (Exception e) {
			log.warn("Warn user not found: " + e.getMessage() + " with userId " + user_id);
		}
		return null;
	}

	private UserDAO getUserDao() {
		return (UserDAO) this.getDao();
	}

}
