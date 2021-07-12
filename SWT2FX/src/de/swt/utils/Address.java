package de.swt.utils;

import java.io.Serializable;

import de.swt.produktverwaltung.obj.IDHolder;

public class Address implements Serializable, IDHolder<Address> {
	private static final long serialVersionUID = 5673261964574338187L;
	private static int gid = 0;
	private int id = gid++;
	private String country;
	private String state;
	private int postCode;
	private String city;
	private String street;
	private int houseNr;
	
	public Address(String country, String state, int postCode, String city, String street, int houseNr) {
		setCountry(country);
		setState(state);
		setPostCode(postCode);
		setCity(city);
		setStreet(street);
		setHouseNr(houseNr);
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public int getPostCode() {
		return postCode;
	}

	public String getStreet() {
		return street;
	}

	public int getHouseNr() {
		return houseNr;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setHouseNr(int houseNr) {
		this.houseNr = houseNr;
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
