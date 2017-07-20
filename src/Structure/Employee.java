package Structure;
import java.time.LocalDate;

import java.util.List;

import  GeneralIformation.*;

public class Employee {
	/***/
	private String ID;
	/**Name of Employee*/
	private String name;
	/***/
	private Institute workplace;
	/***/
	private LocalDate birthday;
	
	private boolean sex;
	
	private String idCardNumber;
	
	private String phoneNumber;
	
	private String nationality;
	

	/***/
	private Ability ability;
	/***/
	private List<Impact> impact;
	/***/
	private Position position;
	/***/
	private Contract contract;

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Institute getWorkplace() {
		return workplace;
	}
	public void setWorkplace(Institute workplace) {
		this.workplace = workplace;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public Ability getAbility() {
		return ability;
	}
	public void setAbility(Ability ability) {
		this.ability = ability;
	}
	public List<Impact> getImpact() {
		return impact;
	}
	public void setImpact(List<Impact> impact) {
		this.impact = impact;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContact(Contract contact) {
		this.contract = contact;
	}
	
	
	public Employee(String iD, String name, Institute workplace) {
		super();
		ID = iD;
		this.name = name;
		this.workplace = workplace;
		//workplace.getMembers().add(this);
	}
	public Employee(String iD, String name, Institute workplace, LocalDate birthday, boolean sex, String idCardNumber,
			String phoneNumber, String nationality, Ability ability, List<Impact> impact,
			Position position, Contract contract) {
		super();
		ID = iD;
		this.name = name;
		this.workplace = workplace;
		this.birthday = birthday;
		this.sex = sex;
		this.idCardNumber = idCardNumber;
		this.phoneNumber = phoneNumber;
		this.nationality = nationality;
		
		this.ability = ability;
		this.impact = impact;
		this.position = position;
		this.contract = contract;
		//workplace.getMembers().add(this);
	}
	public Employee(String iD, String name, Institute workplace,LocalDate birthday, boolean sex, String idCardNumber,
			String phoneNumber, String nationality) {
		super();
		ID = iD;
		this.name = name;
		this.workplace = workplace;
		this.birthday = birthday;
		this.sex = sex;
		this.idCardNumber = idCardNumber;
		this.phoneNumber = phoneNumber;
		this.nationality = nationality;
		//workplace.getMembers().add(this);
	}
}
