package ManageInformation;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import GeneralIformation.Ability;
import GeneralIformation.Contract;
import GeneralIformation.Impact;
import GeneralIformation.Position;
import Structure.Employee;
import adapter.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class InfomationController {
	private static Employee employee; 
	
	@FXML
	private TextField tefName ;
	@FXML
	private TextField tefID;
	@FXML
	private TextField tefNationality;
	@FXML
	private TextField tefIDcard;
	@FXML
	private TextField tefPhoneNumbers;
	@FXML
	private TextField tefPosition;
	@FXML
	private TextField tefTitle;
	@FXML
	private TextField tefInstitute;
	@FXML
	private TextField tefedl;
	@FXML 
	private TextField tefcertificate;
	@FXML
	private TextField tefIdContract;
	@FXML
	private ComboBox<String> cbSex;
	@FXML
	private DatePicker dapBirthday;
	@FXML
	private DatePicker dapStart;
	@FXML
	private DatePicker dapEnd;
	@FXML
	private  Button butSave;
	@FXML
	private  Button butAddKT;
	@FXML
	private  Button butAddKL;
	@FXML
	private  Button butSelect;
	@FXML
	private ImageView imgAva;
	
	public static Employee getEmployee() {
		return employee;
	}

	public static void setEmployee(Employee employee) {
		InfomationController.employee = employee;
	}

	public void initialize(){
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");
		
		cbSex.setItems(list);
		
		
		loadInformation();
	    Image imageDecline = new Image(getClass().getResourceAsStream("save.PNG"));
	    butSave.setGraphic(new ImageView(imageDecline));
		
	}
	
	public void loadInformation(){
		
		tefName.setText(employee.getName());
		tefID.setText(employee.getID());
		tefNationality.setText(employee.getNationality());
		tefIDcard.setText(employee.getIdCardNumber());
		tefPhoneNumbers.setText(employee.getPhoneNumber());
		tefPosition.setText(employee.getPosition().getPosition());
		tefTitle.setText(employee.getPosition().getTitle());
		tefInstitute.setEditable(false);
		tefInstitute.setText(employee.getWorkplace().getName());
		tefedl.setText(employee.getAbility().getEducationLevel());
		tefIdContract.setText(employee.getContract().getID());
		tefcertificate.setText(employee.getAbility().getCertificate());
		if(employee.getSex()){
			cbSex.getSelectionModel().select(0);
		}
		else
			cbSex.getSelectionModel().select(1);
			
		dapBirthday.setValue(employee.getBirthday());
		dapStart.setValue(employee.getContract().getStartingDay());
		dapEnd.setValue(employee.getContract().getTimeToExpired());
		
		ObservableList<Impact> listKT =  FXCollections.observableArrayList();
		ObservableList<Impact> listKL =  FXCollections.observableArrayList();
		if(employee.getImpact() != null){
		for(Impact Imp:employee.getImpact()){
			if(Imp.getType())
				listKT.add(Imp);
			else
				listKL.add(Imp);
		}
		}
		
		
		ConnectToDatabase  newConn= new ConnectToDatabase();
		Connection  conn = newConn.Connec("company.db");
		String querry = "SELECT URL_IMAGE FROM EMPLOYEE WHERE ID =?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ByteArrayOutputStream ouput = new ByteArrayOutputStream();
	
		try{
			pstmt = conn.prepareStatement(querry);
            pstmt.setString(1, employee.getID());
            rs = pstmt.executeQuery();
            if( rs.getBinaryStream("URL_IMAGE") != null){
            	
            
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("URL_IMAGE");
               
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                	ouput.write(buffer);
                }
            }
           
            	 ByteArrayInputStream input = new ByteArrayInputStream(ouput.toByteArray());
            	 Image newImage = new Image(input);
            	 imgAva.setImage(newImage);
            }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
 
                if (conn != null) {
                    conn.close();
                }
 
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } 
        }
		
	}
	
	public void saveCl(){
		/**if (tefID.getText().equals("") || tefPosition.getText().equals("") || tefTitle.getText().equals("") || tefIdContract.getText().equals("")
		||dapStart.getValue()==null|| dapEnd.getValue()==null ||tefedl.getText().equals("") || tefcertificate.getText().equals("")
		||tefName.getText().equals("") || dapBirthday.getValue()==null || tefIDcard.getText().equals("") || 
		tefPhoneNumbers.getText().equals("'") || tefNationality.getText().equals("") ) {
					Alert alert = new Alert(AlertType.ERROR);
			
					alert.setContentText("Xin vui lòng nhập đầy đủ thông tin!");

					alert.showAndWait();
				}*/
		
		
		if(tefTitle.getText().isEmpty() || tefID.getText().isEmpty() || tefNationality.getText().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Xin vui lòng nhập đầy đủ thông tin!");
			alert.showAndWait();
		}
		else {	
		ConnectToDatabase conect = new ConnectToDatabase();
		Connection conn;
		conn = conect.Connec("company.db");
		boolean sex = true;
		if(cbSex.getSelectionModel().getSelectedItem().equals("Nữ"))
			sex = false;
		Position pos = new Position(employee.getID(), tefPosition.getText(), tefTitle.getText(), employee.getPosition().getAllowance());
		Contract cot = new Contract(tefIdContract.getText(), employee.getID(), dapStart.getValue(), dapEnd.getValue());
//		System.out.println(cot.getIdEmployee());
//		System.out.println(cot.getID());
//		System.out.println(cot.getStartingDay());
//		System.out.println(cot.getTimeToExpired());
		Ability abl = new Ability(employee.getID(), tefedl.getText(), tefcertificate.getText()); 
		try {
			Manager.getInstance(null).editEmployee(employee, tefName.getText(), dapBirthday.getValue(), sex , tefIDcard.getText(), tefPhoneNumbers.getText(), tefNationality.getText(), pos,cot, abl, conn);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		Stage stage = (Stage) butSave.getScene().getWindow();
	    stage.close();
		}
	}
	
	public void butAddKTControler(){
		try{
		Stage newstage = new Stage();
		Parent EmployeeInfo = FXMLLoader
				.load(getClass().getResource("/ManageInformation/Impact.fxml"));
		Scene newScene = new Scene(EmployeeInfo);
		newstage.setScene(newScene);
		newstage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void butAddKLControler(){
		try{
			Stage newstage = new Stage();
			Parent EmployeeInfo = FXMLLoader
					.load(getClass().getResource("/ManageInformation/Impact.fxml"));
			Scene newScene = new Scene(EmployeeInfo);
			newstage.setScene(newScene);
			newstage.show();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	public void selectAIamge(){
		ConnectToDatabase  newConn= new ConnectToDatabase();
		Connection  conn = newConn.Connec("company.db");
		String querry = "UPDATE EMPLOYEE "
                + "SET URL_IMAGE = ? "
                + "WHERE ID=?";
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("file PNG","*.PNG"));
		File selectFile = fileChooser.showOpenDialog(null);
		if(selectFile != null){
			//System.out.println(selectFile.getPath());
			try {
				PreparedStatement pstmt = conn.prepareStatement(querry);
				
				String localUrl = selectFile.toURI().toURL().toString();
				Image im = new Image(localUrl);
				imgAva.setImage(im);
				
				ByteArrayOutputStream bos = null;
		       
		        File f = new File(selectFile.getPath());
		        FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[1024];
				bos = new ByteArrayOutputStream();
				for (int len; (len = fis.read(buffer)) != -1;) {
					bos.write(buffer, 0, len);
		        }
				
				 pstmt.setBytes(1, bos.toByteArray());
		         pstmt.setString(2, tefID.getText());
		 
		         pstmt.executeUpdate();
		     }catch (FileNotFoundException e1) {
		    	 e1.printStackTrace();
		     } catch (IOException e2) {
		    	 e2.printStackTrace();
		      
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else{
			
		}
	}
}
