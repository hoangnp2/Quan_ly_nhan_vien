package Structure;

import java.util.ArrayList;

import java.util.List;


public class Department {
	
	/***/
	private String name;
	/***/
	private String ID;
	/***/
	private List<Institute> members;
	/***/
	/**
	 * 
	 */
	
	public String getName() {
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
	public List<Institute> getMember() {
		return members;
	}
	
	public Department(String name, String iD, List<Institute> members) {
		this.name = name;
		ID = iD;
		this.members = members;
	}
	
	public Department(String name, String iD) {
		this.name = name;
		ID = iD;
		members = new ArrayList<Institute>();
	}
	
	public void setMember(List<Institute> member) {
		this.members = member;
	}
	
	public boolean addAInstitute(Institute arg){
		members.add(arg);
		return true;
	}
	
	public boolean deleteInstitute(Institute i) {
		return members.remove(i);
	}
	@Override
	public String toString() {
        return this.name;
    }
	
	
}
