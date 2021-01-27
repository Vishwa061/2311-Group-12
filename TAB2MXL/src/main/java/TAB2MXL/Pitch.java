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
	
	@Override
	public String toString() {
		String mxl = "<pitch>\n";
		
		mxl += "\t<step>" + step +"</step>\n";
		mxl += "\t<alter>" + alter +"</alter>\n";
		mxl += "\t<octave>" + octave +"</octave>\n";
		
		mxl += "</pitch>\n";
		return mxl;
	}
}
