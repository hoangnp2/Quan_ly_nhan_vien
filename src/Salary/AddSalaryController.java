package Salary;

import adapter.ConnectToDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddSalaryController {
	@FXML
	private Button butAdd;
	@FXML
	private TextField tefTitle;
	@FXML
	private TextField tefSalary;
	public void ButAddTitel(){
		try{
		int i=Integer.parseInt(tefSalary.getText());
		ConnectToDatabase newConn = new ConnectToDatabase();
		String querry = "INSERT INTO SALARY(TITLE,SALARY) VALUES ('"+ tefTitle.getText() +"',"+ tefSalary.getText() + ");";
		if(!(tefTitle.getText().isEmpty() ||tefTitle.getText().isEmpty())){
			newConn.querryUpdate(querry, "company.db");
			Stage stage = (Stage) butAdd.getScene().getWindow();
		    stage.close();
		}
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			
			alert.setContentText("Mức lương không hợp lê, Vui lòng nhập lại");

			alert.showAndWait();
		}
	}
}
