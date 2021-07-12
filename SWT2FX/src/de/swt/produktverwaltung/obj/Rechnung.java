package de.swt.produktverwaltung.obj;

import java.io.Serializable;
import java.util.ArrayList;

public class Rechnung implements Serializable, IDHolder<Rechnung> {
	private static final long serialVersionUID = -6579963289455951348L;
	private static int gid = 0;
	private int id = gid++;
	
	private String datum;
	private String zeit;
	private ArrayList<Produktanzahl> produkte = new ArrayList<>();
	
	public Rechnung(String datum, String zeit) {
		setDatum(datum);
		setZeit(zeit);
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public String getZeit() {
		return zeit;
	}
	
	public void setZeit(String zeit) {
		this.zeit = zeit;
	}
	
	public double getSumme() {
		double a = 0;
		for(Produktanzahl pa : getProdukte())
			a += pa.getAnzahl() * pa.getProdukt().getPreis();
		
		return a;
	}
	
	public ArrayList<Produktanzahl> getProdukte() {
		return produkte;
	}
	
	public void addProdukt(Produktanzahl pa) {
		getProdukte().add(pa);
	}
	
	public String toString() {
		return datum + " " + zeit + " @ " + getSumme() + " €";
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
