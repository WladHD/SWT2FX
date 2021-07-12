package de.swt.scenes;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import de.swt.Main;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.scenedirector.RequiredAction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class SceneDirector {
	
	private static SceneDirector inst = new SceneDirector();
	
	public static SceneDirector getInstance() {
		return inst;
	}
	
	private SceneDirector() {}	
	public static RequiredAction _requiredAction = RequiredAction.NONE;
	public static Object _requiredActionData;
	
	public static void _resetRequiredAction() {
		_setRequiredAction(RequiredAction.NONE, null);
	}
	
	public static void _setRequiredAction(RequiredAction ra, Object o) {
		_requiredAction = ra;
		_requiredActionData = o;
	}
	
	public void sceneNeuesLager() {
		loadScene("NewLager");
	}
	
	public void sceneProduktLagerplatz(SimpleEntry<Produkt, Lagerplatz> p) {
		_setRequiredAction(RequiredAction.LAGERPLATZ_PRODUKT_AMOUNT, p);
		loadScene("ProduktLagerplatz");
	}
	
	public void sceneLagerAnzeigen(Lager lagerId) {
		_setRequiredAction(RequiredAction.LAGER_SHOW_LAGER, lagerId);
		sceneNeuesLager();
	}
	
	public void sceneNeuerLagerplatz(Lager lagerId) {
		_setRequiredAction(RequiredAction.LAGER_NEW_LAGERPLATZ, lagerId);
		loadScene("NewLagerplatz");
	}
	
	public void sceneNeueRechnung() {
		loadScene("NewRechnung");
	}
	
	public void sceneNeuesProdukt() {
		loadScene("NewProdukt");
	}
	
	public void sceneRechnung() {
		loadScene("Rechnung");
	}
	
	public void sceneRechnung(Rechnung r) {
		_setRequiredAction(RequiredAction.RECHNUNG_SHOW_RECHNUNG, r);
		sceneRechnung();
	}
	
	public void sceneLager() {
		loadScene("Lager");
	}
	
	public void sceneLager(Lagerplatz lp) {
		_setRequiredAction(RequiredAction.LAGER_SHOW_LAGERPLATZ, lp);
		sceneLager();
	}
	
	public void sceneProdukte() {
		loadScene("Produkt");
	}
	
	public void sceneProdukte(Lagerplatz lagerplatzId) {
		_setRequiredAction(RequiredAction.PRODUKT_NEED_SELECTED_PRODUKT, lagerplatzId);
		sceneProdukte();
	}
	
	public void sceneProdukte(Produkt p) {
		_setRequiredAction(RequiredAction.PRODUKT_SHOW_PRODUKT, p);
		sceneProdukte();
	}
	
	private void loadScene(String fxml) {
		try {
			Main.getRoot().setCenter(loadPane(fxml + ".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Main.getRoot().requestFocus();
	}
	
	private Pane loadPane(String res) throws IOException {
    	return FXMLLoader.load(getClass().getResource(res));
    }
	
	public void showAlert(AlertType al, String header, String text) {
    	Alert a = new Alert(al);
    	a.setHeaderText(header);
    	a.setContentText(text);
    	a.show();
    }
	
}
