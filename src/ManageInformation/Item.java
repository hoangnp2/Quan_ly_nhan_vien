package ManageInformation;

public class Item {
	private String content;
	
	private String ID;
	
	private int type;


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public Item(String content, String ID, int type) {
		super();
		this.ID = ID;
		this.content = content;
		this.type = type;
	}
	
	
    public String toString()  {
        return this.content;
    }
	
}
