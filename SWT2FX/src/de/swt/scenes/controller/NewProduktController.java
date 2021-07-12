package de.swt.scenes.controller;

import java.time.format.DateTimeFormatter;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.obj.ProduktElektrogeraet;
import de.swt.produktverwaltung.obj.ProduktLebensmittel;
import de.swt.scenes.SceneDirector;
import de.swt.utils.VarChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NewProduktController implements VarChecker {
	
	private static final String typ[] = { "Elektrogerät", "Lebensmittel" };
	
	@FXML
	public void initialize() {
		hbDate.setVisible(false);
		hbDate.setManaged(false);
		hbPhasen.setVisible(false);
		hbPhasen.setManaged(false);
		
		cbTyp.getItems().addAll(typ);
		
		cbPhasen.getItems().add(1);
		cbPhasen.getItems().add(3);
	}

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEAN13;

    @FXML
    private TextField tfPreis;

    @FXML
    private ChoiceBox<String> cbTyp;

    @FXML
    private HBox hbDate;

    @FXML
    private DatePicker dpVerfallsdatum;

    @FXML
    private HBox hbPhasen;

    @FXML
    private ChoiceBox<Integer> cbPhasen;

    @FXML
    void onActionAbbrechen(ActionEvent event) {
    	SceneDirector.getInstance().sceneProdukte();
    }

    @FXML
    void onActionPhasen(ActionEvent event) {

    }

    @FXML
    void onActionSpeichern(ActionEvent event) {
    	if(!checkString(tfName.getText())) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiger Name", "Der Name darf nicht leer sein.");
    		return;
    	}
    	
    	if(tfEAN13.getText().length() != 13 || !checkLong(tfEAN13.getText())) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiger EAN13 Code", "Der EAN13 Code muss 13 Stellen haben und aus Zahlen bestehen.");
    		return;
    	}
    	
    	if(!checkDouble(tfPreis.getText())) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültiger Preis", "Der Preis muss ein Double sein.");
    		return;
    	}
    	
    	if(cbTyp.getSelectionModel().getSelectedItem() == null) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Fehlender Typ", "Es muss der Typ des Produkts angegeben werden.");
    		return;
    	}
    	
    	if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[0]) && cbPhasen.getSelectionModel().getSelectedItem() == null) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Fehlende Phasenangabe", "Es muss die Anzahl der Phasen angegeben werden.");
    		return;
    	}
    	
    	if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[1]) && dpVerfallsdatum.getValue() == null) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Fehlendes Verfallsdatum", "Es muss das Verfallsdatum angegeben werden.");
    		return;
    	}
    	
    	if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[0])) {
    		ProduktElektrogeraet pe = new ProduktElektrogeraet(tfName.getText(), tfEAN13.getText(), Double.parseDouble(tfPreis.getText()), cbPhasen.getValue());
    		ProduktverwaltungDataInterface.getProduktverwaltung().addProdukt(pe);
    		SceneDirector.getInstance().sceneProdukte(pe);
    		return;
    	}
    	
    	if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[1])) {
    		ProduktLebensmittel pe = new ProduktLebensmittel(tfName.getText(), tfEAN13.getText(), Double.parseDouble(tfPreis.getText()), dpVerfallsdatum.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    		ProduktverwaltungDataInterface.getProduktverwaltung().addProdukt(pe);
    		SceneDirector.getInstance().sceneProdukte(pe);
    		return;
    	}
    }

    @FXML
    void onActionTyp(ActionEvent event) {
    	if(cbTyp.getSelectionModel().getSelectedItem() == null) {
    		hbDate.setVisible(false);
    		hbDate.setManaged(false);
    		hbPhasen.setVisible(false);
    		hbPhasen.setManaged(false);
    	} else if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[0])) {
    		hbDate.setVisible(false);
    		hbDate.setManaged(false);
    		hbPhasen.setVisible(true);
    		hbPhasen.setManaged(true);
    	} else if(cbTyp.getSelectionModel().getSelectedItem().equals(typ[1])) {
    		hbDate.setVisible(true);
    		hbDate.setManaged(true);
    		hbPhasen.setVisible(false);
    		hbPhasen.setManaged(false);
    	}
    	
    }

}
