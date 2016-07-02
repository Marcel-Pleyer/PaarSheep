package de.hdm.Gruppe4.Paarsheep.client.gui;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.Gruppe4.Paarsheep.client.ClientsideSettings;
import de.hdm.Gruppe4.Paarsheep.shared.PartnerboerseAdministrationAsync;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;

public class NutzerForm extends VerticalPanel {

	// Hier wird die Klasse PartnerboerseAdministration/Asyn instanziiert, damit
	// wir auf die Methode createNutzerprofil dieser Klasse zugreifen koennen.
	PartnerboerseAdministrationAsync partnerboerseVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();

	// Geburtsdatum
	private Label geburtsdatumInhalt = new Label();
	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	private DateBox geburtsdatumDateBox = new DateBox();

	private Label idValueLabel = new Label();
	private VerticalPanel vPanel = new VerticalPanel();
	private ListBox religionListBox = new ListBox();
	// Alle m�ssen die Spalte Koerpergroesse in ihrer Datenbank auf INT aendern
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();

	/**
	 * Diese Methode laedt das Formular zur Erstellung eines neuen Nutzers
	 * 
	 * @param email
	 */
	public void ladeNutzerForm(String email) {
		final String emailAddress = email;

		// Erzeugt und strukturiert die Widgets, welche genutzt werden um
		// einen neuen Nutzer anzulegen.
		Grid nutzerGrid = new Grid(9, 3);
		this.add(nutzerGrid);
		vPanel.add(nutzerGrid);

		Label idLabel = new Label("");
		nutzerGrid.setWidget(0, 0, idLabel);
		nutzerGrid.setWidget(0, 1, idValueLabel);

		Label vornameLabel = new Label("Vorname:");
		nutzerGrid.setWidget(1, 0, vornameLabel);
		nutzerGrid.setWidget(1, 1, vornameTextBox);

		Label nachnameLabel = new Label("Nachname:");
		nutzerGrid.setWidget(2, 0, nachnameLabel);
		nutzerGrid.setWidget(2, 1, nachnameTextBox);

		Label geschlechtLabel = new Label("Geschlecht:");
		geschlechtListBox.addItem("Keine Angabe");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		geschlechtListBox.addItem("Andere");
		nutzerGrid.setWidget(3, 0, geschlechtLabel);
		nutzerGrid.setWidget(3, 1, geschlechtListBox);

		geburtsdatumDateBox.setFormat(new DateBox.DefaultFormat(geburtsdatumFormat));
		geburtsdatumDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
		geburtsdatumDateBox.getDatePicker().setVisibleYearCount(50);
		geburtsdatumDateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date geburtsdatum = event.getValue();
				String geburtsdatumString = DateTimeFormat.getFormat("yyyy-MM-dd").format(geburtsdatum);
				geburtsdatumInhalt.setText(geburtsdatumString);
			}
		});
		geburtsdatumDateBox.setValue(new Date());
		Label geburtsdatumLabel = new Label("Geburtsdatum:");
		nutzerGrid.setWidget(4, 0, geburtsdatumLabel);
		nutzerGrid.setWidget(4, 1, geburtsdatumDateBox);

		Label religionLabel = new Label("Religion");
		nutzerGrid.setWidget(5, 0, religionLabel);
		nutzerGrid.setWidget(5, 1, religionListBox);
		religionListBox.addItem("Keine Angabe");
		religionListBox.addItem("Christentum");
		religionListBox.addItem("Islam");
		religionListBox.addItem("Judentum");
		religionListBox.addItem("Buddhismus");
		religionListBox.addItem("Hinduismus");
		religionListBox.addItem("Andere");

		Label koerpergroesseLabel = new Label("Koerpergroesse");
		nutzerGrid.setWidget(6, 0, koerpergroesseLabel);
		nutzerGrid.setWidget(6, 1, koerpergroesseTextBox);

		Label haarfarbeLabel = new Label("Haarfarbe");
		nutzerGrid.setWidget(7, 0, haarfarbeLabel);
		nutzerGrid.setWidget(7, 1, haarfarbeListBox);
		haarfarbeListBox.addItem("Keine Angabe");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Andere");

		Label raucherLabel = new Label("Raucher");
		nutzerGrid.setWidget(8, 0, raucherLabel);
		nutzerGrid.setWidget(8, 1, raucherListBox);
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Nichtraucher");
		raucherListBox.addItem("Gelegenheitsraucher");
		raucherListBox.addItem("Raucher");

		RootPanel.get("Profil").add(vPanel);
		HorizontalPanel nutzerButtonsPanel = new HorizontalPanel();
		this.add(nutzerButtonsPanel);

		Button anlegenButton = new Button("Anlegen");
		nutzerButtonsPanel.add(anlegenButton);
		Button abbrechenButton = new Button("Abbrechen");
		nutzerButtonsPanel.add(abbrechenButton);

		RootPanel.get("Profil").add(nutzerButtonsPanel);

		// Button um Eingabe abzubrechen.
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("Profil").clear();
				NutzerForm nutzerform = new NutzerForm();
				nutzerform.ladeNutzerForm(emailAddress);
			}
		});
		// Der Button, mit zugehoeriger Methode, zur Erzeugung eines neuen
		// Nutzers
		anlegenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				String vorname = vornameTextBox.getText();
				String nachname = nachnameTextBox.getText();
				Date geburtsdatum = getGeburtsdatum();
				String geschlecht = geschlechtListBox.getSelectedItemText();

				String religion = religionListBox.getSelectedItemText();
				String koerpergroesseString = koerpergroesseTextBox.getText();
				int koerpergroesse = Integer.parseInt(koerpergroesseString);
				String haarfarbe = haarfarbeListBox.getSelectedItemText();
				String raucher = raucherListBox.getSelectedItemText();

				partnerboerseVerwaltung.createNutzerprofil(geburtsdatum, emailAddress, vorname, nachname, geschlecht,
						religion, koerpergroesse, haarfarbe, raucher, new CreateNutzerprofilCallback());
			}
		});
	}

	Date getGeburtsdatum() {
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}
}

// Diese Methode organisiert den asynchronen Callback und gibt uns eine
// Nachricht aus, ob dieser Callback funktioniert
class CreateNutzerprofilCallback implements AsyncCallback<Nutzerprofil> {

	public void onFailure(Throwable caught) {
		Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
	}

	public void onSuccess(Nutzerprofil profil) {
		if (profil != null) {
			Window.alert("Das Anlegen eines neuen Kunden war erfolgreich!");
			RootPanel.get("Profil").clear();
			Window.Location.reload();
			Startseite ladeStartseite = new Startseite();
			ladeStartseite.ladeStartseite();
		}
	}
}