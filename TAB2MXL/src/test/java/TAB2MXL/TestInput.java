package TAB2MXL;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestInput {
	@Test
	public void testReadFile() {
		TabReader testing = new TabReader("test_tabs_reading.txt");
		
		List<String> expected = new ArrayList<String>();
		expected.add("e |-------------0-0-0-0-0-0-----0-------0-0-0-0-0---|");
		expected.add("B |-------------1-1-1-1-1-1-1h3p1p0h1-----1-1-1-1-1-|");
		expected.add("G |-----0h2-----2-2-2-2-2-2-----2-------2-2-2-2-2---|");
		expected.add("D |-0h2-------2-2-2-2-2-2-2-----2-----2-2-2-2-2-2---|");
		expected.add("A |---------0---0-0-0-0-0-----------0---0-0-0-0-0---|");
		expected.add("E |-------------------------------------------------|");
		
		List<String> actual = testing.getTabArray();
		
		assertEquals(expected, actual);
	}

}
