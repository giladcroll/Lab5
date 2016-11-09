package assignment5;

/**
 * @author Gilad Croll
 *	This Critter is a lazy critter. He is most likely to rest most of the time (62.5%). 
 *  walk is 25% and run is 12.5%. He fights only if he has 50 energy or more. 
 */
public class Critter2 extends Critter{
	
	@Override
	public String toString() { return "2"; }
	private int dir;	// direction of walking/running

	
	public Critter2(){
		dir = Critter.getRandomInt(8);	// set random initial direction
	}
	
	@Override
	public void doTimeStep() {
		
		if (Critter.getRandomInt(8) < 2){	// since this is a lazy critter, he's chances to walk are low
			walk(dir);
		}
		if (Critter.getRandomInt(8) == 3)
			run(dir);
		if (getEnergy()> (2*Params.min_reproduce_energy)){	// if has twice the energy - make a child
			Critter2 child = new Critter2();
			reproduce(child, Critter.getRandomInt(8));
		}
		dir = Critter.getRandomInt(8);	// choose a new direction		
	}

	@Override
	public boolean fight(String oponent) {
		if (getEnergy() < 50 || look(dir,false)==null){	// if not much energy, try to walk away from battle
			walk(dir);
			return false;	
		}
		return true;	// fight if can't move or have much energy	
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.GREENYELLOW; 
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
}
