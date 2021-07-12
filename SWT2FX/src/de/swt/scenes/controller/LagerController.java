package de.swt.scenes.controller;

import java.util.AbstractMap.SimpleEntry;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class LagerController {
	private Lager currentLager;
	private Lagerplatz currentLagerplatz;
	private int currentWindow;

	@FXML
	public void initialize() {
		loadLager();
		_manualSelectLagerplatz();
	}

	private void _manualSelectLagerplatz() {
		if (SceneDirector._requiredAction != RequiredAction.LAGER_SHOW_LAGERPLATZ)
			return;

		lvLager.getSelectionModel().select(((Lagerplatz) SceneDirector._requiredActionData).getLager());
		onMousePressedLager(null);
		lvLagerplatz.getSelectionModel().select((Lagerplatz) SceneDirector._requiredActionData);
		onMousePressedLagerplatz(null);
		onMousePressedProdukt(null);
		SceneDirector._resetRequiredAction();
	}

	@FXML
	private HBox hboxBtnHolder;

	@FXML
	private ListView<Lager> lvLager;

	@FXML
	private ListView<Lagerplatz> lvLagerplatz;

	@FXML
	private ListView<String> lvProdukt;

	@FXML
	private Button btn1;

	@FXML
	private Button btn2;

	@FXML
	private Button btn3;

	@FXML
	private Button btn4;

	@FXML
	void onButton1Clicked(ActionEvent event) {
		switch (currentWindow) {
		case 1: {
			SceneDirector.getInstance().sceneNeuesLager();
			break;
		}
		case 2: {
			SceneDirector.getInstance().sceneNeuerLagerplatz(currentLager);
			break;
		}
		
		case 3: {
			SceneDirector.getInstance().sceneProdukte(currentLagerplatz);
			break;
		}
		default:
		}
	}

	@FXML
	void onButton2Clicked(ActionEvent event) {
		switch (currentWindow) {
		case 1: {
			SceneDirector.getInstance().sceneLagerAnzeigen(currentLager);
			break;
		}
		case 2: {
			ProduktverwaltungDataInterface.getProduktverwaltung().removeLagerplatz(currentLagerplatz);
			SceneDirector.getInstance().sceneLager(currentLagerplatz);
			break;
		}
		case 3: {
			SceneDirector.getInstance().sceneProduktLagerplatz(new SimpleEntry<Produkt, Lagerplatz>(currentLagerplatz.getProduktanzahl().getProdukt(), currentLagerplatz));
			break;
		}
		default:
		}
	}

	@FXML
	void onButton3Clicked(ActionEvent event) {
		switch (currentWindow) {
		case 1: {
			ProduktverwaltungDataInterface.getProduktverwaltung().removeLager(currentLager);
			currentLager = null;
			SceneDirector.getInstance().sceneLager();
			break;
		}
		case 3: {
			SceneDirector.getInstance().sceneProdukte(currentLagerplatz.getProduktanzahl().getProdukt());
			break;
		}
		default:
			break;
		}
	}

	@FXML
	void onButton4Clicked(ActionEvent event) {
		switch (currentWindow) {
		case 3: {
			ProduktverwaltungDataInterface.getProduktverwaltung().removeProduktFromLagerplatz(currentLagerplatz);
			SceneDirector.getInstance().sceneLager(currentLagerplatz);
			break;
		}
		default:
			break;
		}
	}

	@FXML
	void onMousePressedLager(MouseEvent event) {
		currentWindow = 1;
		currentLager = lvLager.getSelectionModel().getSelectedItem();
		loadLagerplaetze(currentLager);
		showButtons("Lager hinzufügen", currentLager == null ? null : "Lager anzeigen", currentLager == null ? null : "Lager entfernen");
	}

	@FXML
	void onMousePressedLagerplatz(MouseEvent event) {
		currentWindow = 2;
		currentLagerplatz = lvLagerplatz.getSelectionModel().getSelectedItem();
		loadProdukt(currentLagerplatz);
		showButtons(currentLager == null ? null : "Lagerplatz hinzufügen",
				currentLagerplatz == null ? null : "Lagerplatz entfernen", null);
	}

	@FXML
	void onMousePressedProdukt(MouseEvent event) {
		currentWindow = 3;
		showButtons(currentLagerplatz == null ? null : "Neues Produkt zuweisen",
				currentLagerplatz == null || currentLagerplatz.getProduktanzahl() == null ? null
						: "Produktanzahl bearbeiten",
				currentLagerplatz == null || currentLagerplatz.getProduktanzahl() == null ? null
						: "Produkt anzeigen",
				currentLagerplatz == null || currentLagerplatz.getProduktanzahl() == null ? null
						: "Produkt aus Lagerplatz entfernen");
	}

	private void loadLager() {
		lvLager.setItems(FXCollections.observableArrayList(ProduktverwaltungDataInterface.getProduktverwaltung().getLager()));
	}

	private void loadLagerplaetze(Lager l) {
		lvLagerplatz.getItems().clear();
		lvProdukt.getItems().clear();

		if (l == null)
			return;

		lvLagerplatz.setItems(FXCollections.observableArrayList(ProduktverwaltungDataInterface.getProduktverwaltung().getLagerplaetze(l)));
	}

	private void loadProdukt(Lagerplatz lp) {
		lvProdukt.getItems().clear();

		if (lp == null)
			return;

		Produktanzahl pa = lp.getProduktanzahl();

		if (pa == null)
			lvProdukt.getItems().add("Kein Produkt zugewiesen");
		else {
			lvProdukt.getItems().add(pa.getProdukt().getName());
			lvProdukt.getItems().add(pa.getProdukt().getEAN13());
			lvProdukt.getItems().add(pa.getProdukt().getPreis() + " €");
			lvProdukt.getItems().add(pa.getAnzahl() + " Stück");
		}
	}

	private void showButtons(String name1, String name2, String name3) {
		showButtons(name1, name2, name3, null);
	}

	private void showButtons(String name1, String name2, String name3, String name4) {
		hboxBtnHolder.setVisible(true);

		if (name1 == null) {
			btn1.setVisible(false);
			btn1.setManaged(false);
		} else {
			btn1.setText(name1);
			btn1.setManaged(true);
			btn1.setVisible(true);
		}

		if (name2 == null) {
			btn2.setVisible(false);
			btn2.setManaged(false);
		} else {
			btn2.setText(name2);
			btn2.setManaged(true);
			btn2.setVisible(true);
		}

		if (name3 == null) {
			btn3.setVisible(false);
			btn3.setManaged(false);
		} else {
			btn3.setText(name3);
			btn3.setManaged(true);
			btn3.setVisible(true);
		}

		if (name4 == null) {
			btn4.setVisible(false);
			btn4.setManaged(false);
		} else {
			btn4.setText(name4);
			btn4.setManaged(true);
			btn4.setVisible(true);
		}

	}

}
