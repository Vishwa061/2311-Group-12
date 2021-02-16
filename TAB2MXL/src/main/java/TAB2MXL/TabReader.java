package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TabReader {
	private List<Measure> measureElements = new ArrayList<Measure>();
	private List<String> tabArray = new ArrayList<String>();
	private String outputXMLFile;
	private List<ArrayList<String>> allMeasures = new ArrayList<ArrayList<String>>();
	private List<String> guitarTuning = new ArrayList<String>();
	private String instrument;

	public static void main(String[] args) {
		TabReader reader = new TabReader(new File("src/main/resources/StairwayHeaven.txt"));
		System.out.println(reader.toMXL());
//		for (int i = 0; i < reader.allMeasures.size(); i++) {
//			for(int j = 0; j < reader.allMeasures.get(i).size(); j++) {
//				System.out.println(reader.allMeasures.get(i).get(j));
//			}
//		}
		
	}

	public TabReader(File inputFile) {
		outputXMLFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "<work>\n" + "    <work-title>Prototype Version 1.0</work-title>\n"
				+ "    </work>\n" + "  <part-list>\n" + "    <score-part id=\"P1\">\n"
				+ "      <part-name>Classical Guitar</part-name>\n" + "      </score-part>\n" + "    </part-list>\n"
				+ "  <part id=\"P1\">";
		tabArray = readFile(inputFile);
		instrument = "Classical Guitar";

		guitarTuning = getTuning();
		Measure.setAttributes(new Attributes(guitarTuning));
		allMeasures = splitMeasure();
		measureElements = makeNotes();
	}

	public List<Measure> getMeasures() {
		return this.measureElements;
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

	public List<String> getTuning() {
		List<String> guitarTuning = new ArrayList<String>();
		int numStrings = 6;
		int i = 0;
		while (i < tabArray.size() && guitarTuning.size() < numStrings) {
			String line = tabArray.get(i);
			if (line.contains("-") && line.indexOf('|') > 0) {
				guitarTuning.add(line.substring(0, line.indexOf('|')));
				
			}
			i++;
		}

		return guitarTuning;
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

	public List<Measure> makeNotes() {
		List<Measure> measureElements = new ArrayList<Measure>();
		
		for (int i = 0; i < allMeasures.size(); i++) {
			Measure measure = new Measure(i + 1);
			int noteCounter = 0;
			for (int j = 0; j < 6; j++) {
				String currentLine = allMeasures.get(i).get(j);
				measure.setIndexTotal(currentLine.length());
				String temp;
				for (int k = 0; k < currentLine.length(); k++) {

					if (currentLine.charAt(k) != '-') {

						if (currentLine.charAt(k) == 'p' || currentLine.charAt(k) == 'h' || currentLine.charAt(k) == 's'
								|| currentLine.charAt(k) == '/') {

							if (currentLine.charAt(k) == 'p')
								measure.getNote(noteCounter - 1).slurStart = true;

							if (currentLine.charAt(k) == 'h')
								measure.getNote(noteCounter - 1).tieStart = true;

							if (currentLine.charAt(k) == 's' || currentLine.charAt(k) == '/')
								measure.getNote(noteCounter - 1).slideStart = true;

							continue;

						}

						if (currentLine.charAt(k + 1) != '-') {
							if (currentLine.charAt(k + 1) == '0' || currentLine.charAt(k + 1) == '1'
									|| currentLine.charAt(k) == '2' || currentLine.charAt(k + 1) == '3'
									|| currentLine.charAt(k + 1) == '4' || currentLine.charAt(k + 1) == '5'
									|| currentLine.charAt(k + 1) == '6' || currentLine.charAt(k + 1) == '7'
									|| currentLine.charAt(k + 1) == '8' || currentLine.charAt(k + 1) == '9') {
								temp = currentLine.substring(k, k + 2);
								int fret = Integer.valueOf(temp);
								Note note = new Note(j + 1, guitarTuning.get(j), fret, k, (6-j));
								measure.addNote(note);
								noteCounter++;
								k++;

								if (measure.size() > 1) {
									if (measure.getNote(noteCounter - 2).slurStart)
										note.slurStop = true;

									if (measure.getNote(noteCounter - 2).tieStart)
										note.tieStop = true;

									if (measure.getNote(noteCounter - 2).slideStart)
										note.slideStop = true;
								}

								continue;
							}
						}

						if (currentLine.charAt(k + 1) == '-' || currentLine.charAt(k + 1) == 'p'
								|| currentLine.charAt(k + 1) == 'h' || currentLine.charAt(k + 1) == 's'
								|| currentLine.charAt(k + 1) == '/') {
							temp = currentLine.substring(k, k + 1);
							int fret = Integer.valueOf(temp);
							Note note = new Note(j + 1, guitarTuning.get(j), fret, k, j);
							measure.addNote(note);
							noteCounter++;
							if (measure.size() > 1) {
								if (measure.getNote(noteCounter - 2).slurStart)
									note.slurStop = true;

								if (measure.getNote(noteCounter - 1).tieStart)
									note.tieStop = true;

								if (measure.getNote(noteCounter - 1).slideStart)
									note.slideStop = true;
							}

						}
					}

				}
			}
			noteCounter = 0;
			measure.sortArray();
			measure.durationVal = setDuration(measure);
			noteDuration(measure);
			measureElements.add(measure);
			

		}
		return measureElements;
	}

	
	public ArrayList<Integer> countBars() {
 		ArrayList<Integer> countArray = new ArrayList<>();
 		for (int i = 0; i < tabArray.size();i++) {
 			if (tabArray.get(i).contains("-") ) {
 				ArrayList<Integer> indices = new ArrayList<Integer>();
 				int index = 0;
 				while((index = tabArray.get(i).indexOf('|', index+1))>0) {
 				 indices.add(index);	
 				}
 				for (int j = 0; j < indices.size()-1;j++) {
 					String lineBefore = "";
 					lineBefore = tabArray.get(i-1);
 					int lastIndex = indices.get(j+1) > lineBefore.lastIndexOf('|') ? lineBefore.lastIndexOf('|'): indices.get(j+1);
 					lineBefore = lineBefore.substring(indices.get(j),lastIndex+1);
 					int count = 0;
 					for(String s: lineBefore.split("\\s+")) {
 						if (s.equals("|")) {
 							count++;
 						}

 					}
 					countArray.add(count);
 				}

 				break;
 			}
 		}
 		return countArray;
 	}
	
	public List<ArrayList<String>> splitMeasure(){
		List<ArrayList<String>> split = new ArrayList<ArrayList<String>>();
		HashMap<Integer, String> measure = new HashMap<Integer, String>();
		String line = "";
		int k=0;
		int i =0;
		while (k < tabArray.size() &&  i < 6 ) {
			if (tabArray.get(k).contains("-")) {
				line = tabArray.get(k);
				String[] lineArray = line.split("\\|");
				for(int j=1; j<lineArray.length;j++) {
					if (measure.containsKey(j)) {
						measure.put(j,measure.get(j)+ lineArray[j]+"\n");
					}
					else {
						measure.put(j,lineArray[j]+"\n");
					}
				}
				i++;
				
				}
			k++;
			}
			
		for(int j=1; j <= measure.size();j++) {
			String string = measure.get(j);
			ArrayList<String> splitMeasure= new ArrayList<String>();
			for(String s : string.split("\n")) {
				splitMeasure.add(s);
			}
			split.add(splitMeasure);
		}
		
		return split;
	}
	
	public int setDuration(Measure measure) {
		int indexTotal = measure.getIndexTotal();
		int firstIndex = measure.getNotes().get(0).charIndex;
		int totalChar = indexTotal - firstIndex;
		int eachBeatVal = totalChar/4;
		int eachCharVal = 120/eachBeatVal;
		
		return eachCharVal;
	}
	
	public void noteDuration(Measure measure) {
		List<Note> noteArr = measure.getNotes();
		for (int i = 0; i < noteArr.size(); i++) {
			if(i == (noteArr.size()-1)) {
				noteArr.get(i).duration = (measure.getIndexTotal() - noteArr.get(i).charIndex) * measure.durationVal;
			}
			else {
				noteArr.get(i).duration = (noteArr.get(i+1).charIndex - noteArr.get(i).charIndex) * measure.durationVal;
			}
		}
		
	}
	
	public String toMXL() {
		StringBuilder builder = new StringBuilder();
		builder.append(outputXMLFile).append("\n");
		
		for (Measure m : getMeasures()) {
			builder.append(m.toString()).append("\n");
		}
		
		builder.append("</part>\n</score-partwise>");
		return builder.toString();
	}
}