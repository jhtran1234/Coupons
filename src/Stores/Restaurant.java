package Stores;

public class Restaurant extends Store {
	public Restaurant(String n) {
		super(n);
	}
	
	@Override
	public String toString() {
		return "R " + returnString();
	}
}
