package ManageInformation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import adapter.ConnectToDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CreatAcountController {
	@FXML
	private TextField tefName;
	@FXML
	private PasswordField pasfPas;
	@FXML
	private PasswordField pasfCon;
	@FXML
	private Button butAdd;

	private ArrayList<String> list_user;

	public void initialize() {
		Image imageDecline = new Image(getClass().getResourceAsStream("as.PNG"));
		butAdd.setGraphic(new ImageView(imageDecline));
		ConnectToDatabase newConn = new ConnectToDatabase();
		String login2 = "SELECT * FROM ACOUNT;";
		ResultSet rs = newConn.querry(login2, "company.db");
		list_user = new ArrayList<String>();
		String user_name = new String();
		int i = 0;
		try {
			while (rs.next()) {
				i++;
				list_user.add(rs.getString("USER_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void buttAddController() {

		if (tefName.getText().isEmpty() || pasfPas.getText().isEmpty() || pasfCon.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("dữ liệu trống!");
			alert.showAndWait();
		} else if (!(pasfPas.getText().equals(pasfCon.getText()))) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Xác Nhận Mật Khẩu Sai!");
			alert.showAndWait();
		} else {
			String name = tefName.getText();
			ConnectToDatabase newConns = new ConnectToDatabase();
			Connection conn = newConns.Connec("company.db");
			String MakeAcount = "INSERT INTO ACOUNT(USER_NAME,PASSWORD) VALUES ('" + tefName.getText() + "','"
					+ pasfPas.getText() + "');";

			int check = 0;
			for (String str : list_user) {
				if (str.equals(name)) {
					System.out.println("erorr2!!!!!!");
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Tên tài khoản đã tồn tại!");
					alert.showAndWait();
					check = 1;
					break;
				}
			}

			if (check == 0) {
				try {
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(MakeAcount);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Tài khoản đã được tạo!");
				alert.showAndWait();
			}
			Stage stage = (Stage) butAdd.getScene().getWindow();
			stage.close();

		}
	}
}
