package TAB2MXL;


public class Note {

	public Pitch pitch;
	//public NotationV2 notation; 
	public int duration; 
	public int type;
	public int charIndex;

	Note(Pitch pitch, int charIndex){
		this.pitch = pitch;
		this.charIndex = charIndex;
	}

	@Override
	public String toString() {
		String toMXL = "\t<note>\n" + this.pitch 
		+ "\t\t<duration> method not complete </duration>\n"
		+ "\t\t<type> method not complete </type>\n"
		+ "\t\t<staff>1</staff>\n"
		+ "\t</note>";

		return toMXL;
	}

}
