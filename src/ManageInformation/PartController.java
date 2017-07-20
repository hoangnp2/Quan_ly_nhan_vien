package ManageInformation;

import Structure.Department;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PartController {
	public static Department dep;
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
	public static void setDepartment(Department dep){
		PartController.dep = dep;
	}
	public void butAddControler(){
		if(tefName.getText().isEmpty() || tefID.getText().isEmpty()){
			Alert alert = new Alert(AlertType.WARNING);
			
			alert.setContentText("Tên hoặc ID trống!");

			alert.showAndWait();
		}
		else{
			Manager.getInstance(null).addInstute(dep,tefName.getText(),tefID.getText());
			
			Stage stage = (Stage) butAdd.getScene().getWindow();
		    stage.close();
		    
		    
		}
	}
}
