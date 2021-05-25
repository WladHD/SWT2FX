package de.swt.produktverwaltung.obj;

import java.io.Serializable;

public class Produktanzahl implements Serializable{
	private static final long serialVersionUID = 4650285157889926648L;
	
	private Produkt produkt;
	
	public Produktanzahl(Produkt produkt, int anzahl) {
		setProdukt(produkt);
		setAnzahl(anzahl);
	}
	
	private int anzahl;
	
	public int getAnzahl() {
		return anzahl;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public Produkt getProdukt() {
		return produkt;
	}
	
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}
	
	public double getGesamtsumme() {
		return getProdukt().getPreis() * getAnzahl();
	}
	
	public double getEinzelpreis() {
		return getProdukt().getPreis();
	}
}

// Entwurfsentscheidung
// Produkt als Oberklasse
// Weiteres komplexes Datentyp
