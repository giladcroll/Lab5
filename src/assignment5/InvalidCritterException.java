/* CRITTERS InvalidCritterException.java
 * EE422C Project 5 submission by
 * Gilad Croll
 * gc24654
 * 16445
 * Alejandro Stevenson-Duran
 * as72948
 * 16455
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5;

public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
