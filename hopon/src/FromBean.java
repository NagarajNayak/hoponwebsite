import java.io.Serializable;

public class FromBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String aProp;
	private String bProp;

	public FromBean() {
	}

	public FromBean(String name, String aProp, String bProp) {
		this.name = name;
		this.aProp = aProp;
		this.bProp = bProp;
	}

	public String getAProp() {
		return aProp;
	}

	public void setAProp(String prop) {
		aProp = prop;
	}

	public String getBProp() {
		return bProp;
	}

	public void setBProp(String prop) {
		bProp = prop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}