package TAB2MXL;

import java.util.*;
import java.io.File;

public class TabReader {
	
	public ArrayList<Object> tabArray = new ArrayList<Object>();
	public File inputTabFile;
	public String outputXMLFile;
	
	public void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputTabFile);
			while(sc.hasNextLine()) {
				tabArray.add(sc.nextLine());
			}
		}
		catch (FileNotFoundExpception e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}

}
