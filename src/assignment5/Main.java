package assignment5;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
	
	static GridPane grid = new GridPane();


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
		final int worldSize = 500;
		final Canvas canvas = new Canvas(worldSize,worldSize/2);
	
	    // Buttons
		// makeCritter
		ArrayList<String> critters = returnCritters();
		ComboBox critterList = new ComboBox(FXCollections.observableArrayList(critters));
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
		Button makeCrt= new Button("Make Critter");
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
	
	    // add buttons into container
	    buttons.getChildren().add(flowMakeCrit);
	    buttons.getChildren().add(errLbl);
	    buttons.getChildren().add(flowStep);
	    
	    //canvas layout
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.fillRect(0,0,worldSize,worldSize/2);
		
	    //stats layout
	    Label label = new Label("helloooooooooooooooooooooooooooooooo00000000000000000000000000000000000000000000");
	    label.setMaxWidth(140 + 10 + assignment5.Params.world_width);
	    // TODO add a scroll wrap if surpasses stats max height
	    label.setWrapText(true);
	    flow_stats.getChildren().add(label);

		// adding subcontainers into top grid container
	    stageGrid.add(buttons, 0, 0 ,1, 1);  // col index, row index, col span, row span
	    stageGrid.add(canvas, 1, 0 ,1, 1);
	    stageGrid.add(flow_stats, 0, 1 ,2, 1);

	    // show the good stuff
		Scene scene = new Scene(stageGrid, worldSize + 500, worldSize/2 + 200);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch(Exception e) { e.printStackTrace(); }
}
	

public static void main(String[] args) {
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
