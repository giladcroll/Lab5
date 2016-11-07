/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2015
 */
package assignment5;

import java.lang.reflect.Method;
import java.util.List;

import assignment5.Critter.CritterShape;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Painter {

	/*
	 * Returns a square or a circle, according to shapeIndex
	 */
	static Shape getIcon(int shapeIndex) {
		Shape s = null;
		int size = Main.cell;
		
		switch(shapeIndex) {
		case 0: s = new Rectangle(size, size); 
			s.setFill(javafx.scene.paint.Color.RED); break;
			 
		case 1: s = new Circle(size/2); 
			s.setFill(javafx.scene.paint.Color.GREEN); break;
		}
		// set the outline of the shape
		s.setStroke(javafx.scene.paint.Color.BLUE); // outline
		return s;
	}

	public static void paint(String S, int x_coor, int y_coor) {
		int color=0;
		if (S.equals("@"))
			color = 1;
		Shape s = getIcon(color);	// convert the index to an icon.
			Main.grid.add(s, x_coor, y_coor); // add the shape to the grid.
		}

	
	/*
	 * Paints the shape on a grid.
	public static void paint() {
		Main.grid.getChildren().clear(); // clean up grid.
		for (int i = 0; i <= 1; i++) {
			Shape s = getIcon(i);	// convert the index to an icon.
			Main.grid.add(s, i, i); // add the shape to the grid.
		}
	}
	*/
	
}
