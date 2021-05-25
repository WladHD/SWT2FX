package de.swt.scenes.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import de.swt.produktverwaltung.Produktverwaltung;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProduktController {

	@FXML
	public void initialize() {
		loadProdukte();
	}
	
	private Lagerplatz _cacheForProduktSelection;

	private void loadProdukte() {
		tcProdukt.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
		tcPreis.setCellValueFactory(new PropertyValueFactory<>("preis"));
		tcTyp.setCellValueFactory(new PropertyValueFactory<>("typ"));
		tcInfo.setCellValueFactory(new PropertyValueFactory<>("zusatzinfo"));
		tcAnzahl.setCellValueFactory(new PropertyValueFactory<>("AnzahlInLager"));
		tcKaeufe.setCellValueFactory(new PropertyValueFactory<>("AnzahlDerKaeufe"));
		tvProdukte.getItems().addAll(Produktverwaltung.getInstance().getProdukte());
		
		btnLagerplatz.setVisible(false);
		btnRechnung.setVisible(false);
		
		btnProduktLagerplatz.setVisible(false);
		btnProduktLagerplatz.setManaged(false);

		_manualSelectProdukt();
		_selectProduktLagerplatz();
	}

	private void _manualSelectProdukt() {
		if (SceneDirector._requiredAction != RequiredAction.PRODUKT_SHOW_PRODUKT)
			return;

		tvProdukte.getSelectionModel().select((Produkt) SceneDirector._requiredActionData);
		onMouseClickedProdukt(null);
		SceneDirector._resetRequiredAction();
	}
	
	private void _selectProduktLagerplatz() {
		if(SceneDirector._requiredAction != RequiredAction.PRODUKT_NEED_SELECTED_PRODUKT)
			return;
		
		_cacheForProduktSelection = (Lagerplatz) SceneDirector._requiredActionData;
		SceneDirector._resetRequiredAction();
	}

	@FXML
	private Button btnProduktLagerplatz;

	@FXML
	private TableView<Produkt> tvProdukte;

	@FXML
	private TableColumn<?, ?> tcProdukt;

	@FXML
	private TableColumn<?, ?> tcEAN13;

	@FXML
	private TableColumn<?, ?> tcPreis;

	@FXML
	private TableColumn<?, ?> tcTyp;

	@FXML
	private TableColumn<?, ?> tcInfo;

	@FXML
	private TableColumn<?, ?> tcAnzahl;

	@FXML
	private TableColumn<?, ?> tcKaeufe;

	@FXML
	private Button btnProduktNeu;

	@FXML
	private ListView<Rechnung> lvRechnungen;

	@FXML
	private Button btnRechnung;

	@FXML
	private ListView<Lagerplatz> lvLagerplaetze;

	@FXML
	private Button btnLagerplatz;

	@FXML
	void onActionProduktNeu(ActionEvent event) {
		SceneDirector.getInstance().sceneNeuesProdukt();
	}

	@FXML
	void onActionLagerplatzOeffnen(ActionEvent event) {
		if (lvLagerplaetze.getSelectionModel().getSelectedItem() != null)
			SceneDirector.getInstance().sceneLager(lvLagerplaetze.getSelectionModel().getSelectedItem());
	}

	@FXML
	void onActionRechnungOeffnen(ActionEvent event) {
		if (lvRechnungen.getSelectionModel().getSelectedItem() != null)
			SceneDirector.getInstance().sceneRechnung(lvRechnungen.getSelectionModel().getSelectedItem());
	}

	@FXML
	void onActionProduktLagerplatz(ActionEvent event) {
		SceneDirector.getInstance().sceneProduktLagerplatz(new SimpleEntry<Produkt, Lagerplatz>(tvProdukte.getSelectionModel().getSelectedItem(), _cacheForProduktSelection));
		_cacheForProduktSelection = null;
	}

	@FXML
	void onMouseClickedLagerplatz(MouseEvent event) {
		btnLagerplatz.setVisible(lvLagerplaetze.getSelectionModel().getSelectedItem() != null);
	}

	@FXML
	void onMouseClickedRechnung(MouseEvent event) {
		btnRechnung.setVisible(lvRechnungen.getSelectionModel().getSelectedItem() != null);
	}

	@FXML
	void onMouseClickedProdukt(MouseEvent event) {
		loadRechnungen(tvProdukte.getSelectionModel().getSelectedItem());
		loadLagerplaetze(tvProdukte.getSelectionModel().getSelectedItem());
		
		if(_cacheForProduktSelection != null) {
			btnProduktLagerplatz.setVisible(true);
			btnProduktLagerplatz.setManaged(true);
		}
	}

	private void loadRechnungen(Produkt p) {
		lvRechnungen.getItems().clear();

		if (p == null) {
			btnRechnung.setVisible(false);
			return;
		}

		for (Rechnung r : Produktverwaltung.getInstance().getRechnungen()) {
			for (Produktanzahl pa : r.getProdukte()) {
				if (pa.getProdukt() == p) {
					lvRechnungen.getItems().add(r);
					break;
				}
			}
		}

		if (lvRechnungen.getItems().size() == 0)
			btnRechnung.setVisible(false);
		else
			btnRechnung.setVisible(true);

	}

	private void loadLagerplaetze(Produkt p) {
		lvLagerplaetze.getItems().clear();

		if (p == null) {
			btnLagerplatz.setVisible(false);
			return;
		}

		lvLagerplaetze.getItems()
				.addAll(Produktverwaltung.getInstance().getLagerplaetze().stream()
						.filter(x -> x.getProduktanzahl() != null && x.getProduktanzahl().getProdukt() == p)
						.collect(Collectors.toList()));

		if (lvLagerplaetze.getItems().size() == 0)
			btnLagerplatz.setVisible(false);
		else
			btnLagerplatz.setVisible(true);
	}

}
