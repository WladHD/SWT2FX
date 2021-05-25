package de.swt.utils;

import javafx.scene.control.TextField;

public interface VarChecker {
	default boolean checkString(String s) {
    	return s != null && s.replaceAll(" ", "").length() != 0;
    }
    
    default boolean checkDouble(String s) {
    	try {
    		Double.parseDouble(s);
    		return true;
    	} catch (Exception ex) {
    		
    	}
    	
    	return false;
    }
    
    default boolean checkString(TextField tf) {
    	return checkString(tf.getText());
    }
    
    default boolean checkLong(String s) {
    	try {
    		Long.parseLong(s);
    		return true;
    	} catch (Exception ex) {
    		
    	}
    	
    	return false;
    }
    
    default boolean checkInt(String s) {
    	try {
    		Integer.parseInt(s);
    		return true;
    	} catch (Exception ex) {
    		
    	}
    	
    	return false;
    }
}
