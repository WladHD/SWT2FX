package de.swt.produktverwaltung.obj;

public class ProduktLebensmittel extends Produkt {
	private static final long serialVersionUID = 3822362852820474207L;
	
	private String verfallsdatum;
	
	public ProduktLebensmittel(String name, String ean13, double preis, String verfallsdatum) {
		super(name, ean13, preis);
		
		setVerfallsdatum(verfallsdatum);
	}
	
	public String getVerfallsdatum() {
		return verfallsdatum;
	}
	
	public void setVerfallsdatum(String verfallsdatum) {
		this.verfallsdatum = verfallsdatum;
	}

	@Override
	public String getTyp() {
		return "Lebensmittel";
	}

	@Override
	public String getZusatzinfo() {
		return "Haltbarkeit: " + getVerfallsdatum();
	}

}
