package Stores;

import Coupons.CouponBook;

public class Store {
	public String name;
	public CouponBook book;
		
	public Store(String n) {
		name = n;
		book = new CouponBook();
	}
	
	public String returnString() {
		return this.name + " # " + this.book.getSize();
	}
	
	public String toString() {
		return "X " + this.name + " # " + this.book.getSize();
	}
}