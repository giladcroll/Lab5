package assignment5;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import assignment5.Critter;
import javafx.application.Application; import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane; import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class Main extends Application { static GridPane grid = new GridPane();

@Override public void start(Stage primaryStage) {
	try {
		
		//The BorderPane has the same areas laid out as the
		//BorderLayout layout manager
		BorderPane componentLayout = new BorderPane();
		componentLayout.setPadding(new Insets(20,0,20,20));

		//The FlowPane is a conatiner that uses a flow layout
		final FlowPane choicePane = new FlowPane();
		choicePane.setHgap(100);
		Label choiceLbl = new Label("Fruits");

		//The choicebox is populated from an observableArrayList
		ChoiceBox fruits = new ChoiceBox(FXCollections.observableArrayList("Asparagus", "Beans", "Broccoli", "Cabbage"
		, "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
		, "Pepper", "Radish", "Shallot", "Spinach", "Swede"
		, "Turnip"));
		
		choicePane.getChildren().add(choiceLbl);
		choicePane.getChildren().add(fruits);
		
		//put the flowpane in the top area of the BorderPane
		componentLayout.setTop(choicePane);

		final FlowPane listPane = new FlowPane();
		listPane.setHgap(100);
		Label listLbl = new Label("Vegetables");

		ListView vegetables = new ListView(FXCollections.observableArrayList("Apple", "Apricot", "Banana"
		,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"));
		listPane.getChildren().add(listLbl);
		listPane.getChildren().add(vegetables);
		listPane.setVisible(false);

		componentLayout.setCenter(listPane);

		//The button uses an inner class to handle the button click event
		Button vegFruitBut = new Button("Fruit or Veg");
		vegFruitBut.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
		//switch the visibility for each FlowPane
		choicePane.setVisible(!choicePane.isVisible());
		listPane.setVisible(!listPane.isVisible());
		}
		});

		componentLayout.setBottom(vegFruitBut);

		//Add the BorderPane to the Scene
		Scene appScene = new Scene(componentLayout,500,500);

		//Add the Scene to the Stage
		primaryStage.setScene(appScene);
		/*
		grid.setGridLinesVisible(true);
		Scene scene = new Scene(componentLayout,500,500); primaryStage.setScene(scene);
		*/
		primaryStage.show();
		// Paints the icons. Painter.paint();
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
