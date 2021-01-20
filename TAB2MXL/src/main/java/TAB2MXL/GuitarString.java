package TAB2MXL;

public class GuitarString {
	private static final String[] ALL_NOTES = {"E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#"};
	private int tuningValue;
	private int stringNumber;

	public GuitarString(int stringNumber, String stringTuning) {
		this.stringNumber = stringNumber;
		for (int i = 0; i < ALL_NOTES.length; i++) {
			if (stringTuning == ALL_NOTES[i]) {
				tuningValue = i;
				break;
			}
		}
	}

	public String getStep(int fret) {
		int noteIndex = (tuningValue + fret) % ALL_NOTES.length;
		String note = ALL_NOTES[noteIndex];
		return Character.toString(note.charAt(0));
	}
	
	public int getAlter(int fret) {
		int noteIndex = (tuningValue + fret) % ALL_NOTES.length;
		String note = ALL_NOTES[noteIndex];
		return note.length() - 1;
	}
	
	public int getOctave(int fret) {
		// need to finish this
		return 0;
	}

}