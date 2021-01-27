package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainV2 {

	public static void main(String[] args) {
//		MainV2 test = new MainV2("src/TabLocal/StairwayHeaven.txt");
//		test.setTuning(test.tabArray);
//		test.makeMeasures(test.tabArray);
//		test.makeNotes();
		
		MainV2 test2 = new MainV2("src/main/java/TAB2MXL/StairwayHeaven.txt");
		test2.setTuning(test2.tabArray);
		test2.makeMeasures(test2.tabArray);
		test2.makeNotes();
//		System.out.println(allMeasures.size());
//		for (int i = 0; i < allMeasures.size(); i++) {
//			for(int j = 0; j < 6; j++) {
//				System.out.println(allMeasures.get(i).get(j));
//			}
//		}
		
	}

	public List<String> tabArray = new ArrayList<String>();
	public File inputTabFile;
	public String outputXMLFile;
	static List<ArrayList<String>> allMeasures = new ArrayList<ArrayList<String>>();
	//static ArrayList<MeasureSolo> allMeasuresNotes = new ArrayList<MeasureSolo>();
	static List<Character> guitarTuning = new ArrayList<Character>();

	MainV2(String inputFile) {
		inputTabFile = new File(inputFile);
		outputXMLFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\r\n"
				+ "<score-partwise version=\"3.1\">\r\n" + "  <work>\r\n" + "    <work-title>Good Copy</work-title>\r\n"
				+ "    </work>\r\n" + "  <part-list>\r\n" + "    <score-part id=\"P1\">\r\n"
				+ "      <part-name>Guitar</part-name>\r\n" + "      </score-part>\r\n" + "    </part-list>\r\n"
				+ "  <part id=\"P1\">";
		tabArray = readFile(inputTabFile);

	}

	public List<String> readFile(File inputFile) {
		List<String> tabArray = new ArrayList<String>();
		Scanner sc = null;

		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				tabArray.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}

		return tabArray;
	}

	public void setTuning(List<String> tabArray) {

		for (int i = 0; i < 6; i++) {
			char tuneVal = this.tabArray.get(i).charAt(0);
			guitarTuning.add(tuneVal);
			//System.out.println(tuneVal);
		}

		for (int i = 0; i < this.tabArray.size(); i++) {

			String trimmedLine = this.tabArray.get(i).substring(1, this.tabArray.get(i).length());
			this.tabArray.set(i, trimmedLine);

		}
	}

	public void makeMeasures(List<String> tabArray) {
		ArrayList<String> Measure = new ArrayList<String>();
		int startingIndex = 6;

		for (int j = 0; j < 6; j++) {
			String currentElement = this.tabArray.get(j);

			int index1 = currentElement.indexOf('|');
			int index2 = currentElement.indexOf('|', index1 + 1);
			String lineSegment = currentElement.substring(index1 + 1, index2);
			Measure.add(lineSegment);
//			System.out.println(lineSegment);
			String replaceLine = currentElement.substring(index2);
			this.tabArray.set(j, replaceLine);
		}
		allMeasures.add(Measure);
		if (this.tabArray.get(0).indexOf('|', 1) != -1) {
			makeMeasures(this.tabArray);
		}

		if (this.tabArray.size() > startingIndex)
			makeMeasures(this.tabArray, startingIndex);

	}

	public void makeMeasures(List<String> tabArray, int startingIndex) {
		ArrayList<String> Measure = new ArrayList<String>();

		for (int j = startingIndex; j < startingIndex + 6; j++) {
			String currentElement = this.tabArray.get(j);

			int index1 = currentElement.indexOf('|');
			int index2 = currentElement.indexOf('|', index1 + 1);
			String lineSegment = currentElement.substring(index1 + 1, index2);
			Measure.add(lineSegment);
//			System.out.println(lineSegment);
			String replaceLine = currentElement.substring(index2);
			this.tabArray.set(j, replaceLine);
		}
		allMeasures.add(Measure);
		if (this.tabArray.get(0).indexOf('|', 1) != -1) {
			makeMeasures(this.tabArray);
		}

		if (this.tabArray.size() > startingIndex + 6)
			makeMeasures(this.tabArray, startingIndex + 6);
	}
	
	public void makeNotes() {
		GuitarString[] strings = new GuitarString[6];
		for (int i = 0; i < allMeasures.size(); i++) {
			for(int j = 0; j < 6; j++) {
				strings[j] = new GuitarString(j+1, Character.toString(guitarTuning.get(j)).toUpperCase());
				String currentLine = allMeasures.get(i).get(j);
					for(int k = 0; k < currentLine.length(); k++) {
						if (currentLine.charAt(k) != '-') {
							String temp = currentLine.substring(k, k+1);
							int fret = Integer.valueOf(temp);
//							System.out.println(fret);
//							String guitarTune = guitarTuning.get(j).toString();
//							System.out.println(guitarTune);
							System.out.println("Measure Number: " + (i + 1));
							PitchV2 pitch = new PitchV2(guitarTuning.get(j).toString(), fret);
							Note note = new Note(pitch, k);
//							System.out.println(pitch.getStep());
//							System.out.println(note.getStep());
							System.out.println(note.toString());
						}
							//allMeasuresNotes.get(i).notesArray.add(note);
							
							
					}
				
			}
		}
	}
}

