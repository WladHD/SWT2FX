package de.swt.scenes.controller;

import java.io.IOException;
import java.util.Optional;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.scenes.SceneDirector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	private BorderPane mainPane;

	@FXML
	void onCloseRequested(ActionEvent event) {
		Platform.exit();
        System.exit(0);
	}

	@FXML
	void onLagerungClick(ActionEvent event) throws IOException {
		SceneDirector.getInstance().sceneLager();
	}

	@FXML
	void onLoadRequested(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Daten von Datei laden");
		dialog.setContentText("Bitte Pfad eingeben:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			boolean s = ProduktverwaltungDataInterface.getProduktverwaltung().loadAll(result.get());
			SceneDirector.getInstance().sceneProdukte();
			SceneDirector.getInstance().showAlert(s ? AlertType.INFORMATION : AlertType.ERROR, s ? "Erfolg" : "Fehler",
					s ? "Daten erfolgreich geladen." : "Ein interner Fehler ist aufgetreten.");
		}
	}

	@FXML
	void onProduktClick(ActionEvent event) throws IOException {
		SceneDirector.getInstance().sceneProdukte();
	}

	@FXML
	void onRechnungClick(ActionEvent event) {
		SceneDirector.getInstance().sceneRechnung();
	}

	@FXML
	void onSaveRequested(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Daten in Datei speichern");
		dialog.setContentText("Bitte Pfad eingeben:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			boolean s = ProduktverwaltungDataInterface.getProduktverwaltung().saveAll(result.get());
			SceneDirector.getInstance().showAlert(s ? AlertType.INFORMATION : AlertType.ERROR, s ? "Erfolg" : "Fehler",
					s ? "Daten erfolgreich gespeichert." : "Ein interner Fehler ist aufgetreten.");
		}

	}

}
