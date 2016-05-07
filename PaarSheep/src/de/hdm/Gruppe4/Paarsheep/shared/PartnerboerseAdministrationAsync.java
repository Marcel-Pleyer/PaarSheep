package de.hdm.Gruppe4.Paarsheep.shared;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.Gruppe4.Paarsheep.shared.bo.Auswahl;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Auswahloption;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Beschreibung;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Eigenschaft;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Merkzettel;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Profil;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Sperrliste;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Suchprofil;


/**
 * 
 * PartnerboerseAdministrationAsync ist das Gegenst�ck zu PartnerboerseAdministration. 
 * Es l�sst sich automatisch durch das Google-Plugin erstellen und erg�nzen.
 * Dies macht eine weiter Dokumentation �berfl�ssig.


 * @author Dominik Sasse
 * 
 */


public interface PartnerboerseAdministrationAsync {

	void createAuswahloption(String optionsBezeichnung, AsyncCallback<Auswahloption> callback);

	/**
	 * Abstrakte Klasse
	 */
	//void createEigenschaft(String erlaeuterung, AsyncCallback<Eigenschaft> callback);

	void createNutzerprofil(String vorname, String nachname, Date geburtsdatum, 
							Boolean raucher, String haarfarbe, String religion, Integer koerpergroesse, String geschlecht, 
							AsyncCallback<Nutzerprofil> callback);

	/**
	 * Abstrakte Klasse
	 */
	//void createProfil(Boolean raucher, String haarfarbe, String religion, Integer koerpergroesse, String geschlecht,
	//		AsyncCallback<Profil> callback);

	void createSuchprofil(int altervon, int alterbis, int koerpergroessevon, int koerpergroessebis, AsyncCallback<Suchprofil> callback);

	void getAllProfils(AsyncCallback<ArrayList<Profil>> callback);

	void init(AsyncCallback<Void> callback);

	void createMerkzettel(int ID, AsyncCallback<Merkzettel> callback);

	void createSperrliste(int ID, AsyncCallback<Sperrliste> callback);

	void createBeschreibung(String beschreibung, AsyncCallback<Beschreibung> callback);

	void createAuswahl(Auswahloption a, AsyncCallback<Auswahl> callback);

	void saveNutzerprofil(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void saveSuchprofil(Suchprofil suchprofil, AsyncCallback<Void> callback);

}