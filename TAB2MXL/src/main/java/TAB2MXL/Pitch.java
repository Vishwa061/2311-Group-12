package TAB2MXL;

public class Pitch {
	private String step;
	private int alter;
	private int octave;
	
	public Pitch(String step, int alter, int octave) {
		this.step = step;
		this.alter = alter;
		this.octave = octave;
	}

	public String getStep() {
		return step;
	}

	public int getAlter() {
		return alter;
	}

	public int getOctave() {
		return octave;
	}
}
