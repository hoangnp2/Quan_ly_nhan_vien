package Structure;

import java.util.List;

public class Company {
	private static Company instance;
	private List<Department> departments;

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	private Company(List<Department> departments) {
		super();
		this.departments = departments;
	}
	
	public static Company getInstance(List<Department> departments){
		if(instance == null)
			 instance = new Company(departments);
		return instance;
	}
	
	public boolean deleteDepartment(Department  d) {
		if (instance!=null) {
			return departments.remove(d);
		}
		return false;
	}
}
