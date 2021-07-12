package de.swt.produktverwaltung.obj;

import java.io.Serializable;

import de.swt.utils.Address;

public class Lager implements Serializable, IDHolder<Lager> {
	private static final long serialVersionUID = 3720489565964922314L;
	private static int gid = 0;

	public Lager(String name, Address address) {
		setAddress(address);
		setName(name);
	}
	
	private int id = gid++;
	private Address address;
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String toString() {
		return name;
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
