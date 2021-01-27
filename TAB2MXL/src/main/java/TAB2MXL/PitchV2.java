package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PitchV2 {

	public String step;
	public int alter;
	public int octave;
	public int indexWithinMeasure;

	public PitchV2(String tuningVal, int fretVal) {
		this.step = calcStep(tuningVal, fretVal);
		this.octave = calcOctave(tuningVal, fretVal);
		
	}

	public String calcStep(String tuningVal, int fretVal) {
		String stepVal = "";
		
		String[] eArray = { "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B", "C", "C#", "D", "D#", "E" };
		String[] BArray = { "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#",
				"E", "F", "F#", "G", "G#", "A", "A#", "B" };
		String[] GArray = { "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
				"C", "C#", "D", "D#", "E", "F", "F#", "G" };
		String[] DArray = { "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#",
				"G", "G#", "A", "A#", "B", "C", "C#", "D" };
		String[] AArray = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#",
				"D", "D#", "E", "F", "F#", "G", "G#", "A" };
		String[] EArray = { "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B", "C", "C#", "D", "D#", "E" };
		
		if (tuningVal.equals("e"))
			stepVal = eArray[fretVal];
		if (tuningVal.equals("B"))
			stepVal = BArray[fretVal];
		if (tuningVal.equals("G"))
			stepVal = GArray[fretVal];
		if (tuningVal.equals("D"))
			stepVal = DArray[fretVal];
		if (tuningVal.equals("A"))
			stepVal = AArray[fretVal];
		if (tuningVal.equals("E"))
			stepVal = EArray[fretVal];
		
		if (stepVal.indexOf('#') != -1) {
			this.setAlter(1);
			stepVal = stepVal.replaceAll("#","");
		}
		return stepVal;

	}

	public int calcOctave(String tuningVal, int fretVal) {
		int octaveVal = 0;
		
		if (tuningVal.equals("e")) {
			if (fretVal >= 0 && fretVal <= 7)
				octaveVal = 4;
			if (fretVal >= 8 && fretVal <= 19)
				octaveVal = 5;
			if (fretVal >= 20 && fretVal <= 24)
				octaveVal = 6;
		}

		if (tuningVal.equals("B")) {
			if (fretVal == 0)
				octaveVal = 3;
			if (fretVal >= 1 && fretVal <= 12)
				octaveVal = 4;
			if (fretVal >= 13 && fretVal <= 24)
				octaveVal = 5;
		}
		if (tuningVal.equals("G")) {
			if (fretVal >= 0 && fretVal <= 4)
				octaveVal = 3;
			if (fretVal >= 5 && fretVal <= 16)
				octaveVal = 4;
			if (fretVal >= 17 && fretVal <= 24)
				octaveVal = 5;
		}
		if (tuningVal.equals("D")) {
			if (fretVal >= 0 && fretVal <= 9)
				octaveVal = 3;
			if (fretVal >= 10 && fretVal <= 21)
				octaveVal = 4;
			if (fretVal >= 22 && fretVal <= 24)
				octaveVal = 5;
		}
		if (tuningVal.equals("A")) {
			if (fretVal >= 0 && fretVal <= 2)
				octaveVal = 2;
			if (fretVal >= 3 && fretVal <= 14)
				octaveVal = 3;
			if (fretVal >= 15 && fretVal <= 24)
				octaveVal = 4;
		}
		if (tuningVal.equals("E")) {
			if (fretVal >= 0 && fretVal <= 7)
				octaveVal = 2;
			if (fretVal >= 8 && fretVal <= 19)
				octaveVal = 3;
			if (fretVal >= 20 && fretVal <= 24)
				octaveVal = 4;
		}
		
		return octaveVal;
	}
	
	public String getStep() {
		return this.step;
		
	}
	
	public int getOctave() {
		return this.octave;
	}
	
	public int getAlter() {
		return this.alter;
	}
	
	public void setAlter(int altVal) {
		this.alter = altVal;
	}
	
	public String toString() {
		String toMXL = "           <pitch>\r\n"
				+ "                <step>" + this.getStep() + "</step>\r\n"
				+ "                <alter>" + this.getAlter() + "</alter>\r\n"
				+ "                <octave>" + this.getOctave() + "</octave>\r\n"
				+ "                </pitch>\r\n";
		
		return toMXL;
	}
}
