package Stores;

public class DepartmentStore extends Store{
	public DepartmentStore(String n) {
		super(n);
	}

	@Override
	public String toString() {
		return "D " + returnString();
	}
}
