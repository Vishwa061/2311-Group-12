package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
		System.out.println("Savvy added.");
		System.out.println("Sara added to Java 1.8.");
		System.out.println("Push test");

		System.out.println("Nish made a change");
		System.out.println("Savvy changed Java version to 1.8");
		System.out.println("Sara made a change.");
		
		System.out.println("kaneez made a change.");
	}

	public List<String> readFile(String fileLocation) {
		List<String> tabArray = new ArrayList<String>();
		Scanner sc = null;

		try {
			sc = new Scanner(new File(fileLocation));
			while (sc.hasNextLine()) {
				tabArray.add(sc.nextLine());
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}

		return tabArray;
	}

}
