package de.swt.produktverwaltung;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;

public class ProduktverwaltungLocal implements Serializable, ProduktverwaltungDataInterface{
	private static final long serialVersionUID = 7622433727458951311L;
	private static ProduktverwaltungLocal inst = new ProduktverwaltungLocal();
	
	protected static ProduktverwaltungLocal getInstance() {
		return inst;
	}
	
	private ProduktverwaltungLocal() {}
	
	private ArrayList<Produkt> produkte = new ArrayList<>();
	private ArrayList<Lager> lager = new ArrayList<>();
	private ArrayList<Lagerplatz> lagerplaetze = new ArrayList<>();
	private ArrayList<Rechnung> rechnungen = new ArrayList<>();
	
	public void addLager(Lager l) {
		lager.add(l);
	}
	
	public void removeLager(Lager l) {
		ArrayList<Lagerplatz> rem = new ArrayList<>();
		
		for(Lagerplatz lp : getLagerplaetze()) {
			lp.setProduktanzahl(null);
			lp.setLager(null);
			rem.add(lp);
		}
		
		for(Lagerplatz lp : rem)
			getLagerplaetze().remove(lp);
		
		getLager().remove(l);
	}
	
	public ArrayList<Lager> getLager() {
		return lager;
	}
	
	public void addProdukt(Produkt p) {
		produkte.add(p);
	}
	
	public ArrayList<Produkt> getProdukte() {
		return produkte;
	}
	
	public void addLagerplatz(Lagerplatz lp) {
		lagerplaetze.add(lp);
	}
	
	public ArrayList<Lagerplatz> getLagerplaetze() {
		return lagerplaetze;
	}
	
	public List<Lagerplatz> getLagerplaetze(Lager l) {
		return lagerplaetze.stream().filter(x -> x.getLager() == l).collect(Collectors.toList());
	}
	
	public void removeLagerplatz(Lagerplatz currentLagerplatz) {
		lagerplaetze.remove(currentLagerplatz);
	}
	
	public void removeProduktFromLagerplatz(Lagerplatz lp) {
		lp.setProduktanzahl(null);
	}
	
	public void addRechnung(Rechnung r) {
		rechnungen.add(r);
	}
	
	public ArrayList<Rechnung> getRechnungen() {
		return rechnungen;
	}
	
	public boolean serialize(String location) {
		try(FileOutputStream fos = new FileOutputStream(location);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deserialize(String location) {
		try(FileInputStream fis = new FileInputStream(location);
				ObjectInputStream  ois = new ObjectInputStream (fis)) {
			inst = (ProduktverwaltungLocal) ois.readObject();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean saveAll(String... params) {
		return serialize(params[0]);
	}

	@Override
	public boolean loadAll(String... params) {
		return deserialize(params[0]);
	}

	@Override
	public void setProduktanzahlForLagerplatz(Produktanzahl pa, Lagerplatz lp) {
		lp.setProduktanzahl(pa);
	}

	
}
