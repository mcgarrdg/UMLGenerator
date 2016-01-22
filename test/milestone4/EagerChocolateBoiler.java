package milestone4;

public class EagerChocolateBoiler {
	private boolean empty;
	private boolean boiled;

	private static EagerChocolateBoiler uniqueInstance = new EagerChocolateBoiler();

	private EagerChocolateBoiler() {
		empty = true;
		boiled = false;
	}
	public static EagerChocolateBoiler getInstance() {
		return uniqueInstance;
	}
	
	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
		}
	}
	
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			boiled = true;
		}
	}
	
	public void boil() {
		if (!isEmpty() && !isBoiled()) {
			boiled = true;
		}
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public boolean isBoiled() {
		return boiled;
	}
}
