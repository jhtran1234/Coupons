package Coupons;

public class Date {
	int month;
	int day;
	int year;
	
	public Date(int m, int d, int y) {
		month = m;
		day = d;
		year = y;
	}
	
	public Date(int t) {//Expected MMDDYYYY
		month =  t / 1000000;
		day = (t - month*1000000) / 10000;
		year = (t - month*1000000 - day*10000);
	}
	
	public String toString() {//Written MMDDYYYY
		String out = "";
		if(month < 10) {
			out += "0";
		}
		out += month;
		if(day < 10) {
			out += "0";
		}
		out += day;
		out += year;
		return out;
	}
}
