package ManageInformation;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import GeneralIformation.Ability;
import GeneralIformation.Contract;
import GeneralIformation.Position;
import Structure.*;
import adapter.ConnectToDatabase;

public class Manager {
	private static Manager instance;
	private String username;

	private Manager(String username) {
		super();
		this.username = username;
	}

	public String getUseName() {
		return username;
	}

	public static Manager getInstance(String username) {
		if (instance == null)
			instance = new Manager(username);
		return instance;
	}

	private Company company;

	/**
	 * Sua thong tin cua 1 nhan vien
	 * 
	 * @param e
	 *            the employee to set
	 * @param name
	 *            the name to set
	 * @param profile
	 *            the profile to set
	 * @throws SQLException
	 */
	public void editEmployee(Employee e, String name, LocalDate birthday, boolean sex, String idCard,
			String phoneNumber, String nationality, Position position, Contract contract, Ability ability,
			Connection conn) throws SQLException {
		e.setName(name);
		e.setBirthday(birthday);
		e.setSex(sex);
		e.setIdCardNumber(idCard);
		e.setPhoneNumber(phoneNumber);
		e.setNationality(nationality);
		e.setContact(contract);
		e.setPosition(position);
		e.setAbility(ability);
		String txt = "UPDATE EMPLOYEE SET NAME= '" + name + "',BIRTHDAY= '" + birthday + "',SEX= '" + sex
				+ "',NATIONALITY= '";
		txt = txt + nationality + "' ,PHONENUMBER= '" + phoneNumber + "' ,IDCARD= '" + idCard;
		txt = txt + "' WHERE ID='" + e.getID() + "';";
		String txt3 = "UPDATE POSITION SET POSITION ='" + position.getPosition() + "',ALLOWANCE="
				+ position.getAllowance() + ",TITLE='" + position.getTitle();
		txt3 = txt3 + "' WHERE ID_EMPLOYEE='" + e.getID() + "';";
		String txt4 = "UPDATE ABILITY SET EDUCATIONLEVEL='" + ability.getEducationLevel() + "' ,CERTIFICATE='"
				+ ability.getCertificate();
		txt4 = txt4 + "' WHERE ID_EMPLOYEE='" + e.getID() + "';";

		String txt5 = "UPDATE CONTRACT SET START='" + contract.getStartingDay() + "',INVALID='"
				+ contract.getTimeToExpired() + "' WHERE ID_EMPLOYEE='" + contract.getIdEmployee() + "';";

		ConnectToDatabase connect = new ConnectToDatabase();
		connect.querryUpdate(txt, "company.db");
		connect.querryUpdate(txt3, "company.db");
		connect.querryUpdate(txt4, "company.db");
		connect.querryUpdate(txt5, "company.db");
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã sửa thông tin nhân viên:" + name + "-"
				+ e.getID() + " lúc " + ldt + "');";
		connect.querryUpdate(txt2, "company.db");

	}

	public boolean addDeparment(String name, String id) {
		String querry = "INSERT INTO DEPARTMENT (NAME,ID) VALUES ('" + name + "','" + id + "');";
		company.getInstance(null).getDepartments().add(new Department(name, id));
		ConnectToDatabase connect = new ConnectToDatabase();
		connect.querryUpdate(querry, "company.db");
		String txt = username + "added the department" + name;
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã thêm bộ phận:" + name + "-" + id
				+ " lúc " + ldt + "');";
		connect.querryUpdate(txt2, "company.db");
		return true;
	}

	public boolean addInstute(Department de, String name, String id) {
		String querry = "INSERT INTO INSTITUTE (NAME,ID,ID_DEPARTMENT) VALUES ('" + name + "','" + id + "','"
				+ de.getID() + "');";
		de.getMember().add(new Institute(name, id, de));
		ConnectToDatabase connect = new ConnectToDatabase();
		connect.querryUpdate(querry, "company.db");
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã thêm cơ quan:" + name + "-" + id
				+ " lúc " + ldt + "');";
		connect.querryUpdate(txt2, "company.db");
		return true;
	}

	/**
	 * Add an Employee
	 * 
	 * @param i
	 *            the institute of employee
	 * @param ID
	 * @param name
	 * @param birthday
	 * @param sex
	 * @param idCard
	 * @param phoneNumber
	 * @param nationality
	 * @param position
	 * @param contract
	 * @param ability
	 * @param imageAv
	 * @throws SQLException
	 */
	public void addEmployee(Institute i, String ID, String name, LocalDate birthday, boolean sex, String idCard,
			String phoneNumber, String nationality, Position position, Contract contract, Ability ability,
			byte[] imageAv) throws SQLException {
		Employee e = new Employee(ID, name, i);
		e.setBirthday(birthday);
		e.setSex(sex);
		e.setIdCardNumber(idCard);
		e.setPhoneNumber(phoneNumber);
		e.setNationality(nationality);
		e.setPosition(position);
		e.setContact(contract);
		e.setAbility(ability);
		String txt = "INSERT INTO EMPLOYEE (ID,ID_INSTITUTE,NAME,BIRTHDAY,SEX,NATIONALITY,PHONENUMBER,IDCARD) VALUES ('"
				+ ID + "','" + i.getID() + "','" + name + "','";
		txt = txt + birthday + "','" + sex + "','" + nationality + "','" + phoneNumber + "','" + idCard + "');";
		// Sua get allowance thanh double;
		String txt2 = "INSERT INTO POSITION (ID_EMPLOYEE,POSITION,ALLOWANCE,TITLE) VALUES ('" + ID + "','"
				+ position.getPosition() + "','" + position.getAllowance() + "','" + position.getTitle() + "');";
		String txt3 = "INSERT INTO CONTRACT (ID,ID_EMPLOYEE,START,INVALID) VALUES ('" + contract.getID() + "','" + ID
				+ "','" + contract.getStartingDay() + "','" + contract.getTimeToExpired() + "');";
		String txt4 = "INSERT INTO ABILITY (ID_EMPLOYEE,EDUCATIONLEVEL,CERTIFICATE) VALUES ('" + ID + "','"
				+ ability.getEducationLevel() + "','" + ability.getCertificate() + "');";
		String querry = "UPDATE EMPLOYEE " + "SET URL_IMAGE = ? " + "WHERE ID=?";

		ConnectToDatabase cntd = new ConnectToDatabase();
		Connection conn = cntd.Connec("company.db");

		PreparedStatement pstmt = conn.prepareStatement(querry);
		cntd.querryUpdate(txt, "company.db");
		cntd.querryUpdate(txt2, "company.db");
		cntd.querryUpdate(txt3, "company.db");
		cntd.querryUpdate(txt4, "company.db");
		i.getMembers().add(e);
		LocalDateTime ldt = LocalDateTime.now();
		String txt5 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã thêm nhân viên: " + name + "-" + ID
				+ " lúc " + ldt + "');";
		cntd.querryUpdate(txt5, "company.db");

		pstmt.setBytes(1, imageAv);
		pstmt.setString(2, ID);
		if (imageAv != null)
			pstmt.executeUpdate();
	}

	/**
	 * Delete a department
	 * 
	 * @param d
	 *            the department to delete
	 */
	public void deleteDepartment(Department d) {
		List<Institute> I = d.getMember();
		String txt;
		txt = "DELETE FROM DEPARTMENT WHERE ID='" + d.getID() + "';";
		ConnectToDatabase cntd = new ConnectToDatabase();
		cntd.querryUpdate(txt, "company.db");
		for (Institute x : I) {
			txt = "DELETE FROM INSTITUTE WHERE ID='" + x.getID() + "';";
			cntd.querryUpdate(txt, "company.db");
			for (Employee e : x.getMembers()) {
				txt = "DELETE FROM EMPLOYEE WHERE ID='" + e.getID() + "';";
				cntd.querryUpdate(txt, "company.db");
				txt = "DELETE FROM POSITION WHERE ID_EMPLOYEE='" + e.getID() + "';";
				cntd.querryUpdate(txt, "company.db");
				txt = "DELETE FROM CONTRACT WHERE ID_EMPLOYEE='" + e.getID() + "';";
				cntd.querryUpdate(txt, "company.db");
				txt = "DELETE FROM ABILITY WHERE ID_EMPLOYEE='" + e.getID() + "';";
				cntd.querryUpdate(txt, "company.db");
			}
		}
		Company com = company.getInstance(null);
		com.deleteDepartment(d);
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã xóa bộ phận:" + d.getName() + "-"
				+ d.getID() + " lúc " + ldt + "');";
		cntd.querryUpdate(txt2, "company.db");

	}

	/**
	 * Delete a Institute
	 * 
	 * @param i
	 *            the institute to delete
	 */

	public void deleteInstitute(Institute i) {
		String txt;
		ConnectToDatabase cntd = new ConnectToDatabase();
		txt = "DELETE FROM INSTITUTE WHERE ID='" + i.getID() + "';";
		cntd.querryUpdate(txt, "company.db");
		for (Employee e : i.getMembers()) {
			txt = "DELETE FROM EMPLOYEE WHERE ID='" + e.getID() + "';";
			cntd.querryUpdate(txt, "company.db");
			txt = "DELETE FROM POSITION WHERE ID_EMPLOYEE='" + e.getID() + "';";
			cntd.querryUpdate(txt, "company.db");
			txt = "DELETE FROM CONTRACT WHERE ID_EMPLOYEE='" + e.getID() + "';";
			cntd.querryUpdate(txt, "company.db");
			txt = "DELETE FROM ABILITY WHERE ID_EMPLOYEE='" + e.getID() + "';";
			cntd.querryUpdate(txt, "company.db");
		}
		Department d = i.getDepartment();
		d.deleteInstitute(i);
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã xóa cơ quan:" + i.getName() + "-"
				+ i.getID() + " lúc " + ldt + "');";
		cntd.querryUpdate(txt2, "company.db");
	}

	/**
	 * Delete an Employee
	 * 
	 * @param e
	 *            the employee to delete
	 */

	public void deleteEmployee(Employee e) {
		String txt;
		ConnectToDatabase cntd = new ConnectToDatabase();
		txt = "DELETE FROM EMPLOYEE WHERE ID='" + e.getID() + "';";
		cntd.querryUpdate(txt, "company.db");
		txt = "DELETE FROM POSITION WHERE ID_EMPLOYEE='" + e.getID() + "';";
		cntd.querryUpdate(txt, "company.db");
		txt = "DELETE FROM CONTRACT WHERE ID_EMPLOYEE='" + e.getID() + "';";
		cntd.querryUpdate(txt, "company.db");
		txt = "DELETE FROM ABILITY WHERE ID_EMPLOYEE='" + e.getID() + "';";
		cntd.querryUpdate(txt, "company.db");
		Institute i;
		i = e.getWorkplace();
		i.deleteEmployee(e);
		LocalDateTime ldt = LocalDateTime.now();
		String txt2 = "INSERT INTO HISTORY (CONTENT) VALUES ('" + username + " Đã xóa nhân viên:" + e.getName() + "-"
				+ e.getID() + " lúc " + ldt + "');";
		cntd.querryUpdate(txt2, "company.db");

	}
}
