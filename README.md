# Lab5
Part 2 of Critters - add JavaFX - by Alejandro Stevenson-Duran and Gilad Croll

/* EE422C Project 5 submission by
 * Gilad Croll
 * gc24654
 * 16445
 * Alejandro Stevenson-Duran
 * as72948
 * 16455
 * Slip days used: <0>
 * Fall 2016
 */
In order to implement a better UI experience for our critter world, we used JavaFX to take
care of our view component. 

At a high level, we used a variety of nested built-in containers including GridPane and 
FlowPane.

We incorporated the entirety of the critter view component in a single stage.
We thought this was a bit better than using 2 or 3 stages since it can demonstrate our understanding of how to place containers and effectively manipulate the positioning in a single scene.

Our design has a variety of features that improve the user experience. 
-	Automatic scaling for the cells’ size depending on the critter world parameters
-	Users can change animate factor with simple slide-meter  
-	Available list of critters is given in a dropdown menu to facilitate selection process in make critter and run stats
-	Time buffer in animate to slow down the display refresh rate to allow users to better visualize the dynamics inside critter world
-	Only digits are allowed as inputs in the run step and set seed buttons
-	Designed our own shapes to represent different critters
-	Carefully placed Quit button
