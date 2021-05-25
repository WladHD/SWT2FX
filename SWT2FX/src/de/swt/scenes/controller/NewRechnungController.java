package de.swt.scenes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.swt.produktverwaltung.Produktverwaltung;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.scenes.SceneDirector;
import de.swt.utils.VarChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class NewRechnungController implements VarChecker {

	@FXML
	public void initialize() {
		loadProdukte();
	}

	private void loadProdukte() {
		tcProdukt2.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcEinzelpreis2.setCellValueFactory(new PropertyValueFactory<>("preis"));

		tcProdukt1.setCellValueFactory(new PropertyValueFactory<>("produkt"));
		tcAnzahl.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
		tcEinzelpreis1.setCellValueFactory(new PropertyValueFactory<>("einzelpreis"));
		tcGesamtpreis.setCellValueFactory(new PropertyValueFactory<>("gesamtsumme"));
		tvProduktListe.getItems().addAll(Produktverwaltung.getInstance().getProdukte());
	}

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	@FXML
	private TableView<Produktanzahl> tvRechnungen;

	@FXML
	private TableColumn<?, ?> tcProdukt1;

	@FXML
	private TableColumn<?, ?> tcProdukt2;

	@FXML
	private TableColumn<?, ?> tcAnzahl;

	@FXML
	private TableColumn<?, ?> tcEinzelpreis1;

	@FXML
	private TableColumn<?, ?> tcEinzelpreis2;

	@FXML
	private TableColumn<?, ?> tcGesamtpreis;

	@FXML
	private Button btnAdd;

	@FXML
	private TextField tfAnzahl;

	@FXML
	private Button btnRemove;

	@FXML
	private TableView<Produkt> tvProduktListe;

	@FXML
	void onActionAdd(ActionEvent event) {
		if (tvProduktListe.getSelectionModel().getSelectedItem() == null)
			return;

		Produktanzahl searchPA = null;
		int anzahl = checkInt(tfAnzahl.getText()) ? Integer.parseInt(tfAnzahl.getText()) : 1;
		if (anzahl <= 0)
			anzahl = 1;

		for (Produktanzahl pa : tvRechnungen.getItems())
			if (pa.getProdukt() == tvProduktListe.getSelectionModel().getSelectedItem()) {
				searchPA = pa;
				break;
			}

		if (searchPA != null) {
			searchPA.setAnzahl(searchPA.getAnzahl() + anzahl);
			tvRechnungen.refresh();
		} else {
			searchPA = new Produktanzahl(tvProduktListe.getSelectionModel().getSelectedItem(), anzahl);
			tvRechnungen.getItems().add(searchPA);
		}

		tvRechnungen.getSelectionModel().select(searchPA);
		onMouseClickedRechnungen(null);
	}

	@FXML
	void onActionCancel(ActionEvent event) {
		SceneDirector.getInstance().sceneRechnung();
	}

	@FXML
	void onActionRemove(ActionEvent event) {
		if (tvRechnungen.getSelectionModel().getSelectedItem() == null)
			return;

		int anzahl = checkInt(tfAnzahl.getText()) ? Integer.parseInt(tfAnzahl.getText()) : 1;

		if (tvRechnungen.getSelectionModel().getSelectedItem().getAnzahl() - anzahl > 0) {
			tvRechnungen.getSelectionModel().getSelectedItem()
					.setAnzahl(tvRechnungen.getSelectionModel().getSelectedItem().getAnzahl() - anzahl);
			tvRechnungen.refresh();
			return;
		}

		tvRechnungen.getItems().remove(tvRechnungen.getSelectionModel().getSelectedItem());
		onMouseClickedRechnungen(null);
	}

	@FXML
	void onActionSave(ActionEvent event) {
		if (tvRechnungen.getItems().size() == 0) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Keine Einträge",
					"Rechnung muss mindestens einen Artikel enthalten.");
			return;
		}
		
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        
		Rechnung r = new Rechnung(dateFormat.format(date).split(" ")[0],  dateFormat.format(date).split(" ")[1]);
		
		for(Produktanzahl pa : tvRechnungen.getItems())
			r.addProdukt(pa);
		
		Produktverwaltung.getInstance().addRechnung(r);
		SceneDirector.getInstance().sceneRechnung(r);
	}

	@FXML
	void onMouseClickedProduktListe(MouseEvent event) {
		btnAdd.setDisable(tvProduktListe.getSelectionModel().getSelectedItem() == null);
	}

	@FXML
	void onMouseClickedRechnungen(MouseEvent event) {
		btnRemove.setDisable(tvRechnungen.getSelectionModel().getSelectedItem() == null);
	}

}
