package de.swt.produktverwaltung;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.swt.mysql.MySQL;
import de.swt.produktverwaltung.obj.Lager;
import de.swt.produktverwaltung.obj.Lagerplatz;
import de.swt.produktverwaltung.obj.Produkt;
import de.swt.produktverwaltung.obj.ProduktElektrogeraet;
import de.swt.produktverwaltung.obj.ProduktLebensmittel;
import de.swt.produktverwaltung.obj.ProduktTyp;
import de.swt.produktverwaltung.obj.Produktanzahl;
import de.swt.produktverwaltung.obj.Rechnung;
import de.swt.utils.Address;

public class ProduktverwaltungMySQL implements Serializable, ProduktverwaltungDataInterface {
	private static final long serialVersionUID = 7622433727458951311L;
	private static ProduktverwaltungMySQL inst = new ProduktverwaltungMySQL();

	protected static ProduktverwaltungMySQL getInstance() {
		return inst;
	}

	private ProduktverwaltungMySQL() {
	}
	
	private int getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		
		return -1;
	}

	public void addLager(Lager l) {
		String ad = "INSERT INTO address (country, state, postCode, city, street, houseNr) values (?, ?, ?, ?, ?, ?)";
		
		MySQL m = MySQL.createNewInstance();

		try {
			PreparedStatement s = m.getConnection().prepareStatement(ad,
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, l.getAddress().getCountry());
			s.setString(2, l.getAddress().getState());
			s.setInt(3, l.getAddress().getPostCode());
			s.setString(4, l.getAddress().getCity());
			s.setString(5, l.getAddress().getStreet());
			s.setInt(6, l.getAddress().getHouseNr());

			s.execute();
			
			l.getAddress().setID(getGeneratedKey(s.getGeneratedKeys()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String lag = "INSERT INTO lager (lagerName, addressId) values (?, ?)";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(lag,
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, l.getName());
			s.setInt(2, l.getAddress().getID());
			
			s.execute();

			l.setID(getGeneratedKey(s.getGeneratedKeys()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public void removeLager(Lager l) {
		MySQL m = MySQL.createNewInstance();
		
		String rem = "DELETE FROM lager WHERE lagerId = ?";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(rem);
			s.setInt(1, l.getID());
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public ArrayList<Lager> getLager() {
		MySQL m = MySQL.createNewInstance();
		
		ArrayList<Lager> lager = new ArrayList<Lager>();
		String sel = "SELECT * FROM lager JOIN address USING(addressId)";
		try {
			Statement s = m.getConnection().createStatement();
			ResultSet rs = s.executeQuery(sel);

			while (rs.next()) {
				lager.add(constructLager(rs));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();

		return lager;
	}

	private Lager constructLager(ResultSet rs) throws SQLException {
		return new Lager(rs.getString("lagerName"),
				new Address(rs.getString("country"), rs.getString("state"), rs.getInt("postCode"), rs.getString("city"),
						rs.getString("street"), rs.getInt("houseNr")).setIDWithInstance(rs.getInt("addressId")))
								.setIDWithInstance(rs.getInt("lagerId"));
	}

	public void addProdukt(Produkt p) {
		MySQL m = MySQL.createNewInstance();
		String pr = "INSERT INTO produkt (produktName, ean13, preis, typ, zusatzinfo) values (?, ?, ?, ?, ?)";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(pr,
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, p.getName());
			s.setString(2, p.getEAN13());
			s.setDouble(3, p.getPreis());
			s.setString(4, p.getTyp());
			s.setString(5, p.getZusatzinfo());

			s.execute();
			
			p.setID(getGeneratedKey(s.getGeneratedKeys()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public ArrayList<Produkt> getProdukte() {
		MySQL m = MySQL.createNewInstance();
		ArrayList<Produkt> produkte = new ArrayList<Produkt>();
		String sel = "SELECT * FROM produkt";
		try {
			Statement s = m.getConnection().createStatement();
			ResultSet rs = s.executeQuery(sel);

			while (rs.next())
				produkte.add(constructProdukt(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();

		return produkte;
	}

	private Produkt constructProdukt(ResultSet rs) throws NumberFormatException, SQLException {
		switch (ProduktTyp.parse(rs.getString("typ"))) {
		case ELEKTROGERAET:
			return new ProduktElektrogeraet(rs.getString("produktName"), rs.getString("ean13"), rs.getDouble("preis"),
					Integer.parseInt(rs.getString("zusatzinfo").split(": ")[1]))
							.setIDWithInstance(rs.getInt("produktId"));
		case LEBENSMITTEL:
			return new ProduktLebensmittel(rs.getString("produktName"), rs.getString("ean13"), rs.getDouble("preis"),
					rs.getString("zusatzinfo").split(": ")[1]).setIDWithInstance(rs.getInt("produktId"));
		default:
			System.err.println("Produkt ohne Typ");
			break;
		}

		return null;
	}

	public void addProduktanzahl(Produktanzahl p, Rechnung r) {
		MySQL m = MySQL.createNewInstance();
		
		String pa = r == null ? "INSERT INTO produktanzahl (produktId, anzahl) values (?, ?)"
				: "INSERT INTO produktanzahl (produktId, anzahl, rechnungId) values (?, ?, ?)";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(pa,
					Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, p.getProdukt().getID());
			s.setInt(2, p.getAnzahl());

			if (r != null)
				s.setInt(3, r.getID());

			s.execute();

			p.setID(getGeneratedKey(s.getGeneratedKeys()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	private Produktanzahl constructProduktanzahl(ResultSet rs) throws NumberFormatException, SQLException {
		if (rs.getString("produktName") == null) 
			return null;
		
			
		return new Produktanzahl(constructProdukt(rs), rs.getInt("anzahl"))
				.setIDWithInstance(rs.getInt("produktanzahlId"));
	}

	public void addLagerplatz(Lagerplatz lp) {
		MySQL m = MySQL.createNewInstance();
		
		if (lp.hasProduktanzahl())
			addProduktanzahl(lp.getProduktanzahl(), null);

		String lag = lp.hasProduktanzahl()
				? "INSERT INTO lagerplatz (abteil, position, lagerId, produktanzahlId) values (?, ?, ?, ?)"
				: "INSERT INTO lagerplatz (abteil, position, lagerId) values (?, ?, ?)";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(lag,
					Statement.RETURN_GENERATED_KEYS);

			s.setString(1, lp.getAbteil());
			s.setInt(2, lp.getPosition());
			s.setInt(3, lp.getLager().getID());

			if (lp.hasProduktanzahl())
				s.setInt(4, lp.getProduktanzahl().getID());
			
			s.execute();

			lp.setID(getGeneratedKey(s.getGeneratedKeys()));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public ArrayList<Lagerplatz> getLagerplaetze() {
		MySQL m = MySQL.createNewInstance();
		
		ArrayList<Lagerplatz> lagerplaetze = new ArrayList<Lagerplatz>();

		String lagp = "SELECT * FROM lagerplatz JOIN lager USING(lagerID) JOIN address USING(addressId) LEFT JOIN produktanzahl USING(produktanzahlId) LEFT JOIN produkt USING(produktId)";

		try {
			Statement s = m.getConnection().createStatement();
			ResultSet rs = s.executeQuery(lagp);

			while (rs.next())
				lagerplaetze.add(new Lagerplatz(constructLager(rs), rs.getString("abteil"), rs.getInt("position"),
						constructProduktanzahl(rs)).setIDWithInstance(rs.getInt("lagerplatzId")));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();

		return lagerplaetze;
	}

	public List<Lagerplatz> getLagerplaetze(Lager l) {
		return getLagerplaetze().stream().filter(x -> x.getLager().getID() == l.getID()).collect(Collectors.toList());
	}

	public void removeLagerplatz(Lagerplatz currentLagerplatz) {
		MySQL m = MySQL.createNewInstance();
		
		String lagp = "DELETE FROM lagerplatz WHERE lagerplatzId = ?";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(lagp);
			s.setInt(1, currentLagerplatz.getID());
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public void removeProduktFromLagerplatz(Lagerplatz lp) {
		MySQL m = MySQL.createNewInstance();
		
		String prod = "DELETE FROM produktanzahl WHERE produktanzahlId = ?";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(prod);
			s.setInt(1, lp.getProduktanzahl().getID());
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		lp.setProduktanzahl(null);
		
		m.close();
	}
	
	@Override
	public void setProduktanzahlForLagerplatz(Produktanzahl pa, Lagerplatz lp) {
		MySQL m = MySQL.createNewInstance();
		
		if(lp.hasProduktanzahl())
			removeProduktFromLagerplatz(lp);
		
		addProduktanzahl(pa, null);
		
		String update = "UPDATE lagerplatz SET produktanzahlId = ? WHERE lagerplatzId = ?";
		
		try {
			PreparedStatement s = m.getConnection().prepareStatement(update);
			s.setInt(1, pa.getID());
			s.setInt(2, lp.getID());
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();
	}

	public void addRechnung(Rechnung r) {
		MySQL m = MySQL.createNewInstance();
		
		String rech = "INSERT INTO rechnung (datum, zeit) values (?, ?)";

		try {
			PreparedStatement s = m.getConnection().prepareStatement(rech,
					Statement.RETURN_GENERATED_KEYS);

			s.setString(1, r.getDatum());
			s.setString(2, r.getZeit());
			
			s.execute();

			r.setID(getGeneratedKey(s.getGeneratedKeys()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Produktanzahl pa : r.getProdukte())
			addProduktanzahl(pa, r);
		
		m.close();
	}

	public ArrayList<Rechnung> getRechnungen() {
		MySQL m = MySQL.createNewInstance();
		
		ArrayList<Rechnung> rechnungen = new ArrayList<Rechnung>();

		String rech = "SELECT * FROM rechnung LEFT JOIN produktanzahl USING(rechnungId) JOIN produkt USING(produktId)";

		try {
			Statement s = m.getConnection().createStatement();
			ResultSet rs = s.executeQuery(rech);

			while (rs.next()) {
				Rechnung r = findRechnung(rechnungen, rs.getInt("rechnungId"));
				
				if(r == null) {
					r = new Rechnung(rs.getString("datum"), rs.getString("zeit")).setIDWithInstance(rs.getInt("rechnungId"));
					rechnungen.add(r);
				}
				
				Produktanzahl pa = constructProduktanzahl(rs);
				
				if(pa != null)
					r.addProdukt(pa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.close();

		return rechnungen;
	}

	private Rechnung findRechnung(ArrayList<Rechnung> r, int id) {
		for (Rechnung re : r)
			if (re.getID() == id)
				return re;
		return null;
	}

	@Override
	public boolean saveAll(String... params) {
		return false;
	}

	@Override
	public boolean loadAll(String... params) {
		return false;
	}

}
