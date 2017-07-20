package GeneralIformation;
/**
 * Vị trí công tác
 * @author nguyen
 *
 */
public class Position {
	/***/
	private String idEmployee;
	/***/
	private String position;
	
	/***/
	private String title;
	
	/***/
	private int allowance;
	
	public String getidEmployee() {
		return idEmployee;
	}

	public void setID(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAllowance() {
		return allowance;
	}

	public void setAllowance(int allowance) {
		this.allowance = allowance;
	}
	public Position(String id){
		this.idEmployee = id;
		
	}
	public Position(String idEmployee, String position, String title, int allowance) {
		super();
		this.idEmployee = idEmployee;
		this.position = position;
		this.title = title;
		this.allowance = allowance;
	}

	
}
