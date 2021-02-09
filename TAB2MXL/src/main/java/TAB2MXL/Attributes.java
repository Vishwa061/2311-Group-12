package TAB2MXL;

import java.util.ArrayList;
import java.util.List;

public class Attributes {
	private List<String> guitarTuning = new ArrayList<String>();

	public Attributes(List<String> guitarTuning) {
		this.guitarTuning = guitarTuning;
	}

	@Override
	public String toString() {
		String mxl = "\t<attributes>"
				+ "\n\t\t<divisions>1</divisions>"
				+ "\n\t\t<key>"
				+ "\n\t\t\t<fifths>0</fifths>"
				+ "\n\t\t</key>"
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
					+ new Note(index + 1, guitarTuning.get(index), 0, 0).getPitch().getStep() 
					+ "</tuning-step>"
					+ "\n\t\t\t\t<tuning-octave>" 
					+ new Note(index + 1, guitarTuning.get(index), 0, 0).getPitch().getOctave() 
					+ "</tuning-octave>"
					+ "\n\t\t\t</staff-tuning>";
		}
		mxl += "\n\t\t</staff-details>"
				+ "\n\t</attributes>";

		return mxl;
	}
}
