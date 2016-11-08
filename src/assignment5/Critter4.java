/* CRITTERS Critter4.java
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

import assignment5.Critter.CritterShape;

/**
 * @author Alejandro Stevenson-Duran
 *	this critter's behavior is determined by its current temper.
 *	mad - likes to fight
 *	romantic - tries to reproduce, asexually of course
 *	tired - no walking
 *	their color affects walking/running/fighting
 *	never runs
 */
public class Critter4 extends Critter {
	
	private int dir;	// direction of walking/running
	private final String[] tempers = {"mad", "tired", "romantic"}; 
	private String currTemper;
	
	public Critter4(){
		dir =  Critter.getRandomInt(8);	// set random initial direction
		int index = Critter.getRandomInt(3); // get random value from 0-2
		currTemper = tempers[index];
	}
	
	public String toString() { return "4"; }
	
	
	@Override
	public void doTimeStep() {
		if(currTemper.equals("tired") || (look(dir,false).equals("@"))){	
			// do nothing
		}
		else{
			walk(dir);
		}
		if (currTemper.equals("romantic") ){	// make a child
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}
		dir =  Critter.getRandomInt(8);	// set random direction
		int new_index = Critter.getRandomInt(3); // get random value from 0-2
		currTemper = tempers[new_index];
	}

	@Override
	public boolean fight(String opponent) {
		if (currTemper.equals("mad") || opponent.equals("@")){	// fights only if mad, unless opponent is algae
			return true;
		}
		else {
			walk(dir);
			return false;
			}
	}
	
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.DARKCYAN;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	}

}
