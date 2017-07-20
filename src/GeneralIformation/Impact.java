package GeneralIformation;

/**
 * Khen thưởng hoặc ky luật
 * @author nguyen
 *
 */
public class Impact {
	private String id;

	private String idEmployee;

	private boolean type;
	
	private String couse;

	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	public boolean getType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getCouse() {
		return couse;
	}

	public void setCouse(String couse) {
		this.couse = couse;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Impact(String id) {
		this.id = id;
	}
	
	public Impact(String id,boolean type, String idEmployee, String couse, String content) {
		super();
		this.id = id;
		this.type = type;
		this.idEmployee = idEmployee;
		this.couse = couse;
		this.content = content;
	}

}
