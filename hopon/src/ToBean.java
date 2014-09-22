import java.io.Serializable;

public class ToBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String bProp;
	private String cProp;

	public ToBean() {
	}

	public ToBean(String name, String bProp, String cProp) {
		this.name = name;
		this.bProp = bProp;
		this.cProp = cProp;
	}

	public String getBProp() {
		return bProp;
	}

	public void setBProp(String prop) {
		bProp = prop;
	}

	public String getCProp() {
		return cProp;
	}

	public void setCProp(String prop) {
		cProp = prop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}