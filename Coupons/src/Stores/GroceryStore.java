package Stores;

public class GroceryStore extends Store {
	public GroceryStore(String n) {
		super(n);
	}

	@Override
	public String toString() {
		return "G " + returnString();
	}
}
