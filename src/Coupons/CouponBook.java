package Coupons;

import java.util.ArrayList;
import java.util.Comparator;

public class CouponBook {
	public ArrayList<Coupon> book;

	public CouponBook() {
		book = new ArrayList<>();
	}

	public void addCoupon(Coupon c) {
		book.add(c);
	}
	
	public int getSize() {
		return book.size();
	}
	
	public void sort() {
		book.sort(new Comparator<Coupon>() {
			public int compare(Coupon t, Coupon o) {
				int space1 = o.discount.indexOf(' ');
				int space2 = t.discount.indexOf(' ');
				
				return Integer.parseInt(o.discount.substring(0, space1)) - Integer.parseInt(t.discount.substring(0, space2));
			}
		});
	}
	
	public String toString() {
		String out = "";
		for(Coupon c : book) {
			out += c.toString() + "\n";
		}
		
		return out;
	}
}
