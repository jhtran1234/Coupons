package Stores;

public class GasStation extends Store {
	public GasStation(String n) {
		super(n);
	}

	@Override
	public String toString() {
		return "F " + returnString();
	}
}
