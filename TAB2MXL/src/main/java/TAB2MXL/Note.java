package TAB2MXL;


public class Note {
	
	public PitchV2 pitch;
	//public NotationV2 notation; 
	public int duration; 
	public int type;
	
	Note(PitchV2 pitch){
		this.pitch = pitch;
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
