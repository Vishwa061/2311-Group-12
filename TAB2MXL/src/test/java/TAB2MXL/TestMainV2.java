package TAB2MXL;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class TestMainV2 {
	
	private final String PATH = "src/test/resources/";
	
	@Test
	public void test1() {
		MainV2 test2 = new MainV2(PATH + "countBar.txt");
		//test2.countBars();
	System.out.println(test2.countBars());
		
		
		
	}
	

}
