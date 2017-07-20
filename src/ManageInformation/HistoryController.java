package ManageInformation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import adapter.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HistoryController {
	@FXML
	private ListView<String> listHistory=new ListView<>();
	@FXML
	private Button btnDelete;
	
	public void initialize() throws SQLException {
		Image imageDecline = new Image(getClass().getResourceAsStream("delete.PNG"));
		btnDelete.setGraphic(new ImageView(imageDecline));
		ConnectToDatabase cntd=new ConnectToDatabase();
		Connection conn=cntd.Connec("company.db");
		Statement stmt=null;
		stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM HISTORY;");
		ObservableList<String> content =  FXCollections.observableArrayList(); 
		while (rs.next()){
			
			String cont=rs.getString("CONTENT");
			content.add(cont);
		}
	
		listHistory.setItems(content);
	}
	
	public void Delete() {
		ConnectToDatabase cntd=new ConnectToDatabase();
		String txt="DELETE FROM HISTORY;";
		cntd.querryUpdate(txt,"company.db");
		ObservableList<String> content =  FXCollections.observableArrayList();
		listHistory.setItems(content);
	}
}
