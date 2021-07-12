package de.swt.produktverwaltung.obj;

public interface IDHolder<T> {
	
	int getID();
	void setID(int id);
	
	@SuppressWarnings("unchecked")
	default T setIDWithInstance(int id) {
		setID(id);
		return (T) this;
	}
	
}
