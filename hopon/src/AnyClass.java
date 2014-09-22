public class AnyClass {
	private static int objectCount = 0;
	private AnyClass() {
		System.out.println("Constructor");
	}
	public static AnyClass getInstance() {
		
		if(objectCount < 4) {
			AnyClass obj = new AnyClass();
			objectCount++;
			return obj;
		}
		return null;
	}
	public void test() {
		System.out.println("test");
	}
	
}