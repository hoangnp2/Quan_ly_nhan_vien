package application;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ManageInformation.*;
import adapter.ConnectToDatabase;

public class LoginController {
	@FXML
	private Button buttonsigin;
	
	@FXML
	private Button buttonreset;
	
	@FXML
	private Button butWarning;
	@FXML
	private ImageView imgWarning;
	
	@FXML
	private TextField textfield;
	
	@FXML
	private PasswordField passwordfiel;
	
	DateTimeFormatter  df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static ArrayList<String> war = new ArrayList<String>();
	
	public void initialize(){
		try {
			ConnectToDatabase newconn = new ConnectToDatabase();
			Connection conn = newconn.Connec("company.db");
			Statement stmt = conn.createStatement();
			ResultSet rsDeadline = stmt.executeQuery("SELECT * FROM CONTRACT;");
			LocalDate date = null ;
			
			
			LocalDate now = LocalDate.now();
			while(rsDeadline.next()){
				date = LocalDate.parse(rsDeadline.getString("INVALID"), df);
				 
				Period compare = Period.between( now,date);
				 if(compare.getYears()==0){
					 if(Math.abs(compare.getMonths()) <= 6){
						Image img = new Image("/application/exit.PNG");
						imgWarning.setImage(img);
						break;
					 }
				 }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void Resetform(){
		if(textfield.getText() != null)
			textfield.clear();
		if(passwordfiel.getText() != null)
			passwordfiel.clear();
	}
	
	@FXML
	public void Siginform(){
		ConnectToDatabase newConn = new ConnectToDatabase();
		String usn = textfield.getText();
		String pas = passwordfiel.getText();
		if(usn.isEmpty() || pas.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			
			alert.setContentText("Tên đăng nhập hoặc mật khẩu trống,xin vui lòng nhập đầy đủ!");

			alert.showAndWait();
		}
		else{
			String login = "SELECT * FROM ACOUNT WHERE USER_NAME = ";
		
		try {
			
			ResultSet rs = newConn.querry(login + "'" + usn + "'", "company.db");
			
			if(rs.isClosed()){
				Alert alert = new Alert(AlertType.ERROR);
				
				alert.setContentText("Tài khoản không tồn tại, xin vui lòng kiểm tra lại!");

				alert.showAndWait();
			}
			else{
				String sps = rs.getString("PASSWORD");
				if(sps.equals(pas)){
					Stage newstage = new Stage();
					try{
					Parent nroot  = FXMLLoader.load(getClass().getResource("/ManageInformation/scene_Profile.fxml"));
					newstage.setTitle("My Application");
					//newstage.getIcons().add(new Image("application/Capture.PNG"));
					Scene hsScene = new Scene(nroot);
					hsScene.getStylesheets().add("ManageInformation/HoSo.css");
					newstage.getIcons().add(new Image("application/Capture.PNG"
							+ ""));
					newstage.setScene(hsScene);
					newstage.show();
					Manager.getInstance(usn);
					}catch(Exception e){
						e.printStackTrace();
						
					}
					Stage stage = (Stage) buttonsigin.getScene().getWindow();
				    stage.close();
				}
				else{
					Alert alert = new Alert(AlertType.ERROR);
					
					alert.setContentText("Mật khẩu không khớp, Xin vui lòng nhập lại!");

					alert.showAndWait();
				}
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			
		}
		}
	}
	
	public void showWarning(){
		ConnectToDatabase newconn = new ConnectToDatabase();
		Connection conn = newconn.Connec("company.db");
		
		ArrayList<String> idEmployee = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rsDeadline = stmt.executeQuery("SELECT * FROM CONTRACT;");
			LocalDate date = null ;
			String id = null;
			String content = new String();
			LocalDate now = LocalDate.now();
			while(rsDeadline.next()){
				date = LocalDate.parse(rsDeadline.getString("INVALID"), df);
				 id = rsDeadline.getString("ID_EMPLOYEE");
				Period compare = Period.between( now,date);
				 if(compare.getYears()==0){
					 if(Math.abs(compare.getMonths()) <= 6){
						 String line =  "Nhân Viên số " + id + " có hạn hợp đồng trong vòng "+ Math.abs(compare.getMonths()) + "tháng";
						 war.add(line);
					 }
				 }
				 id = rsDeadline.getString("ID_EMPLOYEE");
				 idEmployee.add(id);
			}
			for(String line:war){
				 content = content + "\n" + line;
			 }
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(content);
			alert.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
