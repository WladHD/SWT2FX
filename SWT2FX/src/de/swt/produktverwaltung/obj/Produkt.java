package de.swt.produktverwaltung.obj;

import java.io.Serializable;

import de.swt.produktverwaltung.ProduktverwaltungDataInterface;

public abstract class Produkt implements Serializable, IDHolder<Produkt> {
	private static final long serialVersionUID = -5258425831740351362L;
	private static int gid = 0;
	
	private String name;
	private String ean13;
	private double preis;
	private int id = gid++;
	
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
		return ProduktverwaltungDataInterface.getProduktverwaltung().getLagerplaetze().stream().filter(x -> x.getProduktanzahl() != null && x.getProduktanzahl().getProdukt().getID() == getID()).map(x -> x.getProduktanzahl().getAnzahl()).reduce(0, Integer::sum);
	}
	
	public int getAnzahlDerKaeufe() {
		int a = 0;
		
		for(Rechnung r : ProduktverwaltungDataInterface.getProduktverwaltung().getRechnungen())
			for(Produktanzahl pa : r.getProdukte())
				if(pa.getProdukt().getID() == getID())
					a += pa.getAnzahl();
		
		return a;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public void setID(int id) {
		this.id = id;
	}
}
