package application;
	
import Structure.Company;
import adapter.ConnectToDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	private static Company company;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root  = FXMLLoader.load(getClass().getResource("/application/scene_login.fxml"));
			 primaryStage.setTitle("My Application");
			 primaryStage.getIcons().add(new Image("application/Capture.PNG"));
			 Scene newScene = new Scene(root);
	         primaryStage.setScene(newScene);
	         primaryStage.show();
	         
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		ConnectToDatabase conn = new ConnectToDatabase();
		
		company = Company.getInstance(conn.loadDaTa("company.db"));
		
		launch(args);
		
		
	}
}
