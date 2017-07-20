package ManageInformation;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ElementController {
	@FXML
	private TextField tefName;
	@FXML
	private TextField tefID;
	@FXML
	private Button butAdd;
	
	public void initialize() {
		Image imageDecline = new Image(getClass().getResourceAsStream("save.PNG"));
		butAdd.setGraphic(new ImageView(imageDecline));
	}
	public void butAddControler(){
		if(tefName.getText().isEmpty() || tefID.getText().isEmpty()){
			Alert alert = new Alert(AlertType.WARNING);
			
			alert.setContentText("Tên hoặc ID trống!");

			alert.showAndWait();
		}
		else{
			
			Manager.getInstance(null).addDeparment(tefName.getText(),tefID.getText());
			
			Stage stage = (Stage) butAdd.getScene().getWindow();
		    stage.close();
		    
		    
		}
	}
}
