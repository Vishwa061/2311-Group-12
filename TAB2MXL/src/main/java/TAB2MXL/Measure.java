package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	private ArrayList<Note> notes;
	private int measureNumber;
	
	public Measure(int measureNumber) {
		notes = new ArrayList<Note>();
		this.measureNumber = measureNumber;
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	@Override
	public String toString() {
		String mxl = "<measure number=\"" + measureNumber + "\">\n";
		
		for (Note note : notes) {
			mxl += note + "\n";
		}
		
		mxl += "</measure>";
		
		return mxl;
	}
	
	public String toFirstMeasureMXL(Attributes a) {
		String mxl = "<measure number=\"" + measureNumber + "\">\n";
		
		mxl += a.toMXL() + "\n";
		
		for (Note note : notes) {
			mxl += note + "\n";
		}
		
		mxl += "</measure>";
		return mxl;
	}
}