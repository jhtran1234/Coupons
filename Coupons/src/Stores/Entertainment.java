package Stores;

public class Entertainment extends Store {
	public Entertainment(String n) {
		super(n);
	}

	@Override
	public String toString() {
		return "E " + returnString();
	}
}
