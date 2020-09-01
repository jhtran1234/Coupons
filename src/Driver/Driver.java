package Driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import Coupons.Coupon;
import Coupons.Date;
import Stores.*;

public class Driver {
	public static void main(String[] args) throws IOException {
		ArrayList<Store> stores = readInFile();
		handleRequests(stores);
		outputToFile(stores);
	}

	/*
	 * Handles reading in previous coupon data from file. Deletes expired
	 * coupons.
	 * 
	 */
	public static ArrayList<Store> readInFile() throws FileNotFoundException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String date = dtf.format(now).substring(0, 10);

		ArrayList<Store> stores = new ArrayList<>();
		Scanner scan = new Scanner(new BufferedReader(new FileReader("Stores.txt")));

		while(scan.hasNextLine()) {
			//Expecting format "X Name # 00"
			String in = scan.nextLine();
			if(in.length() == 0) {break;}
			int index = in.indexOf('#');
			String n = in.substring(2, index - 1);
			int numC = Integer.parseInt(in.substring(index + 2));
			Store s = null;

			switch(in.substring(0, 1)) {
			case("D"): s = new DepartmentStore(n);
			break;
			case("R"): s = new Restaurant(n);
			break;
			case("F"): s = new GasStation(n);
			break;
			case("G"): s = new GroceryStore(n);
			break;
			case("B"): s = new Branded(n);
			break;
			case("E"): s = new Entertainment(n);
			break;
			default: System.out.println("Error on store: " + n + ". No store type found.");
			s = new Store(n);
			break;
			}
			stores.add(s);

			for(int i = 0; i < numC; i ++) {
				in = scan.nextLine();
				index = in.indexOf('|');
				int index2 = in.indexOf('*');
				String d = in.substring(0, index - 1);
				n = in.substring(index + 2, index2 - 1);
				int exp = Integer.parseInt(in.substring(index2 + 2));

				Coupon c = new Coupon(n, d, new Date(exp));
				if(!c.isExpired(date)) {
					s.book.addCoupon(c);
				}
			}
		}

		scan.close();
		return stores;
	}

	public static void handleRequests(ArrayList<Store> stores) {
		Scanner scan = new Scanner(System.in);
		System.out.println("This system holds all of your coupons.");
		System.out.println("Press Q to quit at any time.");
		String in = "";
		while(in != "Q") {
			System.out.println("Please make a selection: ");
			System.out.println("0: Add New Store");
			System.out.println("1: Add New Coupon");
			System.out.println("2: See Coupons by Store");
			System.out.println("3: See Coupons by Store Type");
			System.out.println("4: Delete Store");
			System.out.println("5: Delete Coupon");
			System.out.println("Q: Exit");
			in = scan.nextLine();
			
			if(stores.size() == 0 && in.matches("^(1|2|3|4|5)$")) {
				System.out.println("Error, no stores yet!");
				continue;
			}
			
			if(in.equalsIgnoreCase("0")) {
				newStore(stores, scan);
			}
			else if(in.equalsIgnoreCase("1")) {
				newCoupon(stores, scan);
			}
			else if(in.equalsIgnoreCase("2")) {
				Store s = findStore(stores);
				if(s != null) {
					System.out.println(s.book.book);
				}
			}
			else if(in.equalsIgnoreCase("3")) {
				displayCouponsByType(stores, scan);
			}
			else if(in.equalsIgnoreCase("4")) {
				Store s = findStore(stores);
				if(s != null) {
					stores.remove(s);
				}
			}
			else if(in.equalsIgnoreCase("5")) {
				int index = -1;
				Store s = findStore(stores);

				if(s != null) {
					System.out.println("Please choose which coupon to remove: ");
					System.out.println("Enter any other key to exit.");
					for(int i = 0; i < s.book.book.size(); i ++) {
						System.out.println(i + ": " + s.book.book.get(i));
					}
					
					try {
						index = Integer.parseInt(scan.nextLine());
						s.book.book.remove(index);
					} catch (Exception e) {
						continue;
					}
				}
			}
			else if(in.equalsIgnoreCase("Q")) {
				break;
			}
			else {
				System.out.println("Invalid Selection");
			}
		}
	}

	/*
	 * Adds new store to the list of stores.
	 */
	public static void newStore(ArrayList<Store> stores, Scanner scan) {
		System.out.println("Enter Store name: ");
		String n = scan.nextLine();
		System.out.println("Enter Store type: ");
		System.out.println("B: Branded Store");
		System.out.println("D: Department Store");
		System.out.println("E: Entertainment");
		System.out.println("F: Gas Station");
		System.out.println("G: Grocery Store");
		System.out.println("R: Restaurant");
		System.out.println("X: Other");
		Store s = null;

		String type = String.valueOf(scan.nextLine().toUpperCase().charAt(0));
		
		switch(type) {
		case("D"): s = new DepartmentStore(n);
		break;
		case("R"): s = new Restaurant(n);
		break;
		case("F"): s = new GasStation(n);
		break;
		case("G"): s = new GroceryStore(n);
		break;
		case("B"): s = new Branded(n);
		break;
		case("E"): s = new Entertainment(n);
		break;
		default: s = new Store(n);
		break;
		}
		stores.add(s);
	}
	
	/*
	 * Adds new coupon to a store
	 */
	public static void newCoupon(ArrayList<Store> stores, Scanner scan) {
		Store s = findStore(stores);
		
		if(s != null) {
			System.out.println("Coupon name: ");
			String n = scan.nextLine();
			System.out.println("Coupon discount: ");
			String d = scan.nextLine();
			System.out.println("Coupon expiration (MMDDYYYY): ");
			int date = 0;

			while(date == 0) {
				try {
					date = Integer.parseInt(scan.nextLine());
				} catch (Exception e) {
					System.out.println("No valid input detected, please try again.");
				}
			}

			Coupon c = new Coupon(n, d, new Date(date));
			s.book.addCoupon(c);
		}
	}
	
	public static void displayCouponsByType(ArrayList<Store> stores, Scanner scan) {
		System.out.println("Store type: ");
		System.out.println("B: Branded Store");
		System.out.println("D: Department Store");
		System.out.println("E: Entertainment");
		System.out.println("F: Gas Station");
		System.out.println("G: Grocery Store");
		System.out.println("R: Restaurant");
		System.out.println("X: Other");
		char type = scan.nextLine().charAt(0);
		ArrayList<Store> temp = new ArrayList<>();
		if(type == 'B' || type == 'b') {
			for(Store s : stores) {
				if(s instanceof Branded) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else if(type == 'D' || type == 'd') {
			for(Store s : stores) {
				if(s instanceof DepartmentStore) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else if(type == 'E' || type == 'e') {
			for(Store s : stores) {
				if(s instanceof Entertainment) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else if(type == 'F' || type == 'f') {
			for(Store s : stores) {
				if(s instanceof GasStation) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else if(type == 'G' || type == 'g') {
			for(Store s : stores) {
				if(s instanceof GroceryStore) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else if(type == 'R' || type == 'r') {
			for(Store s : stores) {
				if(s instanceof Restaurant) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
		else {
			for(Store s : stores) {
				if(!(s instanceof Branded) && !(s instanceof DepartmentStore) && !(s instanceof Entertainment) && !(s instanceof GasStation) && !(s instanceof GroceryStore) && !(s instanceof Restaurant)) {
					temp.add(s);
					System.out.println(s.name);
					System.out.println(s.book.book);
				}
			}
		}
	}
	
	/*
	 * Searches for store and returns user selection
	 * @return user selected Store (null if none selected)
	 */
	public static Store findStore(ArrayList<Store> stores) {
		Scanner scan = new Scanner(System.in);
		int index = -1;
		ArrayList<Store> temp = null;

		while(index == -1) {
			System.out.println("Search store name: ");
			String n = scan.nextLine();
			temp = new ArrayList<>();
			for(Store s : stores) {
				if(s.name.toLowerCase().contains(n.toLowerCase())) {
					temp.add(s);
				}
			}
			
			System.out.println("Please select store: ");
			for(int i = 0; i < temp.size(); i ++) {
				System.out.println(i + ": " + temp.get(i).name);
			}
			System.out.println("To search again, enter -1. To cancel search, enter -2.");
			
			try {
				index = Integer.parseInt(scan.nextLine());
				
				if (index < -2 || index >= temp.size()) {
					index = -1;
					throw new IndexOutOfBoundsException();
				}
				else if(index == -2) {
					return null;
				}
			} catch (Exception e) {
				System.out.println("No valid input detected, please try again.");
			}
		}
		
		return temp.get(index);
	}

	/*
	 * Writes out coupons to file Stores.txt
	 */
	public static void outputToFile(ArrayList<Store> stores) throws IOException {
		stores.sort(new Comparator<Store>(){
			public int compare(Store arg0, Store arg1) {
				return String.CASE_INSENSITIVE_ORDER.compare(arg0.name, arg1.name);
			}
		});
		BufferedWriter out = new BufferedWriter(new FileWriter("Stores.txt"));

		for(Store s : stores) {
			out.write(s.toString()+ "\n");
			out.write(s.book.toString());
		}
		out.flush();
		out.close();
	}
}
