package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;


public class Main extends Application {
	private double x, y;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Home.fxml"));
        Scene scene= new Scene(root);
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
       
		primaryStage.setScene(scene);
		
        // Set di chuyen
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        
        
        // Menu khi chuot phai
        ContextMenu contextMenu = new ContextMenu();
		
        MenuItem itemPin = new MenuItem("Pin");
        itemPin.setOnAction(new EventHandler<ActionEvent>() {
	       @Override
	       public void handle(ActionEvent event) {
	    	   boolean isTop = primaryStage.isAlwaysOnTop();
	    	   primaryStage.setAlwaysOnTop(!isTop);
	    	   contextMenu.getItems().get(0).setText((!isTop ? "un" : "")+ "Pin");
	       }
        });
	   
        contextMenu.getItems().add(itemPin);
        
	   	scene.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	       @Override
	       public void handle(ContextMenuEvent event) {
	           contextMenu.show(primaryStage, event.getScreenX(), event.getScreenY());
	       }
	   	});
	       
	    //show
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
