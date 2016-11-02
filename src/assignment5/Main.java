/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        
        
        /* Write your code below. */
        boolean run=true;	// this boolean is true as long as the program is running
       // Map world = new Map();
        //Critter.displayWorld();       //need this? wont the displayworld called in critter class
        while(run){
        	System.out.print("critters> ");
        	String input = kb.nextLine();
        	String [] inputWords = parse(input);	// input words gets all the different words of the input
        	if (inputWords.length == 0) 
        		System.out.println("invalid input, please try again");
        	else if ((inputWords[0].equals("quit"))){	// exit condition
        		if(inputWords.length == 1){
        		run = false;
        		}
        		else{
                	System.out.println("error processing: " + input);
        		}
        	}
        	else if (inputWords[0].equals("show")){
        		if(inputWords.length == 1){
        		Critter.displayWorld();
        		}
        		else{
                	System.out.println("error processing: " + input);
        		}
        	}
        	else if (inputWords[0].equals("step")){
        		if (inputWords.length == 1){
        			try {
						Critter.worldTimeStep();
					} catch (Exception e) {
						System.out.println("error processing: " + input);
					}
        		}
        		else if (inputWords.length == 2){
        			// to do: exception catcher in this parsing function if input not a number or if number is negative!
        			try{
	        			int num = Integer.parseInt(inputWords[1]);
	        			if (num <0)
	        				System.out.println("error processing: " + input);
	        			else{
		        			while(num>0){
		            			Critter.worldTimeStep();
		        				num--;
		        			}
		        		}
        			}
	        			catch(Exception e){
	        				System.out.println("error processing: " + input);
	        		}
        		}
        		else{
                	System.out.println("error processing: " + input);
        		}
        	}
        	else if (inputWords[0].equals("make")){
        		if (!((inputWords.length == 3) || (inputWords.length == 2))){
                	System.out.println("error processing: " + input);
        		}
        		else {
        			int num=0;
        			if(inputWords.length == 2){
        				num = 1;
        			}
        			else{
	        			try{
	        				num = Integer.parseInt(inputWords[2]);
	        			}
	        			catch(Exception e){
	        				System.out.println("error processing: " + input);
	        			}
        			}
        			while(num>0){
        				 try {
        						Critter.makeCritter(inputWords[1]);
        					} catch (Exception e) {
        						// TODO Auto-generated catch block
        						System.out.println("error processing: " + input);
        					}
        				 num--;
        			}
        		}
        	}
         	else if (inputWords[0].equals("stats")){
         		if (inputWords.length != 2)
         			System.out.println("error processing: " + input);
         		else{
	         		// only takes concrete objects TODO
	         		
		         	try{
		         		Class<?> cls = Class.forName(myPackage+"."+inputWords[1]);	// gets the class from string
		         		// check if cls is a child of critter and not abstract
		         		if (Modifier.isAbstract(cls.getModifiers()) || (!Critter.class.isAssignableFrom(cls)))
		         			System.out.println("error processing: " + input);
		         		else{
		         			Method method = cls.getMethod("runStats", List.class);
		         			method.invoke(null ,Critter.getInstances(inputWords[1]));
		         			// method(Critter.getInstances(inputWords[1]));
		         			// cls.runStats(Critter.getInstances(inputWords[1]));	// call runstats in Critter.java with the list of instances of specific Critter
		         			}
		         	}
		         		catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
		         			System.out.println("error processing: " + input);
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (InvalidCritterException e) {
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						} catch (NoClassDefFoundError e){
							// TODO Auto-generated catch block
							System.out.println("error processing: " + input);
						}
		         		
	         		}
        	}
        	else if(inputWords[0].equals("seed")){
        		if (inputWords.length != 2){
                	System.out.println("error processing: " + input);
        		}
        		else{
        			// to do: exception catcher in this parsing function if input not a number or if number is negative!
        			long num=0;
        			try{
            		 num = Long.parseLong(inputWords[1]);
            		 if(num < 0){ // don't allow negative random values
         				System.out.println("error processing: " + input);
            		 }
         			 Critter.setSeed(num);
        			}
        			catch(Exception e){
        				System.out.println("error processing: " + input);
        			}
        		}
        	}
        	else{ // not a valid command
        		System.out.println("invalid input, please try again");
        	}
        }
        
        
      //  System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }


	private static String[] parse(String input){
    	String[] words;	// words to return
    	words = input.split(" ");
    	return words;
    }
    
}
