package de.swt.scenes.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import de.swt.produktverwaltung.Produktverwaltung;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class RechnungController {

	@FXML
	public void initialize() {
		btnProdukt.setManaged(false);
		loadRechnungen();
	}

	private void loadRechnungen() {
		tcDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		tcZeit.setCellValueFactory(new PropertyValueFactory<>("zeit"));
		tcSumme.setCellValueFactory(new PropertyValueFactory<>("summe"));

		tcDatum.setComparator(new Comparator<String>() {

			@Override
			public int compare(String t, String t1) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					Date d1 = format.parse(t);
					Date d2 = format.parse(t1);
					return Long.compare(d1.getTime(), d2.getTime());
				} catch (ParseException p) {
					p.printStackTrace();
				}
				return -1;

			}

		});

		tcProdukt.setCellValueFactory(new PropertyValueFactory<>("produkt"));
		tcAnzahl.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
		tcEinzelpreis.setCellValueFactory(new PropertyValueFactory<>("einzelpreis"));
		tcGruppenpreis.setCellValueFactory(new PropertyValueFactory<>("gesamtsumme"));

		tvRechnungen.getItems().addAll(Produktverwaltung.getInstance().getRechnungen());
		_manualSelectRechnung();
	}

	private void _manualSelectRechnung() {
		if (SceneDirector._requiredAction != RequiredAction.RECHNUNG_SHOW_RECHNUNG)
			return;

		tvRechnungen.getSelectionModel().select((Rechnung) SceneDirector._requiredActionData);
		onMouseClickedRechnungen(null);
		SceneDirector._resetRequiredAction();
	}

	@FXML
	private Button btnRechnung;

	@FXML
	private Button btnProdukt;

	@FXML
	private TableView<Produktanzahl> tvProduktListe;

	@FXML
	private TableColumn<?, ?> tcProdukt;

	@FXML
	private TableColumn<?, ?> tcAnzahl;

	@FXML
	private TableColumn<?, ?> tcEinzelpreis;

	@FXML
	private TableColumn<?, ?> tcGruppenpreis;

	@FXML
	private TableView<Rechnung> tvRechnungen;

	@FXML
	private TableColumn<?, String> tcDatum;

	@FXML
	private TableColumn<?, ?> tcZeit;

	@FXML
	private TableColumn<?, ?> tcSumme;

	@FXML
	void onActionProdukt(ActionEvent event) {
		SceneDirector.getInstance().sceneProdukte(tvProduktListe.getSelectionModel().getSelectedItem().getProdukt());
	}

	@FXML
	void onActionRechnung(ActionEvent event) {
		SceneDirector.getInstance().sceneNeueRechnung();
	}

	@FXML
	void onMouseClickedProduktListe(MouseEvent event) {
		if (tvProduktListe.getSelectionModel().getSelectedItem() == null) {
			btnProdukt.setVisible(false);
			btnProdukt.setManaged(false);
			return;
		}

		btnProdukt.setVisible(true);
		btnProdukt.setManaged(true);
	}

	@FXML
	void onMouseClickedRechnungen(MouseEvent event) {
		tvProduktListe.getItems().clear();
		btnProdukt.setVisible(false);
		btnProdukt.setManaged(false);

		if (tvRechnungen.getSelectionModel().getSelectedItem() == null)
			return;

		tvProduktListe.getItems().addAll(tvRechnungen.getSelectionModel().getSelectedItem().getProdukte());
	}

}
