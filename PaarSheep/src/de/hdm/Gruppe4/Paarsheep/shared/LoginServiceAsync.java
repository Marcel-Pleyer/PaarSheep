package de.hdm.Gruppe4.Paarsheep.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;

/** Diese Klasse ist ein Interface */

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<Nutzerprofil> async);
}