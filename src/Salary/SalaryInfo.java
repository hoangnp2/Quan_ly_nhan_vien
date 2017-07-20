package Salary;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Structure.Employee;
import adapter.ConnectToDatabase;

public class SalaryInfo {
	private String idEmployee;
	
	private String name;
	
	private String title;
	
	private Integer salary=0;
	
	private String dayOfMonth="0";
	
	private String workDays="0";
	
	private Integer result=0;
	
	public SalaryInfo(Employee e) throws SQLException {
		idEmployee=e.getID();
		name=e.getName();
		title=e.getPosition().getTitle();
		ConnectToDatabase cntd=new ConnectToDatabase();
		Connection con=cntd.Connec("company.db");
		Statement stmt2=con.createStatement();
		Statement stmt3=con.createStatement();
		ResultSet rs=stmt2.executeQuery("SELECT * FROM SALARYINFO;");
		boolean ok=false;
		while (rs.next()) {
			String s=rs.getString("ID_EMPLOYEE");
			if (s.equals(e.getID())) {
				salary=rs.getInt("SALARY");
				dayOfMonth=rs.getString("DAYOFMONTH");
				workDays=rs.getString("WORKDAYS");
				result=rs.getInt("RESULT");
				ok=true;
				break;
			}
		}
		rs.close();
		Statement stmt1=con.createStatement();
		if (ok==false) {
			ResultSet rss=stmt1.executeQuery("SELECT * FROM SALARY;");
			while (rss.next()) {
				String s=rss.getString("TITLE");
				if (s.equals(title)) {
					salary=rss.getInt("SALARY");
					break;
				}
			}
			
			String txt="INSERT INTO SALARYINFO(ID_EMPLOYEE,NAME,TITLE,SALARY,DAYOFMONTH,WORKDAYS,RESULT) VALUES ('"+idEmployee+"','";
			txt=txt+name+"','"+title+"',"+salary+",'0','0',0);";
			stmt3.executeUpdate(txt);
		}
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String position) {
		this.title = position;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
