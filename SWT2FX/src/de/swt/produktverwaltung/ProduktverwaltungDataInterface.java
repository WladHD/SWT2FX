package de.swt.produktverwaltung;

import java.util.ArrayList;
import java.util.List;

import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.ProduktElektrogeraet;
import de.swt.produktverwaltung.obj.ProduktLebensmittel;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.utils.Address;

public interface ProduktverwaltungDataInterface {
	
	public static ProduktverwaltungDataInterface getProduktverwaltung() {
		return ProduktverwaltungMySQL.getInstance();
	}

	void addLager(Lager l);
	
	void removeLager(Lager l);
	
	ArrayList<Lager> getLager();
	
	void addProdukt(Produkt p);
	
	ArrayList<Produkt> getProdukte();
	
	void addLagerplatz(Lagerplatz lp);
	
	ArrayList<Lagerplatz> getLagerplaetze();
	
	List<Lagerplatz> getLagerplaetze(Lager l);
	
	void removeLagerplatz(Lagerplatz currentLagerplatz);
	
	void removeProduktFromLagerplatz(Lagerplatz lp);
	
	void addRechnung(Rechnung r);
	
	ArrayList<Rechnung> getRechnungen();
	
	boolean saveAll(String... params);
	
	boolean loadAll(String... params);
	
	default void generateTestData() {
		addProdukt(new ProduktElektrogeraet("Waschmaschine", "0479712946614", 230, 1));
		addProdukt(new ProduktLebensmittel("Brot", "3753402512684", 2.50, "26.07.2021"));
		
		addLager(new Lager("Hauptlager", new Address("Deutschland", "NRW", 44149, "Dortmund", "Schˆne Straﬂe", 12)));
		addLager(new Lager("Nebenlager", new Address("Deutschland", "NRW", 44149, "Dortmund", "Blaue Straﬂe", 2)));
		addLager(new Lager("Gesch‰ft", new Address("Deutschland", "NRW", 44149, "Dortmund", "Gesch‰ftsstraﬂe", 11)));
		
		addLagerplatz(new Lagerplatz(getLager().get(0), "A", 1, new Produktanzahl(getProdukte().get(0), 15)));
		addLagerplatz(new Lagerplatz(getLager().get(0), "A", 2));
		
		Rechnung r = new Rechnung("25.05.2021", "19:20");
		r.addProdukt(new Produktanzahl(getProdukte().get(0), 15));
		r.addProdukt(new Produktanzahl(getProdukte().get(1), 60));
		addRechnung(r);
		
		Rechnung r2 = new Rechnung("24.06.2021", "19:20");
		r2.addProdukt(new Produktanzahl(getProdukte().get(0), 15));
		r2.addProdukt(new Produktanzahl(getProdukte().get(1), 60));
		
		addRechnung(r2);
		
		Rechnung r3 = new Rechnung("26.04.2021", "19:20");
		r3.addProdukt(new Produktanzahl(getProdukte().get(0), 1));
		r3.addProdukt(new Produktanzahl(getProdukte().get(1), 1));
		
		addRechnung(r3);
		
	}
	
}
