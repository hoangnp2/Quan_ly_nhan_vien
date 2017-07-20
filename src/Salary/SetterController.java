package Salary;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import adapter.ConnectToDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SetterController {
	@FXML
	private Button butAdd2;
	@FXML
	private Button butAdd;
	@FXML
	private TableView<Setter> tableST =  new TableView<Setter>();
	@FXML
	private TableColumn<Setter, String> tbcTitle2 ;
	@FXML
	private TableColumn<Setter, String> tbcSalary2;
	@FXML
	private MenuItem mniDelete;
	
	public void initialize(){
		ConnectToDatabase con=new ConnectToDatabase();
		Connection conn=con.Connec("company.db");
		ObservableList<Setter> list =  FXCollections.observableArrayList();
		Image imageDecline = new Image(getClass().getResourceAsStream("as.PNG"));
		butAdd2.setGraphic(new ImageView(imageDecline));
		try{
		tableST.setEditable(true);
		
		
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM SALARY");
		while (rs.next()) {
			Setter s=new Setter();
			s.setTitle(rs.getString("TITLE"));
			s.setSalary(rs.getString("SALARY"));
			list.add(s);
		}
		rs.close();
		tbcTitle2.setCellValueFactory(new PropertyValueFactory<>("title"));
		tbcSalary2.setCellValueFactory(new PropertyValueFactory<>("salary"));
		tbcSalary2.setCellFactory(TextFieldTableCell.<Setter> forTableColumn());
		tbcSalary2.setOnEditCommit((CellEditEvent<Setter, String> event)->{
			TablePosition<Setter, String> pos = event.getTablePosition();
			int row=pos.getRow();
			Setter s=event.getTableView().getItems().get(row);
			String txt=event.getNewValue();
			s.setSalary(txt);
			String txt2="UPDATE SALARY SET SALARY= "+txt+" WHERE TITLE='"+s.getTitle()+"';";
			con.querryUpdate(txt2,"company.db");
			
		});
		tableST.setItems(list);
		}catch(SQLException exc){
			exc.printStackTrace();
		}
		
		mniDelete.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	 if(tableST.getSelectionModel().getSelectedItem() != null){
	        	 String txt3="DELETE FROM SALARY WHERE TITLE= '"+ tableST.getSelectionModel().getSelectedItem().getTitle() + "'";
	        	 list.remove(tableST.getSelectionModel().getSelectedItem());
	        	 con.querryUpdate(txt3,"company.db");
	        	 tableST.refresh();
	        	 
	        	 
	        	 }
	         }
	     });
	}
	
	
	public void ButAddController(){
		Stage newstage = new Stage();
		Parent addTitle;
		try {
			addTitle = FXMLLoader
					.load(getClass().getResource("/Salary/scene_AddSalary.fxml"));
		
		Scene newScene=new Scene(addTitle);
		newstage.setScene(newScene);
		newstage.show();
		
		newstage.setOnHidden(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent we) {
		    	  initialize();
		      }
		  }); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
