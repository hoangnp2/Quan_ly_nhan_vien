package GeneralIformation;
/**
 * Trình độ học vấn
 * @author nguyen
 *
 */
public class Ability {
	
	/** Mã nhân viên*/
	private String idEmployee;
	/** trình độ*/
	private String educationLevel;
	


	/**chứng chỉ*/
	private String certificate;
	
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getidEmloyee() {
		return idEmployee;
	}

	public void setidEmloyee(String idEmloyee) {
		this.idEmployee = idEmloyee;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	
	
	public Ability(String idEmloyee){
		this.idEmployee = idEmloyee;
	}

	public Ability(String idEmployee, String educationLevel, String certificate) {
		super();
		this.idEmployee = idEmployee;
		this.educationLevel = educationLevel;
		this.certificate = certificate;
	};
	
	
}
