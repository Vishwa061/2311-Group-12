package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) {


		System.out.println("Hello World Nishiket"); 

		System.out.println("Hello World, kkaneez"); 
		System.out.println("Hello World, kaneez");
		System.out.println("Hello World, savvy"); 
		System.out.println("Hello World, kaneez did it again"); 


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
