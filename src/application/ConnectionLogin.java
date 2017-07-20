package application;

import java.sql.*;

public class ConnectionLogin {
	static final String DangNhap = "jdbc:sqlite:newdatabase.db" ;
	public static Connection Connec(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DangNhap);
		} catch (SQLException e) {
			
			 System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public boolean ConnectoDangnhap(String id,String pas){
		
		String conndangnhap = "SELECT PASSWORD FROM dangnhap WHERE ID = "+id;
		Connection conndangnhap1 = this.Connec();
		Statement stmt;
		try {
			stmt = conndangnhap1.createStatement();
			ResultSet rs    = stmt.executeQuery(conndangnhap);
			
			if(rs.getString("PASSWORD").equals(pas)){
				System.out.println(rs.getString("PASSWORD"));
				return true;
				
			}
			else
				System.out.println("Fail!!!");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
		
	}
}
