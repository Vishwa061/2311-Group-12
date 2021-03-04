package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TabReader {
	private List<String> tabArray;
	private List<String> guitarTuning;
	private List<Measure> measureElements;
	private List<ArrayList<String>> allMeasures;
	public static String instrument;
	private String title;
	private File file;

	public static void main(String[] args) {

		TabReader reader = new TabReader();
		reader.setInput(new File("src/main/resources/StairwayHeaven.txt"));
		reader.convertTabs();
		System.out.println(reader.toMXL());
	}

	public TabReader() {
		tabArray = new ArrayList<String>();
		guitarTuning = new ArrayList<String>();
		measureElements = new ArrayList<Measure>();
		allMeasures = new ArrayList<ArrayList<String>>();
	}

	public void setInput(String fileAsString) {
		tabArray = Arrays.asList(fileAsString.split("\\n"));
	}

	public String setInput(File inputFile) {
		tabArray = readFile(inputFile);
		file = inputFile;

		StringBuilder builder = new StringBuilder();
		for (String s : tabArray) {
			builder.append(s);
		}

		return builder.toString();
	}

	/**
	 * Converts the text-tab to MXL data and populates fields
	 * 
	 * @return a TabError which contains a string representing the exit code </br>
	 *         Exit codes: </br>
	 *         done - the tabs were successfully converted </br>
	 *         empty - no tabs found </br>
	 *         instrument - the instrument was not found </br>
	 *         tuning - the tuning was not found </br>
	 *         measure - the measure format was incorrect </br>
	 */
	public TabError convertTabs() {
		try {
			instrument = getInstrument();
			title = getTitle();
			guitarTuning = getTuning();
			Measure.setAttributes(new Attributes(guitarTuning));
			allMeasures = compileMeasures();
			measureElements = makeNotes();
		} catch (Exception e) {
			// TODO create error catching
			System.out.println("SOMETHING WENT WRONG");
		}

		return new TabError("done", 0);
	}

	/**
	 * Checks if a given line has tabs
	 * 
	 * @param line - a line from tabArray
	 * @return true iff the line contains 2 vertical bars and 2 dashes
	 */
	public boolean lineHasTabs(String line) {
		return line.lastIndexOf('-') > line.indexOf('-') && line.lastIndexOf('|') > line.indexOf('|');
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

		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < tabArray.size(); i++) {

			tabArray.get(i).trim();
			if (tabArray.get(i).indexOf('-') != -1 && tabArray.get(i).charAt(tabArray.get(i).length() - 1) == '|'
					&& tabArray.get(i).charAt(tabArray.get(i).length() - 2) == '|') {
				String deleteExtraBar = tabArray.get(i).substring(0, (tabArray.get(i).length() - 1));
				tabArray.set(i, deleteExtraBar);

			}

			if (tabArray.get(i).indexOf('-') != -1 && tabArray.get(i).charAt(tabArray.get(i).length() - 1) == '|'
					&& tabArray.get(i).charAt(tabArray.get(i).length() - 2) == '|') {
				String deleteExtraBar = tabArray.get(i).substring(0, (tabArray.get(i).length() - 1));
				tabArray.set(i, deleteExtraBar);

			}

			if (tabArray.get(i).indexOf('-') != -1) {
				String addLine = tabArray.get(i).substring(0, (tabArray.get(i).lastIndexOf('|') + 1));
				temp.add(addLine);
			}

		}

		int numLines = temp.size() / 6;
		for (int i = 1; i < numLines; i++) {
			int insert = i * 6 + (i - 1);
			String blank = " ";
			temp.add(insert, blank);
		}

		return temp;

	}

	public List<String> getTuning() {
		List<String> guitarTuning = new ArrayList<String>();
		int numStrings = 6;
		int i = 0;
		while (i < tabArray.size() && guitarTuning.size() < numStrings) {
			String line = tabArray.get(i);
			if (lineHasTabs(line)) {
				guitarTuning.add(line.substring(0, line.indexOf('|')).toUpperCase().replaceAll("\\s", ""));
			}
			i++;
		}

		return guitarTuning;
	}

	public String getTitle() {
		if (file == null) {
			return "Title";
		}

		return file.getName().split("\\.")[0];
	}

	public List<Measure> makeNotes() {
		List<Measure> measureElements = new ArrayList<Measure>();

		for (int i = 0; i < allMeasures.size(); i++) {
			ArrayList<String> measuresAsStrings = allMeasures.get(i);
			Measure measure = new Measure(i + 1);
			int noteCounter = 0;
			for (int j = 0; j < measuresAsStrings.size(); j++) {
				String currentLine = measuresAsStrings.get(j);
				measure.setIndexTotal(currentLine.length());
				String temp;
				for (int k = 0; k < currentLine.length(); k++) {

					if (currentLine.charAt(k) == '^' || currentLine.charAt(k) == '~' || currentLine.charAt(k) == '*'
							|| currentLine.charAt(k) == '_' || currentLine.charAt(k) == '('
							|| currentLine.charAt(k) == ')' || currentLine.charAt(k) == '['
							|| currentLine.charAt(k) == ']' || currentLine.charAt(k) == 'n'
							|| currentLine.charAt(k) == 'f' || currentLine.charAt(k) == '�'
							|| currentLine.charAt(k) == 'x') {
						continue;
					}

					if (currentLine.charAt(k) != '-') {

						if (currentLine.charAt(k) == 'p' || currentLine.charAt(k) == 'h' || currentLine.charAt(k) == 's'
								|| currentLine.charAt(k) == '/' || currentLine.charAt(k) == '\\'
								|| currentLine.charAt(k) == 'b') {

							if (measure.getNotes().isEmpty())
								continue;

							if (currentLine.charAt(k) == 'p') {
								measure.getNote(noteCounter - 1).slurStart = true;
								measure.getNote(noteCounter - 1).pullStart = true;
							}

							if (currentLine.charAt(k) == 'h') {
								measure.getNote(noteCounter - 1).slurStart = true;
								measure.getNote(noteCounter - 1).hammerStart = true;
							}

							if (currentLine.charAt(k) == 's' || currentLine.charAt(k) == '/'
									|| currentLine.charAt(k) == '\\')
								measure.getNote(noteCounter - 1).slideStart = true;

							if (currentLine.charAt(k) == 'b')
								measure.getNote(noteCounter - 1).bendStart = true;

							continue;

						}

						if (currentLine.charAt(k + 1) != '-') {
							if (currentLine.charAt(k + 1) == '0' || currentLine.charAt(k + 1) == '1'
									|| currentLine.charAt(k + 1) == '2' || currentLine.charAt(k + 1) == '3'
									|| currentLine.charAt(k + 1) == '4' || currentLine.charAt(k + 1) == '5'
									|| currentLine.charAt(k + 1) == '6' || currentLine.charAt(k + 1) == '7'
									|| currentLine.charAt(k + 1) == '8' || currentLine.charAt(k + 1) == '9') {
								temp = currentLine.substring(k, k + 2);
								int fret = Integer.valueOf(temp);
								Note note = new Note(j + 1, guitarTuning.get(j), fret, k);
								measure.addNote(note);
								noteCounter++;
								if (k + 1 == currentLine.length())
									break;
								k++;

								if (measure.size() > 1) {
									if (measure.getNotes().get(measure.getNotes().size() - 2).slurStart) {
										note.slurStop = true;
										if (measure.getNotes().get(measure.getNotes().size() - 2).pullStart) {
											note.pullStop = true;
										}
										if (measure.getNotes().get(measure.getNotes().size() - 2).hammerStart) {
											note.hammerStop = true;
										}

									}

									if (measure.getNotes().get(measure.getNotes().size() - 2).slideStart)
										note.slideStop = true;

									if (measure.getNotes().get(measure.getNotes().size() - 2).bendStart) {
										measure.getNotes().get(measure.getNotes().size() - 2).bendAlter = (note.fret
												- measure.getNotes().get(measure.getNotes().size() - 2).fret) * 4;
									}
								}

								continue;
							}
						}

						if (currentLine.charAt(k + 1) == '-' || currentLine.charAt(k + 1) == 'p'
								|| currentLine.charAt(k + 1) == 'h' || currentLine.charAt(k + 1) == 's'
								|| currentLine.charAt(k + 1) == '/' || currentLine.charAt(k + 1) == '\\'
								|| currentLine.charAt(k + 1) == ']' || currentLine.charAt(k + 1) == ')') {
							temp = currentLine.substring(k, k + 1);
							int fret = Integer.valueOf(temp);
							Note note = new Note(j + 1, guitarTuning.get(j), fret, k);
							measure.addNote(note);
							noteCounter++;
							if (measure.size() > 1) {
								if (measure.getNotes().get(measure.getNotes().size() - 2).slurStart)
									note.slurStop = true;

								if (measure.getNotes().get(measure.getNotes().size() - 2).pullStart)
									note.pullStop = true;

								if (measure.getNotes().get(measure.getNotes().size() - 2).hammerStart)
									note.hammerStop = true;

								if (measure.getNotes().get(measure.getNotes().size() - 2).slideStart)
									note.slideStop = true;

								if (measure.getNotes().get(measure.getNotes().size() - 2).bendStart) {
									measure.getNotes().get(measure.getNotes().size() - 2).bendAlter = (note.fret
											- measure.getNotes().get(measure.getNotes().size() - 2).fret) * 4;
								}
							}

						}
					}

				}
			}
			noteCounter = 0;
			measure.sortArray();
			setDuration(measure);
			noteDuration(measure);
			noteType(measure);

			measureElements.add(measure);

		}
		return measureElements;

	}

	public ArrayList<Integer> countBars() {
		ArrayList<Integer> countArray = new ArrayList<>();
		for (int i = 0; i < tabArray.size(); i++) {
			if (tabArray.get(i).contains("-")) {
				ArrayList<Integer> indices = new ArrayList<Integer>();
				int index = 0;
				while ((index = tabArray.get(i).indexOf('|', index + 1)) > 0) {
					indices.add(index);
				}
				for (int j = 0; j < indices.size() - 1; j++) {
					String lineBefore = "";
					lineBefore = tabArray.get(i - 1);
					int lastIndex = indices.get(j + 1) > lineBefore.lastIndexOf('|') ? lineBefore.lastIndexOf('|')
							: indices.get(j + 1);
					lineBefore = lineBefore.substring(indices.get(j), lastIndex + 1);
					int count = 0;
					for (String s : lineBefore.split("\\s+")) {
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

	public List<ArrayList<String>> splitMeasure(List<String> tabArray, int length) {
		List<ArrayList<String>> split = new ArrayList<ArrayList<String>>();
		HashMap<Integer, String> measure = new HashMap<Integer, String>();
		String line = "";
		int k = 0;
		String str;

		while (k < length) {
			if (lineHasTabs(tabArray.get(k))) {
				line = tabArray.get(k);
				String[] lineArray = line.split("\\|");

				for (int j = 1; j < lineArray.length; j++) {

					if (measure.containsKey(j)) {
						measure.put(j, measure.get(j) + lineArray[j] + "\n");

					} else {
						measure.put(j, lineArray[j] + "\n");
					}
				}

			}
			k++;
		}

		for (int j = 1; j <= measure.size(); j++) {
			String string = measure.get(j);
			ArrayList<String> splitMeasure = new ArrayList<String>();
			for (String s : string.split("\n")) {
				splitMeasure.add(s);
			}
			split.add(splitMeasure);

		}

		return split;
	}

	public List<ArrayList<String>> compileMeasures() {
		List<ArrayList<String>> measures = new ArrayList<ArrayList<String>>();
		final int tabArraySize = tabArray.size();
		int i = 0;

		while (i < tabArraySize) {
			List<String> tabs = new ArrayList<String>();
			boolean tabsFound = false;

			while (i < tabArraySize) {
				String line = tabArray.get(i);

				if (lineHasTabs(line)) {
					tabsFound = true;
					tabs.add(line);
				}

				if (tabsFound && !lineHasTabs(line)) {
					break;
				}

				i++;
			}

			if (!tabs.isEmpty()) {
				measures.addAll(splitMeasure(tabs, tabs.size()));
			}
		}

		return measures;
	}

	public void setDuration(Measure measure) {

		int indexTotal = measure.getIndexTotal();
		int firstIndex = measure.getNotes().get(0).charIndex;
		int totalChar = indexTotal - firstIndex;
		int eachBeatVal = totalChar / 4;
		double eachCharVal = 8 / (double) eachBeatVal;
		eachCharVal = Math.ceil(eachCharVal);

		measure.durationVal = (int) eachCharVal;

	}

	public void noteDuration(Measure measure) {
		List<Note> noteArr = measure.getNotes();
		for (int i = 0; i < noteArr.size(); i++) {
			if (i == (noteArr.size() - 1)) {
				noteArr.get(i).duration = (measure.getIndexTotal() - noteArr.get(i).charIndex) * measure.durationVal;
				if (noteArr.size() > 1 && (noteArr.get(noteArr.size() - 2).charIndex
						- noteArr.get(noteArr.size() - 1).charIndex == 0)) {
					noteArr.get(noteArr.size() - 1).chord = true;
				}
			} else {
				noteArr.get(i).duration = (noteArr.get(i + 1).charIndex - noteArr.get(i).charIndex)
						* measure.durationVal;
			}
			if (noteArr.get(i).duration == 0) {
				int newDuration = 0;
				int indexForward = i + 1;
				while (newDuration == 0) {
					if (indexForward == noteArr.size() - 1) {
						newDuration = (measure.getIndexTotal() - noteArr.get(indexForward).charIndex)
								* measure.durationVal;
						break;
					}
					newDuration = (noteArr.get(indexForward + 1).charIndex - noteArr.get(indexForward).charIndex)
							* measure.durationVal;
					noteArr.get(indexForward).chord = true;
					indexForward++;
				}
				noteArr.get(i).duration = newDuration;
			}
		}
	}

	public void noteType(Measure measure) {
		List<Note> noteArr = measure.getNotes();
		for (int i = 0; i < noteArr.size(); i++) {

			int noteDur = noteArr.get(i).duration;
			Note note = noteArr.get(i);

			if (noteDur == 1)
				note.type = "32nd";
			if (noteDur == 2) {
				note.type = "16th";
			}
			if (noteDur == 3) {
				note.type = "16th";
				note.dot = true;
			}

			if (noteDur == 4) {
				note.type = "eighth";
			}

			if (noteDur > 4 && noteDur < 8) {
				note.type = "eighth";
				note.dot = true;
			}

			if (noteDur == 8)
				note.type = "quarter";

			if (noteDur > 8 && noteDur < 16) {
				note.type = "quarter";
				note.dot = true;
			}

			if (noteDur == 16) {
				note.type = "half";
			}

			if (noteDur > 16 && noteDur <= 24) {
				note.type = "half";
				note.dot = true;
			}

			if (noteDur > 24)
				note.type = "whole";

		}

	}

	/**
	 * Detects and returns the instrument used in the tabs
	 * 
	 * @return the instrument name
	 */
	public String getInstrument() {
		boolean isDrums = true;
		for (String t : getTuning()) {
			System.out.println("HERE:  " + t);
			if (Pitch.ALL_NOTES_MAP.containsKey(t)) {
				isDrums = false;
				break;
			}
		}
		if (isDrums) {
			return "Drumset";
		}

		int lines = 0;
		boolean isCountStarted = false;
		for (int i = 0; i < tabArray.size(); i++) {
			if (lineHasTabs(tabArray.get(i))) {
				lines++;
				isCountStarted = true;
			}

			if (isCountStarted && !lineHasTabs(tabArray.get(i))) {
				break;
			}
		}

		return lines == 4 ? "Bass" : "Classical Guitar";
	}

	public String toMXL() {
		StringBuilder builder = new StringBuilder();
		String headingMXL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "<work>\n" + "\t<work-title>" + title + "</work-title>\n"
				+ "</work>\n" + "<part-list>\n" + "\t<score-part id=\"P1\">\n" + "\t\t<part-name>"
				+ TabReader.instrument + "</part-name>\n" + "\t</score-part>\n" + "</part-list>\n"
				+ "<part id=\"P1\">\n";
		builder.append(headingMXL);

		for (Measure m : getMeasures()) {
			builder.append(m).append("\n");
		}

		builder.append("</part>\n</score-partwise>");
		return builder.toString();
	}
}