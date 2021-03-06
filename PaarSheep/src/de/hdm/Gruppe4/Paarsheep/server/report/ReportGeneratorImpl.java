package de.hdm.Gruppe4.Paarsheep.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.Gruppe4.Paarsheep.server.PartnerboerseAdministrationImpl;
import de.hdm.Gruppe4.Paarsheep.shared.PartnerboerseAdministration;
import de.hdm.Gruppe4.Paarsheep.shared.ReportGenerator;
import de.hdm.Gruppe4.Paarsheep.shared.bo.*;
import de.hdm.Gruppe4.Paarsheep.shared.report.*;

/**
 * Implementierung des ReportGenerator-Interface.
 * 
 * @see ReportGenerator
 *      ------------------------------------------------------------------------
 *      Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung
 *      gewuenscht, als Grundlage uebernommen und bei Notwendigkeit an die
 *      Beduerfnisse des IT-Projekts angepasst.
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	Logger log = Logger.getLogger("logger");

	/**
	 * Ein ReportGenerator benoeigt Zugriff auf die PartnerboerseAdministration,
	 * da diese die essentiellen Methoden fuer die Koexistenz von Datenobjekten
	 * bietet.
	 */
	private PartnerboerseAdministration partnerboerseAdministration = null;

	/**
	 * No-Argument-Konstruktor.
	 * 
	 * @throws IllegalArgumentException
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException {
		PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
		a.init();
		this.partnerboerseAdministration = a;
	}

	/**
	 * Zugehoerige PartnerboerseAdministration auslesen 
	 * 
	 * @return  partnerboerseAdministration.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration;
	}

	/**
	 * Die Methode soll dem Report ein Impressum hinzufuegen. Dazu wird zunaechst
	 * ein neuer CompositeParagraph angelegt, da das Impressum mehrzeilig sein soll.
	 * Danach werden belibige SimpleParagraph dem CompositeParagraph hinzugefuegt. Zum
	 * Schluss wird CompositeParagraph dem Report hinzugefuegt ueber setImprint.
	 * 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {

		CompositeParagraph imprint = new CompositeParagraph();

		imprint.addSubParagraph(new SimpleParagraph("PaarSheep"));

		r.setImprint(imprint);
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllInfosOfNutzerReport
	 * zurueckliefert. Der Report stellt alle Infos eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerprofil-Objekt
	 * @return InfoObjekteByNutzerReport Fertiges Report-Objekt vom Typ InfoObjekteByNutzerReport
	 * @throws IllegalArgumentException
	 */

	public InfoObjekteByNutzerReport createInfoObjekteByNutzerReport(Nutzerprofil np) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;
		InfoObjekteByNutzerReport result = new InfoObjekteByNutzerReport();
		result.setTitle(" ");

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von
		 * Nutzerprofil-Informationen.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Info-Tabelle an.
		 */
		Row headline = new Row();
		headline.addColumn(new Column("Erläuterung"));
		headline.addColumn(new Column("Info"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);

		/*
		 * Nun werden saemtliche Infos des Nutzerprofils ausgelesen
		 */
		Map<List<Beschreibung>, List<Information>> resultMap = this.partnerboerseAdministration
				.showProfilAllEigBeschreibung(np.getProfilID());

		Set<List<Beschreibung>> output = resultMap.keySet();
		for (List<Beschreibung> listB : output) {
			List<Information> listI = new ArrayList<Information>();
			listI = resultMap.get(listB);
			for (int e = 0; e < listI.size(); e++) {

				// Eine leere Zeile anlegen.
				Row infoRow = new Row();
				infoRow.addColumn(new Column(listB.get(e).getErlaeuterung()));
				infoRow.addColumn(new Column(listI.get(e).getInformation()));

				// und schliesslich die Zeile dem Report hinzufuegen.
				result.addRow(infoRow);
			}
		}

		/*
		 * Zum Schluss muss der fertige Report zurueckgeben werden.
		 */
		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ ProfilInfoByNutzerprofilReport
	 * zurueckliefert. Der Report stellt alle Profildaten eines Nutzerprofils
	 * dar.
	 * 
	 * @param np Nutzerorifil-Objekt
	 * @return ProfilInfoByNutzerprofilReport Fertiges Report-Objekt vom Typ ProfilInfoByNutzerprofilReport
	 * @throws IllegalArgumentException
	 */
	public ProfilInfoByNutzerprofilReport createProfilInfoByNutzerprofilReport(Nutzerprofil np, int aehnlichkeitmass)
			throws IllegalArgumentException {
		Nutzerprofil n = this.partnerboerseAdministration.getNutzerprofilById(np.getProfilID());

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunaechst wird ein leerer Report angelegt.
		 */
		ProfilInfoByNutzerprofilReport result = new ProfilInfoByNutzerprofilReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle(n.getVorname() + " " + n.getNachname());

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von Profildaten.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Konto-Tabelle an.
		 */
		Row headline = new Row();
		headline.addColumn(new Column("Vorname"));
		headline.addColumn(new Column("Nachname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Geburtsdatum"));
		headline.addColumn(new Column("Koerpergroessse"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));
		headline.addColumn(new Column("Ähnlichkeit"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);

		/*
		 * Nun werden saemtliche Profildaten des Nutzerprofils ausgelesen
		 */

		System.out.println(np.getProfilID() + "nutzer");
		// Eine leere Zeile anlegen.
		Row profildatenoRow = new Row();

		// Spalten hinzufuegen
		profildatenoRow.addColumn(new Column(n.getVorname()));
		profildatenoRow.addColumn(new Column(n.getNachname()));
		profildatenoRow.addColumn(new Column(n.getGeschlecht()));
		profildatenoRow.addColumn(new Column(String.valueOf(n.getGeburtsdatum())));
		profildatenoRow.addColumn(new Column(String.valueOf(n.getKoerpergroesse())));
		profildatenoRow.addColumn(new Column(n.getHaarfarbe()));
		profildatenoRow.addColumn(new Column(n.getRaucher()));
		profildatenoRow.addColumn(new Column(n.getReligion()));
		profildatenoRow.addColumn(new Column(String.valueOf(aehnlichkeitmass + "%")));

		// und schlie�lich die Zeile dem Report hinzufuegen.
		result.addRow(profildatenoRow);
		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeNpReport
	 * zurueckliefert. Der Report stellt alle unangesehenen Partnervorschlaege
	 * eines Nutzerprofils dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @return AllPartnervorschlaegeNpReport Fertiges Report-Objekt vom Typ AllPartnervorschlaegeNpReport
	 * @throws IllegalArgumentException
	 */

	public AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunaechst wird ein leerer Report angelegt.
		 */
		AllPartnervorschlaegeNpReport result = new AllPartnervorschlaegeNpReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle unangesehenen Partnervorschlaege");

		CompositeParagraph imprint = new CompositeParagraph();

		ArrayList<Aehnlichkeitsmass> allNutzer = this.partnerboerseAdministration.getPartnervorschlaegeNp(np);
		for (Aehnlichkeitsmass a : allNutzer) {
			Nutzerprofil n1 = this.partnerboerseAdministration.getNutzerprofilById(a.getFremdprofil().getProfilID());
			imprint.addSubParagraph(new SimpleParagraph(String.valueOf(a.getFremdprofil().getProfilID())));
			result.addSubReport(this.createProfilInfoByNutzerprofilReport(n1, a.getAehnlichkeitsmass()));
			result.addSubReport(this.createInfoObjekteByNutzerReport(n1));
		}

		// Imressum hinzufuegen
		result.setImprint(imprint);

		/*
		 * Erstellungsdatum hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier: Kopfdaten des Reports zusammenstellen. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzerprofils aufnehmen.
		header.addSubParagraph(new SimpleParagraph(np.getVorname() + " " + np.getNachname()));

		// Nutzerprofil-ID aufnehmen.
		header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + np.getProfilID()));

		// Zusammengestellte Kopfdaten zum Report hinzufuegen.
		result.setHeaderData(header);

		/*
		 * Fertigen Report zurueckgeben.
		 */
		return result;
	}

	// SUCHPROFIL

	public PartnervorschlaegeSpReport createPartnervorschleageBySpReport(Nutzerprofil nutzerprofil,
			Suchprofil suchprofil) throws IllegalArgumentException {
		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Anlegen eines leeren Reports.
		 */
		PartnervorschlaegeSpReport result = new PartnervorschlaegeSpReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		// result.setTitle("Alle Partnervorschlaege anhand des Suchprofils: "
		// + suchprofil.getSuchprofilName());

		CompositeParagraph imprint = new CompositeParagraph();

		ArrayList<Aehnlichkeitsmass> allNutzer = this.partnerboerseAdministration.getPartnervorschlaegeSp(nutzerprofil,
				suchprofil);
		for (Aehnlichkeitsmass a : allNutzer) {
			System.out.println(a + " Suchprofile");
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufuegen zum Gesamt-Report.
			 */
			Nutzerprofil n1 = this.partnerboerseAdministration.getNutzerprofilById(a.getFremdprofil().getProfilID());
			imprint.addSubParagraph(new SimpleParagraph(String.valueOf(a.getFremdprofil().getProfilID())));
			result.addSubReport(this.createProfilInfoByNutzerprofilReport(n1, a.getAehnlichkeitsmass()));
			result.addSubReport(this.createInfoObjekteByNutzerReport(n1));
		}

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier: Kopfdaten des Reports zusammenstellen. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(nutzerprofil.getVorname() + " " + nutzerprofil.getNachname()));

		// Nutzerprofil-ID aufnehmen.
		header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + nutzerprofil.getProfilID()));

		// Zusammengestellte Kopfdaten zum Report hinzufuegen.
		result.setHeaderData(header);
		/*
		 * Nun werden saemtliche Nutzerprofil-Objekte ausgelesen. Anschlie�end
		 * wird fuer jedes Nutzerprofil-Objekt n ein Aufruf von
		 * createAllProfildatenOfNutzerReport(n) und ein Aufruf von
		 * createAllInfosOfNutzerReport(n) durchgefuehrt und somit jeweils ein
		 * AllProfildatenOfNutzerReport-Objekt und ein
		 * AllInfosOfNutzerReport-Objekt erzeugt. Diese Objekte werden
		 * sukzessive der result-Variable hinzugefuegt. Sie ist vom Typ
		 * AllPartnervorschlaegeSpReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */
		// ArrayList<Aehnlichkeitsmass> allNutzer =
		// this.partnerboerseAdministration
		// .getPartnervorschlaegeSp(suchprofil , nutzerprofil);
		//
		// for (Aehnlichkeitsmass a : allNutzer) {
		//
		// /*
		// * Anlegen des jew. Teil-Reports und Hinzufuegen zum Gesamt-Report.
		// */
		// Nutzerprofil n1 =
		// this.partnerboerseAdministration.getNutzerprofilById(a.getFremdprofil().getProfilID());
		// result.addSubReport(this.createProfilInfoByNutzerprofilReport(n1,
		// a.getAehnlichkeitsmass()));
		// result.addSubReport(this.createInfoObjekteByNutzerReport(n1));
		// }

		/*
		 * Fertigen Report zurueckgeben.
		 */
		return result;
	}
}
