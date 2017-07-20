package ManageInformation;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import Structure.Company;
import Structure.Department;
import Structure.Employee;
import Structure.Institute;
import adapter.ConnectToDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;;

public class ProfileController {
	private static final String liste = null;
	@FXML
	private ImageView imgWarning;
	@FXML
	private TextField TestTextFiel;
	@FXML
	private  TreeView<Item> treeCompany;

	@FXML
	private  TreeView<String> myTree;

	@FXML
	private TableView<Employee> tableE;
	
	@FXML
	private TableColumn<Employee, String> tbcName;
	
	@FXML
	private TableColumn<Employee, LocalDate> tbcBirthday;
	
	@FXML
	private TableColumn<Employee, String> tbcID;
	
	@FXML
	private TableColumn<Employee, String> tbcNat;
	
	@FXML
	private MenuItem MeniItemAddUser;
	@FXML
	private Button butS;
	@FXML
	private Menu mnHoTro;
	@FXML
	private MenuItem mniHistory;
	@FXML
	private MenuItem mniSetting;
	@FXML
	private Button butAdd;
	
	
	DateTimeFormatter  df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
	
	
	public ProfileController() {
		treeCompany = new TreeView<Item>();
		myTree = new TreeView<String>();
	}

	
	
	public void initialize() {
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
						Image img = new Image("/ManageInformation/exit.PNG");
						imgWarning.setImage(img);
						break;
					 }
				 }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Image imageDecline = new Image(getClass().getResourceAsStream("as.PNG"));
		butAdd.setGraphic(new ImageView(imageDecline));
		tableE.setEditable(true);
		loadComponents(this.treeCompany,Company.getInstance(null));
		
		tbcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		tbcName.setCellFactory(TextFieldTableCell.<Employee> forTableColumn());
		
		tbcBirthday.setCellValueFactory(new  PropertyValueFactory<>("birthday"));
		tbcID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbcNat.setCellValueFactory(new PropertyValueFactory<>("nationality"));
		
		ArrayList<Employee> liste = new ArrayList<Employee>();
		Company company = Company.getInstance(null);
		for(Department dep:company.getDepartments()){
			for(Institute ins:dep.getMember()){
				for(Employee emp:ins.getMembers()){
					liste.add(emp);
				}
			}
		}
		
		
		
		TestTextFiel.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
				if(!newValue.isEmpty())
				loadListTreeView(searchEmployee(newValue,liste));
				tableE.refresh();
		}
		
		});
		MeniItemAddUser.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	 try {
	        	 Stage newstage = new Stage();
					Parent EmployeeInfo = FXMLLoader
							.load(getClass().getResource("/ManageInformation/scene_CreatAcount.fxml"));
					Scene newScene = new Scene(EmployeeInfo);
					newstage.getIcons().add(new Image("application/Capture.PNG"));
					newstage.setScene(newScene);
					newstage.show();
	        	 } catch (IOException e) {
						e.printStackTrace();
					}
	         }
	         });
		/**Các chức năng của tableView*/
		ContextMenu contextMenuTableView = new ContextMenu();
		MenuItem itemTableView1 = new MenuItem("Thông Tin Chi Tiết");
		MenuItem itemTableView2 = new MenuItem("Xóa");
		itemTableView1.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	 try {
	        		Employee e;
					if (tableE.getSelectionModel().getSelectedItem() != null) {
						e = tableE.getSelectionModel().getSelectedItem();
						InfomationController.setEmployee(e);
						Stage newstage = new Stage();
						Parent EmployeeInfo = FXMLLoader
								.load(getClass().getResource("/ManageInformation/scene_Infomation.fxml"));
						Scene newScene = new Scene(EmployeeInfo);
						newstage.getIcons().add(new Image("application/Capture.PNG"));
						newstage.setScene(newScene);
						newstage.show();
						newstage.setOnHiding(new EventHandler<WindowEvent>() {
						      public void handle(WindowEvent we) {
						    	  	tableE.refresh();
						      }
						  }); 
	        		 }
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	         }
	     });
		itemTableView2.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	
	        		Employee e;
					if (tableE.getSelectionModel().getSelectedItem() != null) {
						e = tableE.getSelectionModel().getSelectedItem();
						Institute ins = e.getWorkplace();
						Manager.getInstance(null).deleteEmployee(e);
						loadEmployee(ins);
					}
	         }
	     });
		contextMenuTableView.getItems().addAll(itemTableView1,itemTableView2);
		tableE.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	           @Override
	           public void handle(ContextMenuEvent event) {
	        	   contextMenuTableView.show(tableE, event.getScreenX(), event.getScreenY());
	           }
	       });
		
		//Các chức năng của treeview
		ContextMenu contextMenuTreeView = new ContextMenu();
		MenuItem itemTreeView1 = new MenuItem("Xóa");
		itemTreeView1.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	TreeItem<Item> chose = treeCompany.getSelectionModel().getSelectedItem();
	        	if(chose.getValue().getType() == 2){
	        		Company company = Company.getInstance(null);
	        		for(Department dep:company.getDepartments()){
	        			if(dep.getID().equals(chose.getValue().getID())){
	        				Manager.getInstance(null).deleteDepartment(dep);
	        				break;
	        			}
	        		}	
	        		loadComponents(treeCompany,Company.getInstance(null));
	        		
    				contextMenuTreeView.hide();
	        	}
	        	if(chose.getValue().getType() == 3){
	        		Company company = Company.getInstance(null);
	        		for(Department dep:company.getDepartments()){
	    				for(Institute ins:dep.getMember()){
	    					if(ins.getID().equals(chose.getValue().getID())){
	    						Manager.getInstance(null).deleteInstitute(ins);
	    						break;
	    					}
	    				}
	    				loadComponents(treeCompany,Company.getInstance(null));
		        		
	    				contextMenuTreeView.hide();
	    			}
	        	}
	        	
	         }
	     });
		
		MenuItem itemTreeView2 = new MenuItem("Thêm Thành Phần");
		itemTreeView2.setOnAction(new EventHandler<ActionEvent>() {
			 
	         public void handle(ActionEvent event) {
	        	TreeItem<Item> chose = treeCompany.getSelectionModel().getSelectedItem();
	        	if(chose.getValue().getType() == 1){
	        		
	        		try{
	        		Stage newstage = new Stage();
					Parent EmployeeInfo = FXMLLoader
							.load(getClass().getResource("/ManageInformation/scene_Element.fxml"));
					Scene newScene = new Scene(EmployeeInfo);
					newstage.getIcons().add(new Image("application/Capture.PNG"));
					newstage.setScene(newScene);
					newstage.show();
					newstage.setOnHidden(new EventHandler<WindowEvent>() {
					      public void handle(WindowEvent we) {
					    	  loadComponents(treeCompany,Company.getInstance(null));
					    	 
					      }
					  }); 
	        		} catch (IOException e) {
						e.printStackTrace();
					}
	        		
	        	}
	        	if(chose.getValue().getType() == 2){
	        		try{
		        		Stage newstage = new Stage();
						Parent EmployeeInfo = FXMLLoader
								.load(getClass().getResource("/ManageInformation/scene_Element.fxml"));
						Scene newScene = new Scene(EmployeeInfo);
						newstage.getIcons().add(new Image("application/Capture.PNG"));
						newstage.setScene(newScene);
						newstage.show();
						Company company = Company.getInstance(null);
						for(Department dep:company.getDepartments()){
							
								if(dep.getID().equals(chose.getValue().getID())){
									PartController.setDepartment(dep);
								}
							
						}
						
						newstage.setOnHidden(new EventHandler<WindowEvent>() {
						      public void handle(WindowEvent we) {
						    	  loadComponents(treeCompany,Company.getInstance(null));
						    	 
						      }
						  }); 
		        		} catch (IOException e) {
							e.printStackTrace();
						}
	        	}
	        	
	         }
	     });
		
		
		treeCompany.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	           @Override
	           public void handle(ContextMenuEvent event) {
	        	  
	        		   if(treeCompany.getSelectionModel().getSelectedItem().getValue().getType() == 3){
	        			   contextMenuTreeView.getItems().remove(0,contextMenuTreeView.getItems().size() );
		        		   contextMenuTreeView.getItems().addAll(itemTreeView1);	
		        		  
		        		}
		        	   else if(treeCompany.getSelectionModel().getSelectedItem().getValue().getType() == 2){
		        		   contextMenuTreeView.getItems().remove(0,contextMenuTreeView.getItems().size() );
		        		   contextMenuTreeView.getItems().addAll(itemTreeView1,itemTreeView2);
	        		   	   
		        	   }
		        	   else if(treeCompany.getSelectionModel().getSelectedItem().getValue().getType() == 1){
		        		   contextMenuTreeView.getItems().remove(0,contextMenuTreeView.getItems().size() );
		        		   contextMenuTreeView.getItems().addAll(itemTreeView2);	
		        	   }
	        		   contextMenuTreeView.show(treeCompany, event.getScreenX(), event.getScreenY());
	           }
	       });
	} 
	
	/** lấy dữ liệu cho treeView*/
	public  void loadComponents(TreeView treeCompany, Company acompany){
		Item root  = new Item("Công Ty","COM01",1); 
		TreeItem<Item> rootOfTree = new TreeItem<Item>(root);
		rootOfTree.setExpanded(true);
		
		Item departments ;
		Item Institutes;
		TreeItem<Item> adepartment;
		TreeItem<Item> aInstitute;
		for(Department dep:acompany.getDepartments()){
			departments = new Item(dep.getName(),dep.getID(),2);
			adepartment = new TreeItem<Item>(departments);
			adepartment.setExpanded(true);
			rootOfTree.getChildren().addAll(adepartment);
			
			for(Institute ins:dep.getMember()){
				Institutes = new Item(ins.getName(),ins.getID(),3);
				aInstitute = new TreeItem<Item>(Institutes);
				aInstitute.setExpanded(true);
				adepartment.getChildren().addAll(aInstitute);
				
			}
		}
		
		treeCompany.setRoot(rootOfTree);
		
	}
	
	/** lấy dữ liệu mới table*/
	public void loadListTreeView(ArrayList<Employee> liste){
		ObservableList<Employee> list =  FXCollections.observableArrayList();
		
		list.setAll(liste);
		
		tableE.setItems(list);
		
	}
	
	public ArrayList<Employee> searchEmployee(String s, ArrayList<Employee> employees) {
        ArrayList<Employee> result = new ArrayList<>();

        for(int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().contains(s) || employees.get(i).getName().contains(s)) {
                result.add(employees.get(i));
            }
        }

        return result;
    }
	
	/** Lấy dữ liệu cho TableView */
	public void loadEmployee(Institute ins){
		ObservableList<Employee> list =  FXCollections.observableArrayList();
		for(Employee emp:ins.getMembers()){
			list.add(emp);
		}
		tableE.setItems(list);
		
		
	}
	
	public void butAddController(){
		try{
		TreeItem<Item> chose = treeCompany.getSelectionModel().getSelectedItem();
		if(chose != null)
			
		if(chose.getValue().getType() == 3){
			Company company = Company.getInstance(null);
			for(Department dep:company.getDepartments()){
				for(Institute ins:dep.getMember()){
					if(ins.getID().equals(chose.getValue().getID())){
						addEmpController.setIns(ins);
					}
				}
			}
			Stage newstage = new Stage();
			Parent EmployeeInfo = FXMLLoader
					.load(getClass().getResource("/ManageInformation/scene_Addemp.fxml"));
			Scene newScene = new Scene(EmployeeInfo);
			newstage.getIcons().add(new Image("application/Capture.PNG"));
			newstage.setScene(newScene);
			newstage.show();
			newstage.setOnHidden(new EventHandler<WindowEvent>() {
			      public void handle(WindowEvent we) {
			    		loadEmployee(addEmpController.getIns());
			      }
			  }); 
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mouseClick(MouseEvent mouseEvent){
		TreeItem<Item> chose = treeCompany.getSelectionModel().getSelectedItem();
		if(chose != null)
			//System.out.println(chose.getValue().getContent());
		if(chose.getValue().getType() == 3){
			Company company = Company.getInstance(null);
			for(Department dep:company.getDepartments()){
				for(Institute ins:dep.getMember()){
					if(ins.getID().equals(chose.getValue().getID())){
						loadEmployee(ins);
					}
				}
			}
		}
		
	}
	
	public void history() {
		try{
			Stage newstage = new Stage();
			Parent historyInfo = FXMLLoader
					.load(getClass().getResource("/ManageInformation/scene_History.fxml"));
			Scene newScene=new Scene(historyInfo);
			newstage.getIcons().add(new Image("application/Capture.PNG"));
			newstage.setScene(newScene);
			newstage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void calSalary() {
		try{
			Stage newstage = new Stage();
			Parent historyInfo = FXMLLoader
					.load(getClass().getResource("/Salary/scene_Option.fxml"));
			Scene newScene=new Scene(historyInfo,1000,800);
			newstage.getIcons().add(new Image("application/Capture.PNG"));
			newstage.setScene(newScene);
			newstage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setting() {
		try{
			Stage newstage = new Stage();
			Parent historyInfo = FXMLLoader
					.load(getClass().getResource("/Salary/scene_SalarySetting.fxml"));
			Scene newScene=new Scene(historyInfo);
			newstage.getIcons().add(new Image("application/Capture.PNG"));
			newstage.setScene(newScene);
			newstage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showWarning(){
		ConnectToDatabase newconn = new ConnectToDatabase();
		Connection conn = newconn.Connec("company.db");
		ArrayList<String> war = new ArrayList<String>();
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
