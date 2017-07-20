package ManageInformation;

import GeneralIformation.Impact;
import Structure.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ImpactController {
	@FXML
	private TextField tefId;
	@FXML
	private TextArea teaCouses;
	@FXML
	private TextArea teaContent;
	@FXML
	private Button butAdd;
	
	public Impact butAddController(Employee epl,boolean type){
		return new Impact(tefId.getText(),type,epl.getID(),teaContent.getText(),teaCouses.getText());
	}
	
}
