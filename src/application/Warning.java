package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import adapter.ConnectToDatabase;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class Warning {
	DateTimeFormatter  df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public void load(){
		
		ConnectToDatabase newconn = new ConnectToDatabase();
		Connection conn = newconn.Connec("company.db");
		
		ArrayList<LocalDate> listEnd = new ArrayList<LocalDate>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rsDeadline = stmt.executeQuery("SELECT * FROM CONTRACT;");
			LocalDate date = null ;
			while(rsDeadline.next()){
				date = LocalDate.parse(rsDeadline.getString("INVALID"), df);
				listEnd.add(date);
			}
			LocalDate now = LocalDate.now();
			for(LocalDate mdate:listEnd){
				Period compare = Period.between( now,mdate);
				 System.out.println(compare.getMonths());
				 System.out.println(now);
				 System.out.println(mdate);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
