package de.swt.produktverwaltung.obj;

public enum ProduktTyp {
	ELEKTROGERAET("Elektrogerät"),
	LEBENSMITTEL("Lebensmittel");
	
	private String typ;
	
	private ProduktTyp(String typ) {
		this.typ = typ;
	}
	
	public String toString() {
		return typ;
	}
	
	public static ProduktTyp parse(String search) {
		for(ProduktTyp p : values())
			if(p.toString().equals(search))
				return p;
		
		return null;
	}
}
