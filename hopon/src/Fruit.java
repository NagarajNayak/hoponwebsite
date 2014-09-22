import java.util.Comparator;


public class Fruit implements Comparable<Fruit>, Comparator<Fruit> {
	private String fruitName;
	private String fruitDesc;
	private int quantity;
 
	public Fruit(){}
	public Fruit(String fruitName, String fruitDesc, int quantity) {
		super();
		this.fruitName = fruitName;
		this.fruitDesc = fruitDesc;
		this.quantity = quantity;
	}
 
	public String getFruitName() {
		return fruitName;
	}
	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}
	public String getFruitDesc() {
		return fruitDesc;
	}
	public void setFruitDesc(String fruitDesc) {
		this.fruitDesc = fruitDesc;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(Fruit obj) {
		return this.getQuantity() - obj.getQuantity();
	}

	@Override
	public int compare(Fruit o1, Fruit o2) {
		return o1.getFruitDesc().compareTo(o2.getFruitDesc());
	}
	
	public static Comparator<Fruit> FruitNameComparator = new Comparator<Fruit>() {

		public int compare(Fruit fruit1, Fruit fruit2) {
			return fruit1.getFruitName().toUpperCase().compareTo(fruit2.getFruitName().toUpperCase());
		}

	};
}
