package assignment5;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Painter;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class Main extends Application { 
	
	final static int canvasWitdh = 500;
	final static int canvasHight = canvasWitdh/2;
	static GridPane grid = new GridPane();	// grid for show world
	static Canvas canvas = new Canvas(canvasWitdh,canvasHight);	// canvas for show world
	static int cell = canvasWitdh/Params.world_width;
	// to redirect console:
	static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
	static PrintStream old = System.out;	// if you want to restore output to console
	
@Override public void start(Stage primaryStage) {
	try {
		final GridPane stageGrid = new GridPane();
	    primaryStage.setTitle("Critters!");
	    stageGrid.setGridLinesVisible(false);
	    stageGrid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
	    stageGrid.setVgap(10); //vertical gap in pixels
		// Paints the icons. 
		//Painter.paint();
		final VBox buttons = new VBox();
		final FlowPane flow_stats = new FlowPane();
		//grid.setMinWidth(1000);
		for (int i=0; i < Params.world_width;i++){
			grid.getColumnConstraints().add(new ColumnConstraints(cell)); 		     
		}
		for (int i=0; i < Params.world_width;i++){
			grid.getRowConstraints().add(new RowConstraints(cell)); 		     
		}

		//final Canvas canvas = new Canvas(canvasWitdh,canvasHight);
		grid.setHgap(Params.world_width);
		grid.setHgap(Params.world_height);
		StackPane world = new StackPane();
		world.getChildren().addAll(canvas,grid);
		
	    // Buttons
		// makeCritter
		ArrayList<String> critters = returnCritters();
		ComboBox<String> critterList = new ComboBox(FXCollections.observableArrayList(critters));
		critterList.setPromptText("Select Critter");
		TextField numOfCrt = new TextField ();
		numOfCrt.setPromptText("number of Critters");
		// force the field to be numeric only
		numOfCrt.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	numOfCrt.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		Label errLbl = new Label("");
		errLbl.setTextFill(Color.RED);
		Button makeCrt= new Button("make critter");
		makeCrt.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		makeCrt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if((critterList.getValue() != null) && !(numOfCrt.getText().equals("")) ){
            		errLbl.setText("");
            		for (int i=0; i< Integer.valueOf(numOfCrt.getText()); i++)
            			try {
            				Critter.makeCritter((String)critterList.getValue());
            			} catch (InvalidCritterException e) {}
            		// clear selection in critter number and in critter selection
            		numOfCrt.clear();
            		critterList.getSelectionModel().clearSelection();
            		Critter.displayWorld();
            	}
            	else{
            		errLbl.setText("Make sure to select a Critter and enter a number");
            	}
            }
        });
		// make critter Button layout
		FlowPane flowMakeCrit = new FlowPane((Orientation.HORIZONTAL));
		flowMakeCrit.getChildren().add(makeCrt);
        flowMakeCrit.getChildren().add(critterList);
        flowMakeCrit.getChildren().add(numOfCrt);
		
		//Step Button 
        TextField numOfSteps = new TextField ();
		numOfSteps.setPromptText("number of steps");
		// force the field to be numeric only
			numOfSteps.textProperty().addListener(new ChangeListener<String>() {
			        @Override
			        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			            if (!newValue.matches("\\d*")) {
			            	numOfSteps.setText(newValue.replaceAll("[^\\d]", ""));
			            }
			        }
			    });
		
		Button stepBtn = new Button("step");
		stepBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		stepBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(!(numOfSteps.getText().equals("")) ){
            		errLbl.setText("");
            		for (int i=0; i< Integer.valueOf(numOfSteps.getText()); i++)
            			try {
            				Critter.worldTimeStep();
            			} catch (Exception e) {}
            		// clear selection in step number
            		numOfSteps.clear();
            	}
            	else{	// if no selection step one
            		Critter.worldTimeStep();
            	}
            	Critter.displayWorld(); //display world after stepping
            }
        });
		
		// step buttons layout
		FlowPane flowStep = new FlowPane((Orientation.HORIZONTAL));
		flowStep.getChildren().add(stepBtn);
		flowStep.getChildren().add(numOfSteps);
		
		// run stats button
		ComboBox critterListStats = new ComboBox(FXCollections.observableArrayList(critters));	//critter selection list for run stats
		critterListStats.setPromptText("Select Critter");
		
		Button statsBtn = new Button("run stats");
		statsBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label statsLabel = new Label("Welcome to Critters");
		statsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (critterListStats.getValue() != null){
            		try {
            			String myPackage = Critter.class.getPackage().toString().split(" ")[1];
            			Class<?> cls = Class.forName(myPackage+"."+(String)critterListStats.getValue());
            			Method method = cls.getMethod("runStats", List.class);
	         			method.invoke(null ,Critter.getInstances((String)critterListStats.getValue()));
	         			statsLabel.setText(testOutputString.toString());
	         			testOutputString.reset();
            			//Critter.runStats(Critter.getInstances((String)critterListStats.getValue()));
            		} catch (Exception e) {}
            	} else
					try {
						Critter.runStats(Critter.getInstances("Critter"));
						statsLabel.setText(testOutputString.toString());
	         			testOutputString.reset();
					} catch (InvalidCritterException e) {}
            	//statsLabel.setText(Critter.runStats(Critter.getInstances("Critter")));
            	
            }
        });
		
		//quit Button
		Button quitBtn = new Button("quit");
		quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	System.exit(0);
            }
        });
		
		// run stats button layout
		FlowPane flowStats = new FlowPane((Orientation.HORIZONTAL));
		flowStats.getChildren().add(statsBtn);
		flowStats.getChildren().add(critterListStats);
		//flowStats.getChildren().add();
		
	    // add buttons into container
	    buttons.getChildren().add(flowMakeCrit);
	    buttons.getChildren().add(errLbl);
	    buttons.getChildren().add(flowStep);
	    buttons.getChildren().add(new Label());
	    buttons.getChildren().add(flowStats);
	    buttons.getChildren().add(new Label());
	    buttons.getChildren().add(quitBtn);
	    //canvas layout
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.AQUA);
		gc.fillRect(0,0,canvasWitdh,canvasHight);
		
		//test canvas cell fill
		gc.setStroke(Color.BLACK);
	    gc.setFill(Color.RED);
		gc.fillRect(0, 0, cell, cell);
		
	    //stats layout
	    statsLabel.setMaxWidth(140 + 10 + assignment5.Params.world_width);
	    // TODO add a scroll wrap if surpasses stats max height
	    statsLabel.setWrapText(true);
	    flow_stats.getChildren().add(statsLabel);

		// adding subcontainers into top grid container
	    stageGrid.add(buttons, 0, 0 ,1, 1);  // col index, row index, col span, row span
	    stageGrid.add(world, 1, 0 ,1, 1);
	    stageGrid.add(flow_stats, 0, 1 ,2, 1);

	    // show the good stuff
		Scene scene = new Scene(stageGrid, canvasWitdh + 700, canvasHight + 400);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch(Exception e) { e.printStackTrace(); }
}
	

public static void main(String[] args) {
	// redirect console
	
	testOutputString = new ByteArrayOutputStream();
	PrintStream ps = new PrintStream(testOutputString);
	// Save the old System.out.
	old = System.out;
	// Tell Java to use the special stream; all console output will be redirected here from now
	System.setOut(ps);
	// 
	
	launch(args);
}

/**
 * returns all childs of critter in a string list 
 * @param Files
 */
	private static ArrayList<String> returnCritters(){
		String myPackage;
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
		File folder = new File(System.getProperty("user.dir") + "/src/" + myPackage );
		File[] files= folder.listFiles();
		ArrayList<String> critterList = new ArrayList<String> ();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String file = files[i].getName();
				if (file.length() >4){
					if (file.substring(file.length() - 4, file.length()).equals(".java"));{
						String className =file.substring(0 , file.length() - 5);
						Class<?> cls;
						try {
							cls = Class.forName(myPackage+"."+className);
							if (!Modifier.isAbstract(cls.getModifiers()) && (Critter.class.isAssignableFrom(cls))){
								critterList.add(className);
							}
						} catch (Exception e) {}	// gets the class from string
	
					}
				}
			}
		}
		return critterList;
	}
			
}
