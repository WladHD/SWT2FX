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
import de.swt.produktverwaltung.obj.ProduktElektrogeraet;
import de.swt.produktverwaltung.obj.ProduktLebensmittel;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.utils.Address;

public class Produktverwaltung implements Serializable {
	private static final long serialVersionUID = 7622433727458951311L;
	private static Produktverwaltung inst = new Produktverwaltung();
	
	public static Produktverwaltung getInstance() {
		return inst;
	}
	
	private Produktverwaltung() {}
	
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
	
	
	// TODO remove
	public void generateTestData() {
		produkte.add(new ProduktElektrogeraet("Waschmaschine", "0479712946614", 230, 1));
		produkte.add(new ProduktLebensmittel("Brot", "3753402512684", 2.50, "26.07.2021"));
		
		lager.add(new Lager("Hauptlager", new Address("Deutschland", "NRW", 44149, "Dortmund", "Schˆne Straﬂe", 12)));
		lager.add(new Lager("Nebenlager", new Address("Deutschland", "NRW", 44149, "Dortmund", "Blaue Straﬂe", 2)));
		lager.add(new Lager("Gesch‰ft", new Address("Deutschland", "NRW", 44149, "Dortmund", "Gesch‰ftsstraﬂe", 11)));
		
		lagerplaetze.add(new Lagerplatz(lager.get(0), "A", 1, new Produktanzahl(produkte.get(0), 15)));
		lagerplaetze.add(new Lagerplatz(lager.get(0), "A", 2));
		
		Rechnung r = new Rechnung("25.05.2021", "19:20");
		r.addProdukt(new Produktanzahl(produkte.get(0), 15));
		r.addProdukt(new Produktanzahl(produkte.get(1), 60));
		rechnungen.add(r);
		
		Rechnung r2 = new Rechnung("24.06.2021", "19:20");
		r2.addProdukt(new Produktanzahl(produkte.get(0), 15));
		r2.addProdukt(new Produktanzahl(produkte.get(1), 60));
		
		rechnungen.add(r2);
		
		Rechnung r3 = new Rechnung("26.04.2021", "19:20");
		r3.addProdukt(new Produktanzahl(produkte.get(0), 1));
		r3.addProdukt(new Produktanzahl(produkte.get(1), 1));
		
		rechnungen.add(r3);
		
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
			inst = (Produktverwaltung) ois.readObject();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
