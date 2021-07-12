package de.swt.produktverwaltung.obj;

import java.io.Serializable;

public class Lagerplatz implements Serializable, IDHolder<Lagerplatz> {
	private static final long serialVersionUID = 7795881023915039552L;
	private static int gid = 0;

	private String abteil;
	private int position;
	private Produktanzahl produktanzahl;
	private Lager lager;
	private int id = gid++;

	public Lagerplatz(Lager l, String abteil, int position) {
		this(l, abteil, position, null);
	}

	public Lagerplatz(Lager l, String abteil, int position, Produktanzahl pa) {
		setLager(l);
		setAbteil(abteil);
		setPosition(position);
		setProduktanzahl(pa);
	}

	public String getAbteil() {
		return abteil;
	}

	public void setAbteil(String abteil) {
		this.abteil = abteil;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Produktanzahl getProduktanzahl() {
		return produktanzahl;
	}
	
	public boolean hasProduktanzahl() {
		return getProduktanzahl() != null;
	}

	public void setProduktanzahl(Produktanzahl produktanzahl) {
		this.produktanzahl = produktanzahl;
	}

	public Lager getLager() {
		return lager;
	}

	public void setLager(Lager lager) {
		this.lager = lager;
	}

	public String toString() {
		return abteil + "#" + position + " (" + (getProduktanzahl() == null ? "frei" : "belegt") + ")";
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
