package de.hdm.Gruppe4.Paarsheep.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.Gruppe4.Paarsheep.client.ClientsideSettings;
import de.hdm.Gruppe4.Paarsheep.shared.PartnerboerseAdministrationAsync;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Beschreibung;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Information;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Suchprofil;

/**
 * Diese Klasse SuchprofilAnzeigen erweitert das VerticalPanel.
 * Hiermit koennen die Suchprofile angelegt, angezeigt und verwaltet werden.
 * 
 * @author Tino Hauler
 */
public class SuchprofilAnzeigen extends VerticalPanel {

	PartnerboerseAdministrationAsync partnerboerseVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanels und HorizontalPanels hinzufuegen.
	 */
	private VerticalPanel suchprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	/**
	 * Widgets hinzufuegen.
	 */
	private Label auswahlLabel = new Label("Wähl dein Suchprofil aus.");
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private FlexTable SuchprofilAnzeigenFlexTable = new FlexTable();

	private Button anzeigenButton = new Button("Anzeigen", new AnzeigenHandler());
	private Button loeschenButton = new Button("Löschen", new DeleteSuchprofilClickHandler());
	private Button bearbeitenButton = new Button("Bearbeiten", new SuchprofilBearbeitenClickHandler());

	private int row;
	private int beschreibungInt;
	private int beschreibungsTable;
	protected int Beschreibung;

	/**
	 * Um das Suchprofil anzeigen zu koennen.
	 */
	public SuchprofilAnzeigen() {

		this.add(mainPanel);

		/**
		 *  Widgets in Panels hinzufügen 
		 */
		mainPanel.add(suchprofilPanel);
		mainPanel.add(infoPanel);
		suchprofilPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		suchprofilPanel.add(auswahlPanel);
		suchprofilPanel.add(buttonPanel);
		
		/**
		 * ClickHandler fuer den Button zum Bearbeiten eines Suchprofils
		 * erzeugen. Sobald dieser Button betaetigt wird, wird die Seite zum
		 * Bearbeiten eines Suchprofils aufgerufen.
		 * @param suchprofilBearbeiten Suchprofil laden in ListBox zum auswaehlen
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				SuchprofilBearbeiten suchprofilBearbeiten = new SuchprofilBearbeiten(
						auswahlListBox.getSelectedItemText());
				RootPanel.get("Profil").clear();
				RootPanel.get("Profil").add(suchprofilBearbeiten);
			}
		});
		loadSuchprofile();
	}

	/**
	 * angelegte Suchprofile suchen und anzeigen lassen
	 */
	public void loadSuchprofile() {
		ClientsideSettings.getPartnerboerseAdministration().findSuchprofilByNutzerID(nutzerprofil.getProfilID(),
				new AsyncCallback<ArrayList<Suchprofil>>() {
					
					/**
					 * um Fehler abzufangen
					 */
					public void onFailure(Throwable caught) {
					}

					/**
					 * Buttons und Panels zum anzeigen eines Suchprofils hinzufuegen
					 * Suchprofil wird angezeigt, falls vorhanden (oder Meldung, falls nicht)
					 * @param suchprofilErstellenForm laedt die Form zum erstellen eines Suchprofils
					 */
					public void onSuccess(ArrayList<Suchprofil> result) {
						if (result.isEmpty()) {
							auswahlListBox.setVisible(false);
							anzeigenButton.setVisible(false);
							suchprofilPanel.setVisible(false);
							Window.alert("Sie haben bisher noch kein Suchprofil angelegt");
							RootPanel.get("NutzerForm").clear();
							RootPanel.get("Profil").clear();
							RootPanel.get("EigenschaftForm").clear();
							SuchprofilErstellenForm suchprofilErstellenForm = new SuchprofilErstellenForm();
							RootPanel.get("Profil").add(suchprofilErstellenForm);	
						} else {
							// suchprofile = result;
							for (Suchprofil suchprofil : result) {
								auswahlListBox.addItem(suchprofil.getSuchprofilName());
								final int suchprofilId = suchprofil.getProfilID();
							}
						}
					}
				});
	}

	/**
	 * Clickhandler zum Updaten des Suchprofils
	 * @author An Dang
	 *
	 */
	private class SuchprofilBearbeitenClickHandler implements ClickHandler {

		/**
		 * Versucht Suchprofil zu laden, faengt etwaige Fehler ab
		 */
		public void onClick(ClickEvent event) {
			try {
				loadSuchprofile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Callback zum Updaten des Suchprofils
	 * @author An Dang
	 *
	 */
	private class UpdateCallback implements AsyncCallback {
		/**
		 * um Fehler abzufangen
		 */
		public void onFailure(Throwable caught) {
		}

		/**
		 * Suchprofil wird aus Datenbank geladen
		 * @param suchprofilAnzeigen Suchprofil soll angezeigt werden
		 * @param suchprofilid ID des Suchprofils zum ausgeben
		 */
		public void onSuccess(Object result) {
			int suchprofilid = Integer.valueOf(SuchprofilAnzeigenFlexTable.getText(0, 2));
			SuchprofilAnzeigen suchprofilAnzeigen = new SuchprofilAnzeigen();
			RootPanel.get("Profil").clear();
			RootPanel.get("Profil").add(suchprofilAnzeigen);
		}
	}

	/**
	 * ClickHandler fuer den Anzeigen-Button hinzufuegen.
	 * @author An Dang
	 */
	private class AnzeigenHandler implements ClickHandler {
		/**
		 * Tabelle mit Suchprofildaten befuellen
		 */
		public void onClick(ClickEvent e) {
			try {
				ClientsideSettings.getPartnerboerseAdministration().findSuchprofiByName(nutzerprofil.getProfilID(),
						auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() {
						
						/**
						 * um Fehler abzufangen
						 */
						public void onFailure(Throwable caught) {
							}

							/**
							 * Daten in FlexTable einfuegen
							 */
							public void onSuccess(Suchprofil result) {
								SuchprofilAnzeigenFlexTable.setText(0, 0, "Suchprofilname:");
								SuchprofilAnzeigenFlexTable.setText(1, 0, "Religion:");
								SuchprofilAnzeigenFlexTable.setText(2, 0, "Körpergröße:");
								SuchprofilAnzeigenFlexTable.setText(3, 0, "Haarfarbe:");
								SuchprofilAnzeigenFlexTable.setText(4, 0, "Raucher:");
								SuchprofilAnzeigenFlexTable.setText(5, 0, "Geschlecht:");

								SuchprofilAnzeigenFlexTable.setText(0, 1, result.getSuchprofilName());
								SuchprofilAnzeigenFlexTable.setText(1, 1, result.getReligion());
								SuchprofilAnzeigenFlexTable.setText(2, 1, Integer.toString(result.getKoerpergroesse()));
								SuchprofilAnzeigenFlexTable.setText(3, 1, result.getHaarfarbe());
								SuchprofilAnzeigenFlexTable.setText(4, 1, result.getRaucher());
								SuchprofilAnzeigenFlexTable.setText(5, 1, result.getGeschlecht());

								partnerboerseVerwaltung.showProfilAllEigBeschreibung(result.getProfilID(),
										new AsyncCallback<Map<List<Beschreibung>, List<Information>>>() {

											/**
											 * um Fehler abzufangen; gibt Fehlermeldung aus
											 */
											public void onFailure(Throwable caught) {
												infoLabel.setText("Es trat ein Fehler auf!");
											}
											
											/**
											 * Suchprofildaten anzeigen lassen 
											 */
											public void onSuccess(Map<List<Beschreibung>, List<Information>> result) {

												Set<List<Beschreibung>> output = result.keySet();

												for (List<Beschreibung> listEig : output) {

													row = SuchprofilAnzeigenFlexTable.getRowCount();

													for (Beschreibung beschreibung : listEig) {
														row++;
														String s = String.valueOf(beschreibung.getID());
														Label l = new Label(s);
														l.setVisible(false);
														SuchprofilAnzeigenFlexTable.setText(row, 0,
																beschreibung.getErlaeuterung());
														SuchprofilAnzeigenFlexTable.setWidget(row, 2, l);
													}
													List<Information> listInfo = new ArrayList<Information>();
													listInfo = result.get(listEig);

													row = 0;
													row = SuchprofilAnzeigenFlexTable.getRowCount();
													for (Information information : listInfo) {
														row++;
														beschreibungInt = 0;
														beschreibungInt = information.getID();
														for (int i = 7; i < SuchprofilAnzeigenFlexTable
																.getRowCount(); i++) {
															beschreibungsTable = 0;
															beschreibungsTable = Integer.valueOf(SuchprofilAnzeigenFlexTable.getText(i, 2));
															if (beschreibungInt == beschreibungsTable) {
																SuchprofilAnzeigenFlexTable.setText(i, 1,
																		information.getInformation());
															}
														}
													}
												}
											}
										});

								/**
								 * CSS-Anbindung
								 */
								SuchprofilAnzeigenFlexTable.addStyleName("flexTable");
								auswahlLabel.addStyleName("Label-Style");
								
								suchprofilPanel.add(SuchprofilAnzeigenFlexTable);
								suchprofilPanel.add(buttonPanel);
								buttonPanel.add(bearbeitenButton);
								buttonPanel.add(loeschenButton);

							}
						});
			} 
			/**
			 * Fehler abfangen
			 */
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * ClickHandler um das Suchprofil auch aus der DB zu loeschen
	 *
	 */
	private class DeleteSuchprofilClickHandler implements ClickHandler {
		
		/**
		 * um Suchprofil aus DB zu loeschen
		 */
		public void onClick(ClickEvent event) {
			try {
				ClientsideSettings.getPartnerboerseAdministration().deleteSuchprofil(nutzerprofil.getProfilID(),
						auswahlListBox.getItemText(auswahlListBox.getSelectedIndex()),
						new SuchprofilLoeschenCallback());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Window.alert("Das Suchprofil wurde erfolgreich gelöscht");
		}

		/**
		 * Callback zum loeschen des Suchprofils
		 *
		 */
		private class SuchprofilLoeschenCallback implements AsyncCallback {
			
			/**
			 * um Fehler abzufangen
			 */
			public void onFailure(Throwable caught) {
				suchprofilPanel.add(new Label(caught.toString()));
			}

			/**
			 * Startseite wird neu geladen wenn Suchprofil geloescht werden konnte
			 * @param suchprofilAnzeigen Suchprofil wird in Form angezeigt
			 */
			public void onSuccess(Object result) {
				RootPanel.get("NutzerForm").clear();
				RootPanel.get("Profil").clear();
				RootPanel.get("EigenschaftForm").clear();
				SuchprofilAnzeigen suchprofilAnzeigen = new SuchprofilAnzeigen();
				RootPanel.get("Profil").add(suchprofilAnzeigen);
			}
		}
	}

}
