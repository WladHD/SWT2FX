module SWT2FX {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.base;
	requires java.sql;
	
	opens de.swt to javafx.graphics, javafx.fxml, javafx.base;
	opens de.swt.produktverwaltung.obj to javafx.graphics, javafx.fxml, javafx.base;
	opens de.swt.scenes.controller to javafx.graphics, javafx.fxml, javafx.base;
}
