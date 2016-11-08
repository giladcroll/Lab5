/* CRITTERS Critter3.java
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
 *	this critter loves to run. he never walks. 
 *	he only runs diagonally. 
 *	he only wants to fight critters of his type or algae
 *	he tries to run from a fight 50% of the times
 *	he reproduces only when he has at least twice the minimum energy to do it
 */

public class Critter3 extends Critter{
	
	private int dir;	// direction of walking/running
	public Critter3(){
		dir = Critter.getRandomInt(8);	// set random initial direction
		dir = (2*dir + 1)%8; // make dir always odd- to run diagonally
	}
	
	public String toString() { return "3"; }

	@Override
	public void doTimeStep() {
		run(dir); // this critter always runs, never walks
		if (getEnergy()>= (2*Params.min_reproduce_energy)){	// if has twice the energy - make a child
			Critter3 child = new Critter3();
			reproduce(child, Critter.getRandomInt(8));
		}
		dir = Critter.getRandomInt(8);	// choose a new direction
		dir = (2*dir + 1)%8; // make dir always odd- to run diagonally
	}

	@Override
	public boolean fight(String opponent) {
		look(dir,false);	// look at surroundings, learn the battlefield
		if(toString().equals(opponent) || opponent.equals("@")){ // this crit only fights with his own kind or algae
			return true;
		}
		else{
			walk(dir);
			return false;	// try to run!
		}
	}

	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.PINK; 
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
	

}
