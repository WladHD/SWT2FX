package de.swt.produktverwaltung.obj;

import java.io.Serializable;

import de.swt.produktverwaltung.Produktverwaltung;

public abstract class Produkt implements Serializable {
	private static final long serialVersionUID = -5258425831740351362L;
	
	private String name;
	private String ean13;
	private double preis;
	
	public Produkt(String name, String ean13, double preis) {
		setName(name);
		setEAN13(ean13);
		setPreis(preis);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEAN13() {
		return ean13;
	}
	
	public void setEAN13(String ean13) {
		this.ean13 = ean13;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	public abstract String getTyp();
	
	public abstract String getZusatzinfo();
	
	public int getAnzahlInLager() {
		return Produktverwaltung.getInstance().getLagerplaetze().stream().filter(x -> x.getProduktanzahl() != null && x.getProduktanzahl().getProdukt() == this).map(x -> x.getProduktanzahl().getAnzahl()).reduce(0, Integer::sum);
	}
	
	public int getAnzahlDerKaeufe() {
		int a = 0;
		
		for(Rechnung r : Produktverwaltung.getInstance().getRechnungen())
			for(Produktanzahl pa : r.getProdukte())
				if(pa.getProdukt() == this)
					a += pa.getAnzahl();
		
		return a;
	}
	
	public String toString() {
		return name;
	}
}
