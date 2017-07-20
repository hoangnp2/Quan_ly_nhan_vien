package Structure;

import java.util.ArrayList;
import java.util.List;

public class Institute {
	

	/***/
	private String name;
	/***/
	private String ID;
	/***/
	protected List<Employee> members;
	/***/
	private Department department;
	
	public  String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public List<Employee> getMembers() {
		return members;
	}

	public void setMembers(List<Employee> members) {
		this.members = members;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public boolean addAmember(Employee anyone){
		members.add(anyone);
		return true;
	}
	
	public Institute(String name, String iD, Department department) {
		this.name = name;
		ID = iD;
		this.department = department;
		members = new ArrayList<Employee>();
	}

	public Institute(String name, String iD, List<Employee> members, Department department) {
		this.name = name;
		ID = iD;
		this.members = members;
		this.department = department;
	}
	
	public boolean deleteEmployee(Employee e) {
		return members.remove(e);
	}
	
	@Override
	public String toString(){
		return name;
	}
}
