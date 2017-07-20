package Salary;

import java.io.File; 

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Structure.Company;
import Structure.Department;
import Structure.Employee;
import Structure.Institute;
import adapter.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class OptionController {
	@FXML
	private ListView<Department> livDepartments;
	@FXML
	private Button butSelect;
	@FXML
	private Button butSelectAll;
	@FXML
	private TableView<SalaryInfo> tableS=new TableView<SalaryInfo>();
	@FXML
	private TableColumn<SalaryInfo, String> tbcName;
	@FXML
	private TableColumn<SalaryInfo,Integer> tbcSalary;
	@FXML
	private TableColumn<SalaryInfo, String> tbcTitle;
	@FXML
	private TableColumn<SalaryInfo, String> tbcDate1;
	@FXML
	private TableColumn<SalaryInfo, String> tbcDate2;
	@FXML
	private TableColumn<SalaryInfo,Integer> tbcResult;
	@FXML
	private Button butTL;
	@FXML
	private Button butSave;
	List<SalaryInfo> listS=new ArrayList<>();
	
	public void initialize(){
		tableS.setEditable(true);
		ObservableList<Department> departments =  FXCollections.observableArrayList();
		Company com=Company.getInstance(null);
		for (Department de: com.getDepartments()) departments.add(de);
		livDepartments.setItems(departments);
		tbcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tbcTitle.setCellFactory(TextFieldTableCell.<SalaryInfo> forTableColumn());
		tbcSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		tbcDate1.setCellValueFactory(new PropertyValueFactory<>("dayOfMonth"));
		tbcDate1.setCellFactory(TextFieldTableCell.<SalaryInfo> forTableColumn());
		tbcDate1.setOnEditCommit((CellEditEvent<SalaryInfo, String> event)->{
			String newDate1 = event.getNewValue();
			try{
			int i=Integer.parseInt(newDate1);
			TablePosition<SalaryInfo, String> pos = event.getTablePosition();
			int row=pos.getRow();
			SalaryInfo s=event.getTableView().getItems().get(row);
			s.setDayOfMonth(newDate1);
			String txt="UPDATE SALARYINFO SET DAYOFMONTH='"+newDate1+"' WHERE ID_EMPLOYEE='"+s.getIdEmployee()+"';";
			ConnectToDatabase conn=new ConnectToDatabase();
			conn.querryUpdate(txt,"company.db");
			}
			catch (Exception e) {
				System.out.println("Abc");
			}
			
		});
		tbcDate2.setCellValueFactory(new PropertyValueFactory<>("workDays"));
		tbcDate2.setCellFactory(TextFieldTableCell.<SalaryInfo> forTableColumn());
		tbcDate2.setOnEditCommit((CellEditEvent<SalaryInfo, String> event)->{
			String newDate2 = event.getNewValue();
			TablePosition<SalaryInfo, String> pos = event.getTablePosition();
			int row=pos.getRow();
			SalaryInfo s=event.getTableView().getItems().get(row);
			s.setWorkDays(newDate2);
			String txt="UPDATE SALARYINFO SET WORKDAYS='"+newDate2+"' WHERE ID_EMPLOYEE='"+s.getIdEmployee()+"';";
			ConnectToDatabase conn=new ConnectToDatabase();
			conn.querryUpdate(txt,"company.db");
		});
		tbcResult.setCellValueFactory(new PropertyValueFactory<>("result"));
	}
	
	public void selectAll() throws IOException, SQLException {
		listS.removeAll(listS);
		ObservableList<SalaryInfo> slrs =  FXCollections.observableArrayList();
		Company com=Company.getInstance(null);
		for (Department de: com.getDepartments()) {
			for (Institute in: de.getMember()) {
				for (Employee e: in.getMembers()){
					SalaryInfo s=new SalaryInfo(e);
					slrs.add(s);
					listS.add(s);
				}
			}
		}
		tableS.setItems(slrs);
		
	}
	
	public void select() throws IOException, SQLException {
		listS.removeAll(listS);
		Department de=livDepartments.getSelectionModel().getSelectedItem();
		ObservableList<SalaryInfo> slrs =  FXCollections.observableArrayList();
		for (Institute in: de.getMember()) {
			for (Employee e: in.getMembers()){
				SalaryInfo s=new SalaryInfo(e);
				slrs.add(s);
				listS.add(s);
			}
		}
		tableS.setItems(slrs);
	}
	
	public void calSalary(){
		CalculationSalary cal =new CalculationSalary(listS);
		cal.calSalary();
		try{
			for (SalaryInfo s: listS) {
				String txt="UPDATE SALARYINFO SET RESULT= "+s.getResult()+" WHERE ID_EMPLOYEE= '"+s.getIdEmployee()+"';";
				String newDate1=s.getWorkDays();
				int i=Integer.parseInt(newDate1);
				String newDate2=s.getDayOfMonth();
				int j=Integer.parseInt(newDate2);
				ConnectToDatabase con=new ConnectToDatabase();
				con.querryUpdate(txt,"company.db");
			}
			tableS.refresh();
		}
		catch(Exception e) {
			System.out.println("abc");
		}
	}
	
	public void saveTable(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("excel file(*.xls)","*.xls"));
		fileChooser.setTitle("Save file");
		File savedFile = fileChooser.showSaveDialog(null);
		
         if (savedFile != null) {
        	
        	 try {
        		File excelFile = new File(savedFile.getPath() );
				WritableWorkbook workbook = Workbook.createWorkbook(excelFile);
				WritableSheet sheet = workbook.createSheet("Bang Luong", 0);
				int i=0,j=1;
				sheet.addCell(new Label(0, 0, "Tên"));
				sheet.addCell(new Label(1, 0, "Chức Vụ"));
				sheet.addCell(new Label(2, 0, "Lương Gốc"));
				sheet.addCell(new Label(3, 0, "Ngày Làm việc trong tháng"));
				sheet.addCell(new Label(4, 0, "Ngày làm việc thực tế"));
				sheet.addCell(new Label(5, 0, "Lương"));
				for(SalaryInfo sal:tableS.getItems()){
					sheet.addCell(new Label(i, j,sal.getName() ));
					i++;
					sheet.addCell(new Label(i, j,sal.getTitle() ));
					i++;
					double number = sal.getSalary().doubleValue();
					sheet.addCell(new jxl.write.Number(i,j,number));
					i++;
					sheet.addCell(new Label(i, j,sal.getWorkDays() ));
					i++;
					sheet.addCell(new Label(i, j,sal.getDayOfMonth() ));
					i++;
					double number2 = sal.getResult().doubleValue();
					sheet.addCell(new jxl.write.Number(i,j,number2));
					i=0;
					j++;
				}
				workbook.write();
				workbook.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
         } else {
             
         }
	}
}
