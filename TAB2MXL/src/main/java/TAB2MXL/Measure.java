package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	private ArrayList<Note> notes;
	private int measureNumber;
	public static Attributes a;
	
	public Measure(int measureNumber) {
		notes = new ArrayList<Note>();
		this.measureNumber = measureNumber;
	}
	
	public static void setAttributes(Attributes a) {
		Measure.a = a;
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	@Override
	public String toString() {
		String mxl = "<measure number=\"" + measureNumber + "\">\n";
		
//		if (measureNumber == 1) {
//			mxl += a;
//		}
		
		for (Note note : notes) {
			mxl += note + "\n";
		}
		
		mxl += "</measure>";
		
		return mxl;
	}
}