package TAB2MXL;


public class Note {
	
	public PitchV2 pitch;
	//public NotationV2 notation; 
	public int duration; 
	public int type;
	public int charIndex;
	
	Note(PitchV2 pitch, int charIndex){
		this.pitch = pitch;
		this.charIndex = charIndex;
	}
	

	public String toString() {
		String toMXL = "<note>\r\n" + this.pitch.toString() 
				+ "            <duration> method not complete </duration>\r\n"
				+ "            <type> method not complete </type>\r\n"
				+ "            <staff>1</staff>\r\n"
				+ "            </note>";
		
		return toMXL;
	}

}
