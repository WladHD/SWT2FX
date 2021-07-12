package de.swt.scenes.controller;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import de.swt.utils.VarChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class NewLagerplatzController implements VarChecker {
	
	private Lager l;
	
	@FXML
	public void initialize() {
		_initLager();
	}
	
	private void _initLager() {
		if(SceneDirector._requiredAction == RequiredAction.LAGER_NEW_LAGERPLATZ) {
			l = (Lager) SceneDirector._requiredActionData;
			SceneDirector._resetRequiredAction();
		}
		
		tfName.setText(l.getName());
		tfName.setDisable(true);
	}

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAbteil;

    @FXML
    private TextField tfPosition;

    @FXML
    void onActionAbbrechen(ActionEvent event) {
    	SceneDirector.getInstance().sceneLager();
    }

    @FXML
    void onActionSpeichern(ActionEvent event) {
    	if(!checkString(tfAbteil)) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiges Abteil", "Das Abteil darf nicht leer sein.");
    		return;
    	}
    	
    	if(!checkInt(tfPosition.getText()))  {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Position", "Die Position muss aus Zahlen bestehen.");
    		return;
    	}
    	
    	Lagerplatz lp = new Lagerplatz(l, tfAbteil.getText(), Integer.parseInt(tfPosition.getText()));
    	ProduktverwaltungDataInterface.getProduktverwaltung().addLagerplatz(lp);
    	l = null;
    	SceneDirector.getInstance().sceneLager(lp);
    }

}
