package de.swt.scenes.controller;

import java.util.AbstractMap.SimpleEntry;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.scenedirector.RequiredAction;
import de.swt.scenes.SceneDirector;
import de.swt.utils.VarChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ProduktLagerplatzController implements VarChecker {
	
	private SimpleEntry<Produkt, Lagerplatz> pair;
	
	@FXML
	public void initialize() {
		_load();
		
		tfProdukt.setText(pair.getKey().getName());
		tfProdukt.setDisable(true);
		
		tfLager.setText(pair.getValue().getLager().getName());
		tfLager.setDisable(true);
		
		tfLagerplatz.setText(pair.getValue().toString());
		tfLagerplatz.setDisable(true);
		
		if(pair.getValue().getProduktanzahl() != null && pair.getValue().getProduktanzahl().getProdukt() == pair.getKey())
			tfAnzahl.setText(pair.getValue().getProduktanzahl().getAnzahl() + "");
	}
	
	@SuppressWarnings("unchecked")
	private void _load() {
		if(SceneDirector._requiredAction == RequiredAction.LAGERPLATZ_PRODUKT_AMOUNT) {
			pair = (SimpleEntry<Produkt, Lagerplatz>) SceneDirector._requiredActionData;
			SceneDirector._resetRequiredAction();
		}
	}

    @FXML
    private TextField tfProdukt;

    @FXML
    private TextField tfLager;

    @FXML
    private TextField tfLagerplatz;

    @FXML
    private TextField tfAnzahl;

    @FXML
    void onActionAbbrechen(ActionEvent event) {
    	SceneDirector.getInstance().sceneLager(pair.getValue());
    }

    @FXML
    void onActionSpeichern(ActionEvent event) {
    	if(!checkInt(tfAnzahl.getText())) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Anzahl", "Die Anzahl muss eine Zahl sein.");
    		return;
    	}
    	
    	int anzahl = Integer.parseInt(tfAnzahl.getText());
    	
    	if(anzahl <= 0) {
    		SceneDirector.getInstance().showAlert(AlertType.ERROR, "Ungültige Anzahl", "Die Anzahl muss mindestens 1 sein.");
    		return;
    	}
    	
    	ProduktverwaltungDataInterface.getProduktverwaltung().setProduktanzahlForLagerplatz(new Produktanzahl(pair.getKey(), anzahl), pair.getValue());
    	
    	
    	SceneDirector.getInstance().sceneLager(pair.getValue());
    }

}
