/* CRITTERS Critter1.java
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
 * @author Gilad Croll
 *	Critter1 never stands still, always runs or walks. he only walk in directions 0-3.
 *	He does not fight he's own kind. He reproduces only when facing 0 direction, and having the necessary minimum.
 *  This Critter also has an hungry field, which makes him decide wethether or not to eat an algae. when it does, the hunger level goes down. 
 */
public class Critter1 extends Critter2 {
	
	private int dir;	// direction of walking/running
	private int hungry;
	
	public Critter1(){
		dir =  Critter.getRandomInt(4);	// set random initial direction
		hungry =5;
	}
	
	public String toString() { return "1"; }
	
	
	@Override
	public void doTimeStep() {
		if (Critter.getRandomInt(8) < 5 && (look(dir,false).equals(null))){	
			walk(dir);
		}
		else
			run(dir);
		if ( (getEnergy()> (Params.min_reproduce_energy)) && (dir ==0) ){	// make a child if going at 0 direction and have enoughe energy
			Critter1 child = new Critter1();
			//reproduce(child, Critter.getRandomInt(8));
		}
		if (hungry<10)
			hungry++;
		dir =  Critter.getRandomInt(4);	// set random initial direction
	}

	@Override
	public boolean fight(String opponent) {
		if (opponent == "@"){	// if critter is hungry, than he tries to eat algea, else tries to run
			if (hungry >6){
				hungry -=5;
				return true;
			}
			else{
				walk(dir);
			return false;}
		}
		if (opponent == toString()){	// if opponent is of the same type, walk
			walk(dir);
			return false;
		}
		else 
			return true;
	}
	
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.BLACK;
	}
	
}
