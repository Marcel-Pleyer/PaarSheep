package de.hdm.Gruppe4.Paarsheep.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.Gruppe4.Paarsheep.shared.LoginService;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;

/**
 * Implementierungsklasse des Interfaces LoginService
 * @see LoginService
 * @see LoginServiceAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {

	/**
	 * Diese Methode fuert den Login aus und ruft die Daten von der Google
	 * Accounts API ab.
	 * @param requestUri 
	 * @return loginInfos
	 */
  public Nutzerprofil login(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    Nutzerprofil loginInfo = new Nutzerprofil();

    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());
      loginInfo.setNickname(user.getNickname());
      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
    }
    return loginInfo;
  }

}
