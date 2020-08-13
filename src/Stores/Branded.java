package Stores;

public class Branded extends Store {
	public Branded(String n) {
		super(n);
	}

	@Override
	public String toString() {
		return "B " + returnString();
	}
}
