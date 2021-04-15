package TAB2MXL;

import java.util.ArrayList;
import java.util.List;

public class Attributes {
	private List<String> guitarTuning = new ArrayList<String>();
	private int key;
	
	public Attributes(List<String> guitarTuning) {
		this.guitarTuning = guitarTuning;
		key = 0;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		if (TabReader.instrument.equals("Drumset")) {
			return "\t<attributes>\n"
					+ "\t\t<divisions>8</divisions>\n"
					+ "\t\t<key>\n"
					+ "\t\t\t<fifths>" + key + "</fifths>\n"
					+ "\t\t</key>\n"
					+ "\t\t<time>\n"
					+ "\t\t\t<beats>4</beats>\n"
					+ "\t\t\t<beat-type>4</beat-type>\n"
					+ "\t\t</time>\n"
					+ "\t\t<clef>\n"
					+ "\t\t\t<sign>percussion</sign>\n"
					+ "\t\t\t<line>2</line>\n"
					+ "\t\t</clef>\n"
					+ "\t</attributes>";
		}

		String mxl = "\t<attributes>"
				+ "\n\t\t<divisions>8</divisions>"
				+ "\n\t\t<key>"
				+ "\n\t\t\t<fifths>" + key + "</fifths>"
				+ "\n\t\t</key>"
				+ "\n\t\t<time>"
				+ "\n\t\t\t<beats>4</beats>"
				+ "\n\t\t\t<beat-type>4</beat-type>"
				+ "\n\t\t</time>"
				+ "\n\t\t<clef>"
				+ "\n\t\t\t<sign>TAB</sign>"
				+ "\n\t\t\t<line>5</line>"
				+ "\n\t\t</clef>"
				+ "\n\t\t<staff-details>";

		mxl += "\n\t\t\t<staff-lines>" + guitarTuning.size() + "</staff-lines>";
		for (int i = 1; i <= guitarTuning.size(); i++) {
			int index = (guitarTuning.size() - i);
			mxl += "\n\t\t\t<staff-tuning line=\"" + i + "\">"
					+ "\n\t\t\t\t<tuning-step>" 
					+ new Pitch(index + 1, guitarTuning.get(index), 0).getStep() 
					+ "</tuning-step>"
					+ "\n\t\t\t\t<tuning-octave>" 
					+ new Pitch(index + 1, guitarTuning.get(index), 0).getOctave() 
					+ "</tuning-octave>"
					+ "\n\t\t\t</staff-tuning>";
		}
		mxl += "\n\t\t</staff-details>"
				+ "\n\t</attributes>";

		return mxl;
	}
}

