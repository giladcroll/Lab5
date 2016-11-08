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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Painter {

	public static void paint(Critter C, int x_coor, int y_coor) {
		//test for painting
		double cell = Main.cell;
		CritterShape critterShape = C.viewShape();
		Shape s = null;
		if (critterShape.toString().equals("CIRCLE")){
			s = new Circle(Main.cell/2);
		}
		else if (critterShape.toString().equals("RECTANGLE")){
			s = new Rectangle(Main.cell,Main.cell);
		}
		else if (critterShape.toString().equals("TRIANGLE")){
			Polygon triangle = new Polygon();
			triangle.getPoints().addAll(new Double[]{
					(double) (Main.cell/2), 0.0,
					0.0, (double) (Main.cell),
					(double) (Main.cell), (double) (Main.cell) });
			s = triangle;
		}
		else if (critterShape.toString().equals("DIAMOND")){
			Polygon diamond = new Polygon();
			diamond.getPoints().addAll(new Double[]{
					cell*3/4, (cell/2),
					 cell/2, 0.0,
					 (cell/4), (cell/2),
					 (cell/2), (cell) });
			s = diamond;
		}
		else if (critterShape.toString().equals("STAR")){
			Polygon star= new Polygon();
			star.getPoints().addAll(new Double[]{
					(cell*.5), 0.0,
					(cell*.6), (cell*.3),
					(cell), cell*.3,
					cell*.7, cell*.6,
					cell*.8,cell,
					cell*.5,cell*.8,
					cell*.2,cell,
					cell*.3,cell*.6,
					0.0, cell*.3,
					cell*.4,cell*.3

			});
				
			s = star;
		}
		
		
		s.setFill(C.viewFillColor());		
		s.setStroke(C.viewOutlineColor()); // outline
		Main.grid.add((Shape) s, x_coor, y_coor); // add the shape to the grid.
	}
	
}
