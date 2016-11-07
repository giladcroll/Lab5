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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;
import javax.swing.Painter;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Slider;
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
import javafx.util.Duration;
public class Main extends Application { 
	
	final static int worldWidth = 800;
	static GridPane grid = new GridPane();	// grid for show world
	//static Canvas canvas = new Canvas(canvasWitdh,canvasHight);	// canvas for show world
	static int cell = worldWidth/Params.world_width;
	static {	// set cell to proper value
		if (Params.world_height*2/3>Params.world_width)
			cell = worldWidth*2/3/Params.world_height;
	}
	boolean animate = false;
	// to redirect console:
	static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
	static PrintStream old = System.out;	// if you want to restore output to console
	Timeline animationTL ;
	List<Button> buttonList = new ArrayList<Button>();
	
@Override public void start(Stage primaryStage) {
	try {
		final GridPane stageGrid = new GridPane();
	    primaryStage.setTitle("Critters!");
	    stageGrid.setGridLinesVisible(false);
	    stageGrid.setHgap(10); //horizontal gap in pixels 
	    stageGrid.setVgap(10); //vertical gap in pixels
		final VBox buttons = new VBox();
		final FlowPane flow_stats = new FlowPane();
		for (int i=0; i < Params.world_width;i++){
			grid.getColumnConstraints().add(new ColumnConstraints(cell)); 		     
		}
		for (int i=0; i < Params.world_height;i++){
			grid.getRowConstraints().add(new RowConstraints(cell)); 		     
		}

		//final Canvas canvas = new Canvas(canvasWitdh,canvasHight);
		grid.setHgap(0);
		grid.setVgap(0);
		
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
		buttonList.add(makeCrt);
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
		buttonList.add(stepBtn);
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
		buttonList.add(statsBtn);
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
		quitBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
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
		
		// Animation
		
		Button animateBtn = new Button("animate");
		animateBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		animateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {            	 
            	animationTL = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            	    @Override
            	    public void handle(ActionEvent event) {
            	    	for (Button butt: buttonList){
            	    		butt.setDisable(true);
            	    	}
            	    	Critter.worldTimeStep();
            	    	Critter.displayWorld();
            	    	// run stats for critter
            	    	try {
            	    		Critter.runStats(Critter.getInstances("Critter"));
            	    	} catch (InvalidCritterException e) {
            	    		// TODO Auto-generated catch block
            	    		e.printStackTrace();
            	    	}
            	    	statsLabel.setText(testOutputString.toString());
            	    	testOutputString.reset();
            	    	animateBtn.setDisable(true);
            	    }
            	}));
            	animationTL.setCycleCount(Timeline.INDEFINITE);
            	animationTL.play(); 

            }
		});

		Button stopAnimateBtn = new Button("stop");
		stopAnimateBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		stopAnimateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	animationTL.stop(); 
            	for (Button butt: buttonList){
    	    		butt.setDisable(false);
    	    	}
               	animateBtn.setDisable(false);
            }
        });
		
		Slider slider = new Slider();
		
		slider.valueProperty().addListener((obs, oldval, newVal) -> slider.setValue(newVal.intValue())); // make it discrete values
		slider.setMin(0);
		slider.setMax(50);
		slider.setValue(25);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(10);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
		
	    // add buttons into container
	    buttons.getChildren().add(flowMakeCrit);
	    buttons.getChildren().add(errLbl);
	    buttons.getChildren().add(flowStep);
	    buttons.getChildren().add(new Label());
	    buttons.getChildren().add(flowStats);
	    buttons.getChildren().add(new Label());
	    buttons.getChildren().add(animateBtn);
	    buttons.getChildren().add(stopAnimateBtn);
	    buttons.getChildren().add(new Label("animation speed:"));
	    buttons.getChildren().add(slider);
	    buttons.getChildren().add(new Label());
	    buttons.getChildren().add(quitBtn);
	    
	    statsLabel.setMaxWidth(140 + 10 + assignment5.Params.world_width);
	    // TODO add a scroll wrap if surpasses stats max height
	    statsLabel.setWrapText(true);
	    flow_stats.getChildren().add(statsLabel);
	    

		// adding subcontainers into top grid container
	    stageGrid.add(buttons, 1, 1 ,1, 1);  // col index, row index, col span, row span
	    stageGrid.add(grid, 2, 1 ,1, 1);
	    stageGrid.add(flow_stats, 1, 2 ,2, 1);

	    // show the good stuff
		//Scene scene = new Scene(stageGrid, worldWidth + 500, worldHight + 400);
	    int gridWorldHight = cell*Params.world_height ;
	    if (gridWorldHight < 300)	// make sure that grade for world is not too small
	    	gridWorldHight = 300;
	    stageGrid.getColumnConstraints().add(new ColumnConstraints(10));
	    stageGrid.getColumnConstraints().add(new ColumnConstraints(400));
	    stageGrid.getColumnConstraints().add(new ColumnConstraints(worldWidth + 10));
	    stageGrid.getRowConstraints().add(new RowConstraints(10));
	    stageGrid.getRowConstraints().add(new RowConstraints(gridWorldHight));
	    stageGrid.getRowConstraints().add(new RowConstraints(100));
	    Scene scene = new Scene(stageGrid);
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


