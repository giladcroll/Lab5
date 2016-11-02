/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Gilad Croll
 * GC24654
 * 16445
 * Alejandro Stevenson Duran
 * AS72948
 * 16455
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private boolean haveMoved;	// indicates if critter has walked/run for a current timestep
	private boolean inFight;
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	
	//NEW//
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() {	//new// 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	protected String look(int direction, boolean steps) {return "";}
	
	
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
		Map world = new Map();
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		if(!inFight){
			haveMoved = true;
		}
		int init_x = x_coord;
		int init_y = y_coord;
		if (direction == 0)
			x_coord = incrementCoord(x_coord, Params.world_width);
		if (direction == 1){
			x_coord = incrementCoord(x_coord, Params.world_width);
			y_coord = decrementCoord(y_coord, Params.world_height);
		}
		if (direction == 2){
			y_coord=decrementCoord(y_coord, Params.world_height);
		}
		if (direction == 3){
			y_coord = decrementCoord(y_coord, Params.world_height);
			x_coord = decrementCoord(x_coord, Params.world_width);
		}
		if (direction == 4){
			x_coord = decrementCoord(x_coord, Params.world_width);
		}
		if (direction == 5){
			x_coord = decrementCoord(x_coord, Params.world_width);
			y_coord = incrementCoord(y_coord,Params.world_height);
		}
		if (direction == 6)
			y_coord = incrementCoord(y_coord,Params.world_height);
		if (direction == 7){
			x_coord = incrementCoord(x_coord, Params.world_width);
			y_coord = incrementCoord(y_coord,Params.world_height);
		}
		// if the new position is occupied, and this critter is in a fight, go back
		if(inFight && (!isFree(x_coord,y_coord,this) || haveMoved)){	
			x_coord = init_x;
			y_coord = init_y;
		}
		energy -= Params.walk_energy_cost; 
	}
	
	private static boolean isFree (int x,  int y, Critter calling){
		for(Critter c : population){
			if((c.x_coord==x) && (c.y_coord==y) && c!=calling){ // means someone is already there
				return false;
			}
		}
		return true; // spot is empty
	}
	
	/**
	 * @param coord
	 * @param axisSize
	 * @return the updated coordinate, making sure that its in the map
	 */
	private static int decrementCoord(int coord, int axisSize){
		coord--;
		if (coord<0)
			coord +=axisSize;
		return coord;
	}
	
	/**
	 * @param coord
	 * @param axisSize
	 * @return the updated coordinate, making sure that its in the map
	 */
	private static int incrementCoord(int coord, int axisSize){
		return (coord+1)%axisSize;
	}
	
	/**
	 * @param coord
	 * @param axisSize
	 * @return valid twice decremented position
	 */
	private static int decrementCoord2(int coord, int axisSize){
		coord--;
		coord--;
		if (coord<0)
			coord +=axisSize;
		return coord;
	}
	
	/**
	 * @param coord
	 * @param axisSize
	 * @return twice incremented coordinate, making sure that its in the map
	 */
	private static int incrementCoord2(int coord, int axisSize){
		return (coord+2)%axisSize;
	}
	
	
	protected final void run(int direction) {
		if(!inFight){
			haveMoved = true;
		}	
		int init_x = x_coord;
		int init_y = y_coord;
		if (direction == 0)
			x_coord = incrementCoord2(x_coord, Params.world_width);
		if (direction == 1){
			x_coord = incrementCoord2(x_coord, Params.world_width);
			y_coord = decrementCoord2(y_coord, Params.world_height);
		}
		if (direction == 2){
			y_coord=decrementCoord2(y_coord, Params.world_height);
		}
		if (direction == 3){
			y_coord = decrementCoord2(y_coord, Params.world_height);
			x_coord = decrementCoord2(x_coord, Params.world_width);
		}
		if (direction == 4){
			x_coord = decrementCoord2(x_coord, Params.world_width);
		}
		if (direction == 5){
			x_coord = decrementCoord2(x_coord, Params.world_width);
			y_coord = incrementCoord2(y_coord,Params.world_height);
		}
		if (direction == 6)
			y_coord = incrementCoord2(y_coord,Params.world_height);
		if (direction == 7){
			x_coord = incrementCoord2(x_coord, Params.world_width);
			y_coord = incrementCoord2(y_coord,Params.world_height);
		}
		// if the new position is occupied, and this critter is in a fight, go back
		if(inFight && (!isFree(x_coord,y_coord,this) || haveMoved)){	
			x_coord = init_x;
			y_coord = init_y;
		}
		energy -= Params.run_energy_cost; 
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		
	if(this.getEnergy() < Params.min_reproduce_energy){ // return if parent doesnt have enough energy
		return;
	}
	// make sure critter not dead? probs not necessary as he wouldnt be in the list
	offspring.energy = Math.floorDiv(this.energy, 2); // round down
	this.energy = this.energy - offspring.energy; // round up
	offspring.x_coord = this.x_coord;
	offspring.y_coord = this.y_coord;
	// calculate new position
	if (direction == 0)
		offspring.x_coord = (offspring.x_coord+1)%Params.world_width;
	if (direction == 1){
		offspring.x_coord = (offspring.x_coord+1)%Params.world_width;
		offspring.y_coord--;
		if (offspring.y_coord<0)
			offspring.y_coord +=Params.world_height;
	}
	if (direction == 2){
		offspring.y_coord--;
		if (offspring.y_coord<0)
			offspring.y_coord +=Params.world_height;
	}
	if (direction == 3){
		offspring.x_coord--;
		if (offspring.x_coord<0)
			offspring.x_coord +=Params.world_width;
		offspring.y_coord--;
		if (offspring.y_coord<0)
			offspring.y_coord +=Params.world_height;
	}
	if (direction == 4){
		offspring.x_coord--;
		if (offspring.x_coord<0)
			offspring.x_coord +=Params.world_width;
	}
	if (direction == 5){
		offspring.x_coord--;
		if (offspring.x_coord<0)
			offspring.x_coord +=Params.world_width;
		offspring.y_coord = (offspring.y_coord+1)%Params.world_height;
	}
	if (direction == 6)
		offspring.y_coord = (offspring.y_coord+1)%Params.world_height;
	if (direction == 7){
		offspring.x_coord = (offspring.x_coord+1)%Params.world_width;
		offspring.y_coord = (offspring.y_coord+1)%Params.world_height; 
	}
	
	babies.add(offspring); // dont add to normal list yet
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
				Class<?> cls = Class.forName(myPackage+"."+critter_class_name);	// gets the class from string
			Object newCrit=cls.newInstance();	// instantiate a new critter object
			population.add((Critter) newCrit);	// add new critter to list
			//newCrit.setX_coord(getRandomInt(Params.world_width));
			((Critter)newCrit).x_coord = getRandomInt(Params.world_width);
			((Critter)newCrit).y_coord = getRandomInt(Params.world_height);
			((Critter)newCrit).energy = Params.start_energy; 
			}
			catch(Exception e){
				throw new InvalidCritterException(critter_class_name);
			}
	}
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class<?> cls = Class.forName(myPackage+"."+critter_class_name);
			for (Critter c: population){
				if (cls.isInstance(c))
					result.add(c);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	// gets the class from string
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population = new java.util.ArrayList<Critter>(); // is that fine or need to delete old object?
		Map.clearMap();
	}
	
	public static void worldTimeStep()  {
		//move babies to grown ups list
		population.addAll(babies);
		babies.clear();
		for (Critter c: population){	
			c.inFight = false;
			c.haveMoved = false;
			c.doTimeStep();	// doTimeStep for everyone
			c.energy -= Params.rest_energy_cost;	// take energy off for resting
		}
		clearDead();	// clear all critters with not energy
		// make them eat algae TODO
		// reproduce algae TODO
		resolveEncounters();	//resolves all encounters
		//add algaes
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try {
				Critter.makeCritter("Algae");	
			}catch(Exception e){
				System.out.println("nothing");
			}

		}
	}
	
	private static void resolveEncounters(){
		ArrayList[][] fightMap = createFightMap();
		for (int i=0; i<Params.world_height;i++){
			for (int j=0; j<Params.world_width;j++){
				if (fightMap[i][j].size() > 1){
					Critter winner = (Critter)fightMap[i][j].get(0);
					for (int k=1; k<fightMap[i][j].size(); k++){
						// make sure that winner fights next guy
						if (winner == null)	// if no winner from previous round, winner gets the next one in line 
							winner = (Critter)fightMap[i][j].get(k);
						else
							winner = battle(winner, (Critter)fightMap[i][j].get(k));	// invoke battle. winner gets the winner
					}
				}
			}
		}
	}
	
	/**
	 * @param c1
	 * @param c2
	 * @returns the winner
	 */
	private static Critter battle(Critter c1, Critter c2){
		c1.inFight = true;
		c2.inFight = true;
		boolean want1 = c1.fight(c2.toString());
		boolean want2 = c2.fight(c1.toString());
		if (c1.energy < 0 && c2.energy < 0){ // both died
			population.remove(c1);
			population.remove(c2);
			return null;
		}
		else if (c1.energy <0){	//only c1 died
			population.remove(c1);	//remove from world
			return c2;	//return winner
		}
		else if (c2.energy <0){	//only c1 died
			population.remove(c2);	//remove from world
			return c1;	//return winner
		}
		// get here if both are still alive
		int roll1=0;	// the number c1 rolls for the fight
		int roll2=0;
		if (want1) 
			roll1 = getRandomInt(c1.energy+1);
		if (want2) 
			roll2 = getRandomInt(c2.energy+1);
		if (c1.x_coord == c2.x_coord && c1.y_coord == c2.y_coord){
			if (roll1>roll2){	
				c1.energy += c2.energy/2;	// c1, the winner, gets half energy of loser
				population.remove(c2);	//remove loser from world
				return c1;	// return the winner
			}
			else{	// 
				c2.energy += c1.energy/2;	// winner gets half energy of loser
				population.remove(c1);	//remove loser from world
				return c2;	// return the winner
			}
		}
		return null;
	}
	
	/**
	 * this function deletes all the dead critters (no energy)
	 */
	private static void clearDead(){
		ArrayList<Critter> toKill = new ArrayList<Critter> ();  
		for (Critter c: population){
			if (c.energy <=0)
				toKill.add(c);
		}
		for (Critter c: toKill){
			population.remove(c);
		}
	}
	
	/**
	 * returns a map with all the critters for each specific position
	 */
	private static ArrayList[][] createFightMap(){
		ArrayList[][] critMap = new ArrayList[Params.world_height][Params.world_width];	// critt map is 2d array, size of map, where each cell is a list of all critters there
		for (int i=0; i<Params.world_height;i++)
			for (int j=0; j<Params.world_width;j++)
				critMap[i][j] = new ArrayList<Critter>();	// initialize all the arraylists
		for (Critter c: population){
			critMap[c.y_coord][c.x_coord].add(c);	// fill up the table
		}
		//ArrayList<Critter> [][] critMap1 = new ArrayList<Critter>[Params.world_height][Params.world_width]; // why doesnt work?
		return critMap;
	}
	
	public static void displayWorld() {
		Map.clearMap();
		for (Critter c: population){	
			int x= c.x_coord;
			int y= c.y_coord;
			// map is set as [height][width], for some reason
			Map.map[y][x] = c.toString(); // maybe?
		}
		Map.displayMap();
	}
	
}
