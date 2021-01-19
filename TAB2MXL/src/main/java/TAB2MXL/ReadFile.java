package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		System.out.println("Please insert a text file: ");
		File file = new File ("Read File");
		try {
			Scanner sc = new Scanner(file);
			ArrayList<String> Slist = new ArrayList<String>();
			while (sc.hasNextLine()) {
				Slist.add(sc.next());
				System.out.println(Slist);
		}
		sc.close();
	}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		

	

}
}
