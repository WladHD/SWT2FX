package de.swt.scenes.controller;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import de.swt.utils.Address;
import de.swt.utils.VarChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NewLagerController implements VarChecker {

	@FXML
	public void initialize() {
		_showLager();
	}

	private void _showLager() {
		if (SceneDirector._requiredAction != RequiredAction.LAGER_SHOW_LAGER)
			return;

		btnSave.setVisible(false);
		btnSave.setManaged(false);

		Lager l = (Lager) SceneDirector._requiredActionData;

		tfName.setText(l.getName());
		tfName.setDisable(true);

		tfLand.setText(l.getAddress().getCountry());
		tfLand.setDisable(true);

		tfBundesland.setText(l.getAddress().getState());
		tfBundesland.setDisable(true);

		tfPostleitzahl.setText(l.getAddress().getPostCode() + "");
		tfPostleitzahl.setDisable(true);

		tfStadt.setText(l.getAddress().getCity());
		tfStadt.setDisable(true);

		tfStrasse.setText(l.getAddress().getStreet());
		tfStrasse.setDisable(true);

		tfHausNr.setText(l.getAddress().getHouseNr() + "");
		tfHausNr.setDisable(true);
		
		SceneDirector._resetRequiredAction();
	}

	@FXML
	private Button btnSave;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfLand;

	@FXML
	private TextField tfBundesland;

	@FXML
	private TextField tfPostleitzahl;

	@FXML
	private TextField tfStadt;

	@FXML
	private TextField tfStrasse;

	@FXML
	private TextField tfHausNr;

	@FXML
	void onActionAbbrechen(ActionEvent event) {
		SceneDirector.getInstance().sceneLager();
	}

	@FXML
	void onActionSpeichern(ActionEvent event) {
		if (!checkString(tfName)) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiger Name", "Der Name darf nicht leer sein.");
			return;
		}

		if (!checkString(tfLand)) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiges Land", "Das Land darf nicht leer sein.");
			return;
		}

		if (!checkString(tfBundesland)) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiges Bundesland",
					"Das Bundesland darf nicht leer sein.");
			return;
		}

		if (tfPostleitzahl.getText().length() == 5 && !checkInt(tfPostleitzahl.getText())) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Postleitzahl",
					"Die Postleitzahl muss eine Zahl und 5 Stellen lang sein.");
			return;
		}

		if (!checkString(tfStadt)) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Stadt",
					"Die Stadt darf nicht leer sein.");
			return;
		}

		if (!checkString(tfStrasse)) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Straße",
					"Die Straße darf nicht leer sein.");
			return;
		}

		if (!checkInt(tfHausNr.getText())) {
			SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Hausnummer",
					"Die Hausnummer muss eine Zahl sein.");
			return;
		}

		ProduktverwaltungDataInterface.getProduktverwaltung()
				.addLager(new Lager(tfName.getText(),
						new Address(tfLand.getText(), tfBundesland.getText(),
								Integer.parseInt(tfPostleitzahl.getText()), tfStadt.getText(), tfStrasse.getText(),
								Integer.parseInt(tfHausNr.getText()))));
		SceneDirector.getInstance().sceneLager();
	}

}
