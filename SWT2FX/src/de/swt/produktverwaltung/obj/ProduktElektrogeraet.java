package de.swt.produktverwaltung.obj;

public class ProduktElektrogeraet extends Produkt {
	private static final long serialVersionUID = -6188676952520343475L;
	
	private int phasen;
	
	public ProduktElektrogeraet(String name, String ean13, double preis, int phasen) {
		super(name, ean13, preis);
		
		setPhasen(phasen);
	}
	
	public int getPhasen() {
		return phasen;
	}
	
	public void setPhasen(int phasen) {
		this.phasen = phasen;
	}

	@Override
	public String getTyp() {
		return ProduktTyp.ELEKTROGERAET.toString();
	}

	@Override
	public String getZusatzinfo() {
		return "Phasen: " + getPhasen();
	}
}
