package de.swt.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	private static MySQL _inst = new MySQL();

	public static MySQL getInstance() {
		return _inst;
	}

	private Connection connection;

	private MySQL() {
		establishConnection();
	}

	private void establishConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/produktverwaltung", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			if (connection.isClosed())
				establishConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

}
