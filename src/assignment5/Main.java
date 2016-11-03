package assignment5;
import javax.swing.Painter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application { 
	
	static GridPane grid = new GridPane();
	//static GridPane grid2 = new GridPane();


@Override public void start(Stage primaryStage) {
	try {
	    primaryStage.setTitle("Critters!");
		grid.setGridLinesVisible(false);
		grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		grid.setVgap(10); //vertical gap in pixels
		// Paints the icons. 
		//Painter.paint();
		final FlowPane flow_buttons = new FlowPane();
		final FlowPane flow_stats = new FlowPane();
		final Canvas canvas = new Canvas(assignment5.Params.world_width,assignment5.Params.world_height);
		final GridPane canvas_grid = new GridPane();

		// button layout
	    flow_buttons.setMaxWidth(140);
		flow_buttons.setHgap(10);
		flow_buttons.setVgap(10);
		Button btn = new Button();
	    Button btn1 = new Button();
	    flow_buttons.getChildren().add(btn);
	    flow_buttons.getChildren().add(btn1);
	    btn.setText("Add new Critter");
	    btn1.setText("yooooo");
	    btn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            System.out.println("You have created a new Crit!");
	        }
	    });

	    //canvas layout
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.fillRect(0,0,assignment5.Params.world_width,assignment5.Params.world_height);
		
	    //stats layout
	    Label label = new Label("helloooooooooooooooooooooooooooooooo00000000000000000000000000000000000000000000");
	    label.setMaxWidth(140 + 10 + assignment5.Params.world_width);
	    // TODO add a scroll wrap if surpasses stats max height
	    label.setWrapText(true);
	    flow_stats.getChildren().add(label);

	   
		// adding subcontainers into top grid container
	    grid.add(flow_buttons, 0, 0 ,1, 1);  // col index, row index, col span, row span
	    grid.add(canvas, 1, 0 ,1, 1);
	    grid.add(flow_stats, 0, 1 ,2, 1);

	    // show the good stuff
		Scene scene = new Scene(grid, assignment5.Params.world_width + 200, assignment5.Params.world_height + 200);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
	} catch(Exception e) { e.printStackTrace(); }
}
	

public static void main(String[] args) {
	
	ArrayList<String> critters = returnCritters();
	    
	
	
	System.out.println();
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
