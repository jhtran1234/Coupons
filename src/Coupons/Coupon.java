package Coupons;

public class Coupon implements Comparable<Coupon>{
	public String storeType;
	public String name;
	public String discount;
	public Date exp;
	
	public Coupon(String n, String d, Date exp) {
		storeType = null;
		name = n;
		discount = d;
		this.exp = exp;
	}
	
	public Coupon(String t, String n, String d, Date exp) {
		storeType = t;
		name = n;
		discount = d;
		this.exp = exp;
	}
	
	@Override
	public int compareTo(Coupon o) {
		int space1 = o.discount.indexOf(' ');
		int space2 = discount.indexOf(' ');
		
		return Integer.parseInt(o.discount.substring(0, space1)) - Integer.parseInt(discount.substring(0, space2));
	}
	
	
	public boolean isExpired(Date today) {
		if(today.year > exp.year) {return true;}
		else if(today.year == exp.year) {
			if(today.month > exp.month) {return true;}
			else if(today.month == exp.month) {
				if(today.day > exp.day) {return true;}
				else {return false;}
			}
			else {return false;}
		}
		else {return false;}
	}
	
	/*public boolean isExpired(String today) {//Expected MMDDYYYY
		return isExpired(new Date(Integer.parseInt(today.substring(0, 2)), Integer.parseInt(today.substring(2, 4)), Integer.parseInt(today.substring(4, 8))));
	}*/
	
	public boolean isExpired(String today) {//Expected YYYY/MM/DD
		return isExpired(new Date(Integer.parseInt(today.substring(5, 7)), Integer.parseInt(today.substring(8, 10)), Integer.parseInt(today.substring(0, 4))));
	}
	
	public String toString() {
		return discount + " | " + name + " * " + exp.toString();
	}
}
