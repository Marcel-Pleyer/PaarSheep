package de.hdm.Gruppe4.Paarsheep.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.Gruppe4.Paarsheep.shared.PartnerboerseAdministrationAsync;
import de.hdm.Gruppe4.Paarsheep.shared.bo.Nutzerprofil;

/**
 * Die Klasse FremdesProfilInfo erweitert das Vertical Panel.
 * Hiermit koennen die Infos eines fremden Profils dargestellt werden.
 * 
 * @author An Dang
 *
 */
public class FremdesProfilInfo extends VerticalPanel {
	
	PartnerboerseAdministrationAsync partnerboerseVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public FremdesProfilInfo() {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Infos dieser Person:");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		// Tabelle für Beschreibungsinfo

		/**
		 * Tabelle erzeugen, in der die Beschreibungsinfos dargestellt werden.
		 */
		final FlexTable showInfoFlexTable = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft");
		showInfoFlexTable.setText(0, 2, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label infoLabel = new Label();


//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(
//				profilId, new AsyncCallback<List<Info>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabel.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(List<Info> result) {
//						// Anzahl der Zeilen ermitteln.
//						int row = showInfoFlexTable.getRowCount();
//
//						// Tabelle mit Inhalten aus der Datenbank befüllen.
//						for (Info i : result) {
//							row++;
//							
//							final String fremdprofilId = String.valueOf(i.getNutzerprofilId());
//							
//							showInfoFlexTable.setText(row, 0, fremdprofilId);
//							showInfoFlexTable.setText(row, 1, i.getEigenschaftErlaeuterung());
//							showInfoFlexTable.setText(row, 2, i.getInfotext());
//						}
//					}
//				});

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(profilId, new AsyncCallback<List<Info>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				infoLabel.setText("Es trat ein Fehler auf.");
//			}
//
//			@Override
//			public void onSuccess(List<Info> result) {
//				// Anzahl der Zeilen ermitteln.
//				int row = showInfoFlexTable.getRowCount();
//
//				// Tabelle mit Inhalten aus der Datenbank befüllen.
//				for (Info i : result) {
//					row++;
//
//					final String fremdprofilId = String.valueOf(i.getNutzerprofilId());
//
//					showInfoFlexTable.setText(row, 0, fremdprofilId);
//					showInfoFlexTable.setText(row, 1, i.getEigenschaftErlaeuterung());
//					showInfoFlexTable.setText(row, 2, i.getInfotext());
//				}
//			}
//		});


		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);

		// Tabelle für Auswahlinfo

		/**
		 * Tabelle erzeugen, in der das Suchprofil dargestellt wird.
		 */
		final FlexTable showInfoFlexTableAuswahl = new FlexTable();

				/**
				 * Tabelle formatieren und CSS einbinden.
				 */
				showInfoFlexTableAuswahl.setCellPadding(6);
				showInfoFlexTableAuswahl.getRowFormatter().addStyleName(0, "TableHeader");
				showInfoFlexTableAuswahl.addStyleName("FlexTable");
				
				
//				ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(
//						profilId, new AsyncCallback<List<Info>>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								infoLabel.setText("Es trat ein Fehler auf.");
//							}
//
//							@Override
//							public void onSuccess(List<Info> result) {
//								// Anzahl der Zeilen ermitteln.
//								int row = showInfoFlexTableAuswahl.getRowCount();
//
//								// Tabelle mit Inhalten aus der Datenbank befüllen.
//								for (Info iA : result) {
//									row++;
//									
//									final String fremdprofilId = String.valueOf(iA.getNutzerprofilId());
//									
//									showInfoFlexTableAuswahl.setText(row, 0, fremdprofilId);
//									showInfoFlexTableAuswahl.setText(row, 1, iA.getEigenschaftErlaeuterung());
//									showInfoFlexTableAuswahl.setText(row, 2, iA.getOptionsbezeichnung());
//								}
//							}
//						});	
				

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTableAuswahl.setText(0, 0, "Nutzerprofil-Id");
		showInfoFlexTableAuswahl.setText(0, 1, "Eigenschaft");
		showInfoFlexTableAuswahl.setText(0, 2, "Auswahloption");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTableAuswahl.setCellPadding(6);
		showInfoFlexTableAuswahl.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTableAuswahl.addStyleName("FlexTable");

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(profilId, new AsyncCallback<List<Info>>() {
//
//			@Override
//			public void onSuccess(List<Info> result) {
//				// Anzahl der Zeilen ermitteln.
//				int row = showInfoFlexTableAuswahl.getRowCount();
//
//				// Tabelle mit Inhalten aus der Datenbank befüllen.
//				for (Info iA : result) {
//					row++;
//
//					final String fremdprofilId = String.valueOf(iA.getNutzerprofilId());
//
//					showInfoFlexTableAuswahl.setText(row, 0, fremdprofilId);
//					showInfoFlexTableAuswahl.setText(row, 1, iA.getEigenschaftErlaeuterung());
//					showInfoFlexTableAuswahl.setText(row, 2, iA.getOptionsbezeichnung());
//				}
//			}
//		});

		verPanel.add(showInfoFlexTableAuswahl);
		verPanel.add(infoLabel);

	}
}