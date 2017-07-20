package adapter;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import GeneralIformation.Ability;
import GeneralIformation.Contract;
import GeneralIformation.Impact;
import GeneralIformation.Position;
import Structure.*;

public class ConnectToDatabase {
	static final String connection = "jdbc:sqlite:";

	public Connection Connec(String nameFile) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connection + nameFile);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}
	
	public int querryUpdate(String querry,String fileName){
		Connection conn;
		conn = this.Connec(fileName);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(querry);
			stmt.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet querry(String querry,String fileName){
		Connection conn;
		conn = this.Connec(fileName);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(querry);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Department> loadDaTa(String fileName) {
		ArrayList<Department> departments = new ArrayList<Department>();

		String loaddepartments = "SELECT * FROM DEPARTMENT";
		String loadInstitutes = "SELECT * FROM INSTITUTE WHERE ID_DEPARTMENT = ";
		String loadEmployees = "SELECT * FROM EMPLOYEE WHERE ID_INSTITUTE = ";
		String loadAbility = "SELECT * FROM ABILITY WHERE ID_EMPLOYEE = ";
		String loadContract= "SELECT * FROM CONTRACT WHERE ID_EMPLOYEE = ";
		String loadPosition= "SELECT * FROM POSITION WHERE ID_EMPLOYEE = ";
		String loadImpact= "SELECT * FROM IMPACT WHERE ID_EMPLOYEE = ";
		
		Connection makeData;
		makeData = this.Connec(fileName);
		Statement stmtDP;
		Statement stmtINS;
		Statement stmtEM;
		Statement stmtPO;
		Statement stmtCO;
		Statement stmtAB;
		Statement stmtIM;
		try {
			DateTimeFormatter  df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			stmtDP = makeData.createStatement();
			stmtINS = makeData.createStatement();
			stmtEM = makeData.createStatement();
			stmtPO = makeData.createStatement();
			stmtCO = makeData.createStatement();
			stmtAB = makeData.createStatement();
			stmtIM = makeData.createStatement();
			ResultSet rsdepartment = stmtDP.executeQuery(loaddepartments);
			while (rsdepartment.next()) {
				String name = rsdepartment.getString("NAME");
				String id = rsdepartment.getString("ID");
				
				Department de = new Department(name, id);
				
				ResultSet rsInstitutes = stmtINS.executeQuery(loadInstitutes + "'" + id + "'");
				while (rsInstitutes.next()) {
					String nameIns = rsInstitutes.getString("NAME");
									String idIns = rsInstitutes.getString("ID");
					Institute Ins = new Institute(nameIns, idIns, de);
					ResultSet rsEmployees = stmtEM.executeQuery(loadEmployees + "'" + idIns + "'");
					
					while(rsEmployees.next()){
						
						String nameE = rsEmployees.getString("NAME");
						
						String idE = rsEmployees.getString("ID");
						LocalDate birE = null;
						
						
						
						birE = LocalDate.parse(rsEmployees.getString("BIRTHDAY"), df);
							
						
						
						boolean sex = rsEmployees.getBoolean("SEX");
						String nat = rsEmployees.getString("NATIONALITY");
						String numbp = rsEmployees.getString("PHONENUMBER");
						String idC = rsEmployees.getString("IDCARD");
						
						ResultSet reAbility = stmtAB.executeQuery(loadAbility + "'" + idE + "'");
						String idEmployee = reAbility.getString("ID_EMPLOYEE");
						String education = reAbility.getString("EDUCATIONLEVEL");
						String certificate = reAbility.getString("CERTIFICATE"); 
					
						
						
						ResultSet reContract = stmtCO.executeQuery(loadContract + "'" + idE + "'");
						String idContract = reContract.getString("ID");
						String idEmpC  = reContract.getString("ID_EMPLOYEE");
						
						LocalDate starD = null;
						
							starD =  LocalDate.parse(reContract.getString("START"),df);
						
						LocalDate endD = null;
						
							endD = LocalDate.parse(reContract.getString("INVALID"),df);
						
						
						
						ResultSet rePosition = stmtPO.executeQuery(loadPosition+ "'" + idE + "'");
						String idEmpPos  = rePosition.getString("ID_EMPLOYEE");
						String title = rePosition.getString("TITLE");
						String positionPos = rePosition.getString("POSITION");
						int allowancePos = rePosition.getInt("ALLOWANCE");
						
						
						ResultSet reImpact = stmtIM.executeQuery(loadImpact + "'" + idE + "'");
						ArrayList<Impact> listImpact = new ArrayList<Impact>();
						while(reImpact.next()){
							String idEmpImp  = reImpact.getString("ID_EMPLOYEE");
							String idImp = reImpact.getString("ID");
							boolean typeImp = reImpact.getBoolean("TYPE");
							String couseImp = reImpact.getString("COUSES");
							String contentImp = reImpact.getString("CONTENT");
							listImpact.add(new Impact(idImp,typeImp,idEmpImp,couseImp,contentImp));
						}
						
						Employee newE = new Employee(idE, nameE, Ins, birE, sex, idC, numbp, nat,
								new Ability(idEmployee,education,certificate),listImpact
								,new Position(idEmpPos,positionPos,title,allowancePos),new Contract(idContract, idEmpC, starD, endD));
						
						Ins.getMembers().add(newE);
						
					}
					
					de.addAInstitute(Ins);

				}
				
				departments.add(de);
			}
			stmtDP.close();
			stmtINS.close();
			stmtEM.close();
			stmtPO.close();
			stmtCO.close();
			stmtAB.close();
			stmtIM.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return departments;

	}

}
