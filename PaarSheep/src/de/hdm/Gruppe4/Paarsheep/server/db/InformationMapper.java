package de.hdm.Gruppe4.Paarsheep.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.Gruppe4.Paarsheep.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Information</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * Die Klasse InformationMapper wird nur einmal instantiiert. Man spricht hierbei
 * von einem sogenannten <b>Singleton</b>.
 * <p>
 * 
 * 
 * @author Thies
 * @author Hauler
 * @author Dang
 */

public class InformationMapper {
	/**
	   * 
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	  private static InformationMapper informationMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected InformationMapper() {
	}

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>InformationMapper.informationMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>InformationMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> InformationMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return informationMapper <code>InformationMapper</code>-Objekt.
	   * @see informationMapper
	   */
	  public static InformationMapper informationMapper() {
	    if (informationMapper == null) {
	    	informationMapper = new InformationMapper();
	    }
	    return informationMapper;
	  }
	 
	  /**
	   * Einfügen eines <code>Information</code>-Objekts in die Datenbank. Dabei wird
	   * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	   * berichtigt.
	   * 
	   * @param information das zu speichernde Objekt
	   * @param con Datenbankverbindung
	   * @param stmt Statement
	   * @param ProfilID Profil ID des Users
	   * @param EigenschaftID ID der Eigenschaft
	   * @param Information Beschreibung der Eigenschaft
	   * @return information das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	   *         <code>id</code>.
	   */
	  public Information insertInformation(Information information, int ProfilID, int EigenschaftID, String Information) {
	    Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(InformationID) AS maxid " + "FROM Information ");
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * a erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				information.setID(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Information (InformationID, ProfilID, EigenschaftID, Information) "
						+ "VALUES (" + information.getID() + "," + ProfilID + "," + EigenschaftID + ",'" + Information
						+ "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Rückgabe der Information
		return information;
	}

	  /**
	   * Infoeigenschaften koennen bearbeitet werden bzw. in DB aktualisiert/geaendert werden
	 * @param info Infoeigenschaft
	 * @param con Datenbankverbindung
	 * @param profilID ID des Userprofils
	 * @param eigenschaftID ID der Eigenschaft
	 */
	public void bearbeiteNutzerprofilInfo(String info, int profilID, int eigenschaftID) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Dieses Statement übergibt die Werte an die Tabelle Profil
			stmt.executeUpdate(
					"UPDATE Information SET  Information.Information = '" + info + "' WHERE Information.ProfilID = "
							+ profilID + " AND Information.EigenschaftID =  " + eigenschaftID);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	 
	  /**
	   * Löschen der Daten eines <code>Information</code>-Objekts aus der Datenbank.
	   * @param profilID ID des Profils
	   * @param con Datenbankverbindung
	   * @param stmt Statement
	   * @param information das aus der DB zu löschende "Objekt"
	   */
	  public void deleteAllNutzerInfo(int profilID) {
	    Connection con = DBConnection.connection();
	    try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Information WHERE Information.ProfilID =" + profilID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	  /**
	   * Auslesen aller Informationen eines durch Fremdschlüssel (ProfilID) gegebenen
	   * Profils.
	   * @param profilID 
	   * @param ProfilID Schlüssel des zugehörigen Profils.
	   * @param con Datenbankverbindung
	   * @param result ArrayList, in der die Abfrage gespeichert wird
	   * @return result Eine ArrayList mit Information-Objekten, die sämtliche Information des
	   *         betreffenden Profils repräsentieren. Bei evtl. Exceptions wird ein
	   *         partiell gefüllter oder ggf. auch leerer ArrayList zurückgeliefert.
	   */
	  public ArrayList<Information> findInfoByProfil(int profilID) {
	    Connection con = DBConnection.connection();
	    ArrayList<Information> result = new ArrayList<Information>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT Information.Information, Information.EigenschaftID "
	    		   + "FROM Information INNER JOIN Eigenschaft "
	    		   + "ON Information.EigenschaftID = Eigenschaft.EigenschaftID "
	               + "And Information.ProfilID =" + profilID
	               + " AND Eigenschaft.Eigenschaftstyp NOT LIKE 'o'");
	      

	      // Für jeden Eintrag im Suchergebnis wird nun ein Informations-Objekt erstellt.
	      while (rs.next()) {
	        Information information = new Information();
	        information.setInformation(rs.getString("Information"));
	        information.setID(rs.getInt("Information.EigenschaftID"));
	        

	        // Hinzufügen des neuen Objekts zum Array
	        result.add(information);
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Ergebnisvektor zurückgeben
	    return result;
	  }
	  
	  /**
	   * Gibt Erlaeuterungen von Auswahloption an.
	 * @param profilID ID des Userprofils
	 * @param stmt Statement
	 * @return result Ergebnisvektor
	 */
	public ArrayList<Information> findAuswahlInfoByProfil(int profilID) {
		Connection con = DBConnection.connection();
		ArrayList<Information> result = new ArrayList<Information>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Information " + "FROM Information INNER JOIN Eigenschaft "
					+ "ON Information.EigenschaftID = Eigenschaft.EigenschaftID " + "AND Information.ProfilID ="
					+ profilID + " AND Eigenschaft.Eigenschaftstyp = 'o'");

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Informations-Objekt erstellt.
			while (rs.next()) {
				Information information = new Information();
				information.setInformation(rs.getString("Information"));

				// Hinzufügen des neuen Objekts zum Array
				result.add(information);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Alle Erlaeuterungen von Infos ausgeben.
	 * @param con Datenbankverbindung
	 * @param result ArrayList mit Resultaten aus DB-Abfrage zurueckgeben.
	 * @param profilID ID des Userprofils
	 * @return ArrayList<Information>
	 */
	public ArrayList<Information> findAllInfoByProfil(int profilID) {
		Connection con = DBConnection.connection();
		ArrayList<Information> result = new ArrayList<Information>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT Information, Information.EigenschaftID " + "FROM Information inner join Eigenschaft "
							+ "ON Information.EigenschaftID = Eigenschaft.EigenschaftID " + "AND Information.ProfilID ="
							+ profilID);

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Informations-Objekt erstellt.
			while (rs.next()) {
				Information information = new Information();
				information.setInformation(rs.getString("Information"));
				information.setID(rs.getInt("Information.EigenschaftID"));

				// Hinzufügen des neuen Objekts zum Array
				result.add(information);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * @param profilID
	 * @param eigID
	 */
	public void deleteNutzerInfo(int profilID, int eigID) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Information WHERE Information.ProfilID =" + profilID
					+ "AND Information.EigenschaftID = " + eigID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}