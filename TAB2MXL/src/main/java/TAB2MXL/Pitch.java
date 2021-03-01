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
		String mxl = "\t\t<pitch>\n";
		
		mxl += "\t\t\t<step>" + step +"</step>\n";
		mxl += "\t\t\t<alter>" + alter +"</alter>\n";
		mxl += "\t\t\t<octave>" + octave +"</octave>\n";
		
		mxl += "\t\t</pitch>\n";
		return mxl;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pitch p = (Pitch) obj;
		return p.alter == alter && p.step.equals(step) && p.octave == octave; 
	}
}
