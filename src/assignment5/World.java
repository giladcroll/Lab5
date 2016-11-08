/* World.java
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

import java.util.List;

//import assignment5.Critter.TestCritter;

public class World {
	public static String [][] map; // can it  be char instead of string??
	
	// Constructor for Map
	public World(){
		map = new String[Params.world_height][Params.world_width];	// set the map with the proper size
		//System.out.println("map created");	
		for (int i=0; i<Params.world_height;i++){
			for (int j=0; j<Params.world_width;j++){
				map[i][j]=" ";
			}
		}
	}
	
	/**
	 * prints out the current state of map
	 * @param population 
	 */
	public static void displayMap(){
	//	buildMap(population);
		// print first line
		System.out.print("+");	
		for (int i=0; i< Params.world_width; i++){System.out.print("-"); }
		System.out.print("+\n");
		// print actual world line
		for (int i=0; i< Params.world_height;i++){
			System.out.print("|");
			for(int j=0; j<Params.world_width;j++){
				System.out.print(map[i][j]);
	        }
			System.out.print("|\n");
		}		
		// print last line
		System.out.print("+");
		for (int i=0; i< Params.world_width; i++){System.out.print("-"); }
		System.out.print("+\n");

	}
	
	/**
	 * resets the map to " "
	 */
	public static void clearMap(){
		for (int i=0; i<Params.world_height;i++){
			for (int j=0; j<Params.world_width;j++){
				map[i][j]=" ";
			}
		}
	}
		
	
}
