package de.swt.test;

import java.sql.SQLException;

import de.swt.mysql.MySQL;
import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import de.swt.produktverwaltung.ProduktverwaltungMySQL;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.utils.Address;
import junit.framework.TestCase;

public class JUnitTest extends TestCase {

	public JUnitTest(String name) {
		super(name);
	}
	
	// Test der Datenhaltung
	public void testWeiterleitungZurRichtigenKlasse() {
		ProduktverwaltungDataInterface pdi = ProduktverwaltungDataInterface.getProduktverwaltung();
		
		assertTrue(pdi instanceof ProduktverwaltungMySQL);
	}
	
	public void testVerbindungZuMySQL() {
		MySQL s = MySQL.createNewInstance();
		
		try {
			assertTrue(!s.getConnection().isClosed());
		} catch (SQLException e) {
			assertTrue(false);
		} finally {
			 s.close();
		}
	}
	
	
	public void testListen() {
		assertNotNull(ProduktverwaltungDataInterface.getProduktverwaltung().getRechnungen());
		assertNotNull(ProduktverwaltungDataInterface.getProduktverwaltung().getLager());
		assertNotNull(ProduktverwaltungDataInterface.getProduktverwaltung().getLagerplaetze());
		assertNotNull(ProduktverwaltungDataInterface.getProduktverwaltung().getProdukte());
	}
	
	// Test der Fachlogik
	public void testHinzufügenUndLöschenEinesLagers() {
		Lager l = new Lager("TestGebäude", new Address("Test", "Test", 12345, "Test", "Test", 321));
		
		ProduktverwaltungDataInterface.getProduktverwaltung().addLager(l);
		
		for(Lagerplatz lp : ProduktverwaltungDataInterface.getProduktverwaltung().getLagerplaetze())
			if(l.getID() == lp.getID()) {
				assertSame(l, lp);
				break;
			}
		
		ProduktverwaltungDataInterface.getProduktverwaltung().removeLager(l);
		
		boolean exists = false;
		
		for(Lagerplatz lp : ProduktverwaltungDataInterface.getProduktverwaltung().getLagerplaetze())
			if(l.getID() == lp.getID()) {
				exists = true;
				break;
			}
		
		assertFalse(exists);	
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JUnitTest.class);
	}
}
