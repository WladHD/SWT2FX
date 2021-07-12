package de.swt.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	private Connection connection;

	private String url;
	private String username;
	private String password;

	public static MySQL createNewInstance() {
		return createNewInstance("localhost:3306/produktverwaltung", "root", "root");
	}

	public static MySQL createNewInstance(String url, String username, String password) {
		MySQL m = new MySQL();
		m.setPassword(password);
		m.setURL(url);
		m.setUsername(username);

		return m;
	}

	private MySQL() {
	}

	private void establishConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed())
				establishConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
