package TAB2MXL;

/*
 * This class has been created for the unpitch in drums.
 * To match the mxl file on the wiki
 */

public class Unpitch {

	private int octave=0;
	private String step="";
	
	public Unpitch(String step, int octave) {
		this.octave = octave;
		this.step = step;
	}

	public int getOctave() {
		return octave;
	}

	public String getStep() {
		return step;
	}

	@Override
	public String toString() {
			String mxl = "\t\t<unpitched>"+"\n";
			mxl = mxl + "\t\t\t<display-step>"+step+"</display-step>"+"\n";
			mxl = mxl + "\t\t\t<display-octave>"+octave+"</display-octave>"+"\n";
			mxl = "\t\t\t</unpitched>"+"\n";
			return mxl;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		Unpitch unp = (Unpitch) obj;
		return unp.step.equals(step) && unp.octave == octave; 
	}

	

}
