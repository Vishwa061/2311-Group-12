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
		String toMXL = "<note>\n" + this.pitch 
		+ "            <duration> method not complete </duration>\r\n"
		+ "            <type> method not complete </type>\r\n"
		+ "            <staff>1</staff>\r\n"
		+ "            </note>";

		return toMXL;
	}

}
