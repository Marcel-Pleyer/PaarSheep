package de.hdm.Gruppe4.Paarsheep.shared.bo;

import java.sql.Date;

public class Nutzerprofil extends Profil {

	private static final long serialVersionUID = 1L;
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	
	 /**
	   * Fremdschluesselbeziehung zum Inhaber des Nutzerprofils.
	   */
	private int nutzerprofil_ProfilID;
	
	
	  /**
	   * Auslesen des Fremdschluessels zum Nutzerprofil Inhaber.
	   */
	  public int getNutzerprofil_ProfilID() {
	    return this.nutzerprofil_ProfilID;
	  }
	  
	  /**
	   * Setzen des Fremdschluessels zum Nutzerprofil Inhaber.
	   */
	  public void setNutzerprofil_ProfilID(int ProfilID) {
	    this.nutzerprofil_ProfilID = ProfilID;
	  }
	
	/**
	 *Attribut vorname vom Typ String wird angelegt.
	 *
	 * @author Dominik Sasse
	 */
	private String vorname;
	
	/**
	 *Attribut nachname vom Typ String wird angelegt.
	 *
	 * @author Dominik Sasse
	 */
	private String nachname;
	
	/**
	 *Attribut Geburtsdatum vom Typ Date wird angelegt.
	 *
	 * @author Dominik Sasse
	 */
	private Date geburtsdatum;
	
	/**
	 *Methode um das Attribut vorname abzurufen.
	 *
	 * @author Dominik Sasse
	 */
	public void setVorname(String vorname){
		this.vorname = vorname;
	}
	
	/**
	 *Methode um das Attribut vorname abzurufen.
	 *
	 * @author Dominik Sasse
	 */
	public String getVorname(){
		return this.vorname;
	}
	
	/**
	 *Methode um das Attribut nachname zu setzen.
	 *
	 * @author Dominik Sasse
	 */
	public void setNachname(String nachname){
		this.nachname = nachname;
	}
	
	/**
	 *Methode um das Attribut nachname abzurufen.
	 *
	 * @author Dominik Sasse
	 */
	public String getNachname(){
		return this.nachname;
	}
	
	/**
	 *Methode um das Attribut geburtsdatum zu setzen.
	 *
	 * @author Dominik Sasse
	 */
	public void setGeburtsdatum(Date geburtsdatum){
		this.geburtsdatum = geburtsdatum;
	}
	
	/**
	 *Methode um das Attribut geburtsdatum abzurufen.
	 *
	 * @author Dominik Sasse
	 */
	public Date getGeburtsdatum(){
		return this.geburtsdatum;
	}
	
	// Attribute von der Abstract Klasse Profil werden hier benutzt
	
	/**
	 * Attribut raucher vom Typ boolean wird angelegt. Entweder der Nutzer raucht (true), oder nicht (false)
	 * 
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	private boolean raucher;
	
	/**
	 *Attribut haarfarbe vom Typ String wird angelegt.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	private String haarfarbe = "";
	
	/**
	 *Attribut religion vom Typ String wird angelegt.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	private String religion = "";
	
	/**
	 *Attribut koerpergroesse vom Typ Integer wird angelegt.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	private int korpergroesse = 0;
	
	/**
	 *Attribut geschlecht vom Typ String wird angelegt.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	private String geschlecht = "";
	
	/**
	 *Methode um das Attribut raucher zu setzen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	
	public void setRaucher(boolean raucher){
		this.raucher = raucher;
	}
	
	/**
	 *Methode um das Attribut raucher abzurufen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public boolean getRaucher(){
		return true;
	}
	
	/**
	 *Methode um das Attribut haarfarbe zu setzen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public void setHaarfarbe(String haarfarbe){
		this.haarfarbe = haarfarbe;
	}
	
	/**
	 *Methode um das Attribut haarfarbe abzurufen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public String getHaarfarbe(){
		return this.haarfarbe;
	}
	
	/**
	 *Methode um das Attribut religion zu setzen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public void setReligion(String religion){
		this.religion = religion;
	}
	
	/**
	 *Methode um das Attribut religion abzurufen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public String getReligion(){
		return this.religion;
	}
	
	/**
	 *Methode um das Attribut koerpergroesse zu setzen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public void setKoerpergroesse(int koerpergroesse){
		this.korpergroesse = koerpergroesse;
	}
	
	/**
	 *Methode um das Attribut koerpergroesse abzurufen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public double getKoerpergroesse(){
		return this.korpergroesse;
	}
	
	/**
	 *Methode um das Attribut geschlecht zu setzen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public void setGeschlecht(String geschlecht){
		this.geschlecht = geschlecht;
	}
	
	/**
	 *Methode um das Attribut geschlecht abzurufen.
	 *
	 * @author An Dang
	 * @author Dominik Sasse
	 */
	public String getGeschlecht(){
		return this.geschlecht;
	}
	

	  /**
	   * Erzeugen einer einfachen textuellen Repr�sentation des jeweiligen Profils
	   * mit Profil-ID, Vorname und Nachname.
	   * 
	   * @author Thies
	   * @author Dominik Sasse
	   */
	  @Override
	public String toString() {
	    return super.toString() + " Profil-ID: #" + this.nutzerprofil_ProfilID + 
	    						" " + this.vorname + " " + this.nachname;
	  }

	  /**
	   * 
	   * Feststellen der <em>inhaltlichen</em> Gleichheit zweier Account-Objekte.
	   * Die Gleichheit wird in diesem Beispiel auf eine identische Nutzer-ID
	   * beschr�nkt.
	   * 
	   * @author Thies
	   * @author Dominik Sasse
	   * 
	   */
	  @Override
	public boolean equals(Object o) {
	    /*
	     * Abfragen, ob ein Objekt ungl. NULL ist und ob ein Objekt gecastet werden
	     * kann, sind immer wichtig!
	     */
	    if (o != null && o instanceof Nutzerprofil) {
	      Nutzerprofil n = (Nutzerprofil) o;
	      try {
	        return super.equals(n);
	      }
	      catch (IllegalArgumentException e) {
	        return false;
	      }
	    }
	    return false;
	  }
	

}


