package Salary;


import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class CalculationSalary {
	private List<SalaryInfo> list;
	
	public CalculationSalary(List<SalaryInfo> l) {
		list=l;
	}
	
	public void calSalary() {
		for (SalaryInfo s: list) {
			try{
				int a=Integer.parseInt(s.getDayOfMonth());
				int b=Integer.parseInt(s.getWorkDays());
				if (b > 31 || b < 1 || a > 31 || a<1 ){
					Alert alert = new Alert(AlertType.ERROR);
					
					alert.setContentText("Nhập số ngày của" +s.getName()+"-"+s.getIdEmployee()+"không hợp lệ");

					alert.showAndWait();
				}
				if(a != 0 && b<=a){
					s.setResult(s.getSalary()*b/a);
				}
				else if(a==0) {
					s.setResult(0);
				}
				else if (b>a ) {
					Alert alert = new Alert(AlertType.ERROR);
					
					alert.setContentText("Số ngày đi làm của" +s.getName()+"-"+s.getIdEmployee()+"lớn hơn số ngày trong tháng!! Vui lòng nhập lại");

					alert.showAndWait();
				}
				
				
			}
			catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				
				alert.setContentText("Số ngày không hợp lệ,Xin vui lòng nhập lại số ngày của "+s.getName()+"-"+s.getIdEmployee());

				alert.showAndWait();
			}
		
		}
	}
}
