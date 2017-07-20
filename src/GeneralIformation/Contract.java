package GeneralIformation;

import java.time.*;

/**
 * Hợp đồng
 * 
 * @author nguyen
 *
 */
public class Contract {
	/*Mã hợp đồng*/
	private String id;
	/*Mã nhân Viên*/
	private String idEmployee;
	/*Ngày bắt đầu*/
	private LocalDate startingDay;
	/*Hạn Hợp đồng*/
	private LocalDate timeToExpired;

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		this.id = iD;
	}

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public LocalDate getStartingDay() {
		return startingDay;
	}

	public void setStartingDay(LocalDate startingDay) {
		this.startingDay = startingDay;
	}

	public LocalDate getTimeToExpired() {
		return timeToExpired;
	}

	public void setTimeToExpired(LocalDate timeToExpired) {
		this.timeToExpired = timeToExpired;
	}

	public Contract(String iD) {
		this.id = iD;
	}

	public Contract(String iD, String idEmployee, LocalDate startingDay, LocalDate timeToExpired) {
		super();
		id = iD;
		this.idEmployee = idEmployee;
		this.startingDay = startingDay;
		this.timeToExpired = timeToExpired;
	}

}
