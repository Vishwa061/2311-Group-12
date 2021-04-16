package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TabReader {
	private List<String> rawTabArray;
	private List<String> tabArray;
	private List<String> guitarTuning;
	private List<Measure> measureElements;
	private List<ArrayList<String>> allMeasures;
	public static String instrument;
	private String title;
	private File file;
	private int numStrings;
	private List<ArrayList<String>> scoreInstrument;
	private List<Character> techniques;
	private List<Character> drumsetTechniques;
	private int key;
	private String composer;
	private int beats;
	private int beatType;
	private List<Repeat> repeats;

	public static void main(String[] args) {
		TabReader reader = new TabReader();
//		reader.setInput(new File("src/test/resources/StairwayHeaven.txt"));
		// reader.setInput(new File("src/test/resources/SmellsLikeTeenSpirit.txt"));
		// reader.setInput(new File("src/test/resources/LastCharTest.txt"));
		// reader.setInput(new File("src/test/resources/ChopSuey.txt"));
		reader.setInput(new File("src/test/resources/drumBeamsTest.txt"));
		// reader.setInput(new File("src/test/resources/SplitDrum.txt"));
//		reader.setInput(new File("src/test/resources/basic_bass.txt"));
		// reader.setInput(new File("src/test/resources/BadMeasure.txt"));
		// reader.setInput(new File("src/test/resources/examplerepeat.txt"));
		reader.convertTabs();
		System.out.println(reader.toMXL());

		// System.out.println(reader.scoreInstrument);
//		for (String s : reader.tabArray) {
//			System.out.println(s);
//		}

//		System.out.println(reader.scoreInstrument);
//		System.out.println(reader.toMXL());
	}

	public TabReader() {
		init();
		title = "Title";
		composer = "";
		beats = 4;
		beatType = 4;
		key = 0;
	}

	private void init() {
		rawTabArray = new ArrayList<String>();
		tabArray = new ArrayList<String>();
		guitarTuning = new ArrayList<String>();
		measureElements = new ArrayList<Measure>();
		allMeasures = new ArrayList<ArrayList<String>>();
		scoreInstrument = new ArrayList<ArrayList<String>>();
		techniques = new ArrayList<Character>();
		techniques.addAll(Arrays.asList('\\', '/', 'b', 'g', 'h', 'p', 'r', 'S', 's'));
		drumsetTechniques = new ArrayList<Character>();
		drumsetTechniques.addAll(Arrays.asList('O', 'f', 'd', 'b', 'x', 'X', 'o', '#', 'g', '@', 's', 'S', 'c', 'C'));
		repeats = new ArrayList<Repeat>();
	}

	public void setInput(String fileAsString) {
		init();
		tabArray = Arrays.asList(fileAsString.split("\\n"));
		tabArray = filterInput();
	}

	public String setInput(File inputFile) {
		init();
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
	 * @return a TabError
	 */
	public TabError convertTabs() {
		try {
			instrument = getInstrument();
			title = getTitle();
			numStrings = countNumStrings(tabArray);
			guitarTuning = getTuning();
			Attributes attr = new Attributes(guitarTuning);
			attr.setKey(key);
			Measure.setAttributes(attr);
			allMeasures = compileMeasures();
			measureElements = TabReader.instrument.equals("Drumset") ? makeDrumNotes() : makeNotes();
			addRepeats();
		} catch (Exception e) {
			// TODO create error catching
			System.out.println("SOMETHING WENT WRONG");
			e.printStackTrace();
		}

		return new TabError("done", 0, "");
	}

	/**
	 * Saves a given measure
	 * 
	 * @param measureNumber   - the zero-indexed measure number
	 * @param measureAsString - the edited measure
	 * @return the edited tabs
	 */
	public String editMeasure(int measureNumber, String measureAsString) {
		List<String> temp = Arrays.asList(measureAsString.split("\\n"));

		// List to ArrayList
		ArrayList<String> measure = new ArrayList<String>();
		measure.addAll(temp);

		allMeasures.remove(measureNumber);
		if (measureNumber >= allMeasures.size()) { // accounts for when the user edits the last measure
			allMeasures.add(measure);
		}
		allMeasures.add(measureNumber, measure);

		tabArray.clear();
		for (int i = 0; i < allMeasures.size(); i++) {
			ArrayList<String> m = allMeasures.get(i);
			for (int j = 0; j < m.size(); j++) {
				if (m.get(j).lastIndexOf('-') > m.get(j).indexOf('-')) {
					tabArray.add(guitarTuning.get(j) + "|" + m.get(j) + "|" + "\n"); // THIS ONLY WORKS FOR GUITAR RIGHT
																						// NOW
					// System.out.println(guitarTuning.get(j)+"|"+m.get(j)+"|");
				}
			}
			tabArray.add("\n");
			// System.out.println("");
		}

		String savedTabs = "";
		for (int i = 0; i < tabArray.size(); i++) {
			savedTabs += tabArray.get(i);
		}

		return savedTabs;
	}

	/**
	 * Checks if a given line has tabs
	 * 
	 * @param line - a line from tabArray
	 * @return true iff the line contains 2 vertical bars and 2 dashes
	 */
	public boolean lineHasTabs(String line) {
		if (line.indexOf('-') == -1) {
			boolean containsTechniques = true;
			char[] lineArr = line.toCharArray();
			int start = line.indexOf('|') + 1;
			int end = line.lastIndexOf('|');
			
			for (int i = start; i < end; i++) {
				
				if (Character.isDigit(lineArr[i]))
					continue;
				
				if (this.getInstrument().equals("Drumset") && !drumsetTechniques.contains(lineArr[i])) {
					containsTechniques = false;
					break;
				}
				
				if (this.getInstrument().equals("Classical Guitar") && !techniques.contains(lineArr[i])) {
					containsTechniques = false;
					break;
				}
				
				if (this.getInstrument().equals("Bass") && !techniques.contains(lineArr[i])) {
					containsTechniques = false;
					break;
				}
			}
			
			return line.lastIndexOf('|') > line.indexOf('|') && containsTechniques;
		}

		return line.lastIndexOf('-') > line.indexOf('-') && line.lastIndexOf('|') > line.indexOf('|');
	}

	public List<Measure> getMeasures() {
		return this.measureElements;
	}

	public List<String> readFile(File inputFile) {
		this.tabArray = new ArrayList<String>();
		this.rawTabArray = new ArrayList<String>();
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

		this.tabArray = tabArray;
		return filterInput();
	}

	public List<String> filterInput() {
		rawTabArray.addAll(tabArray);
		numStrings = countNumStrings(tabArray);
		TabReader.instrument = getInstrument();

		for (int i = 0; i < tabArray.size(); i++) {
			tabArray.set(i, tabArray.get(i).replaceAll("\\|\\|", "|"));
		}

		if (TabReader.instrument.equals("Drumset")) {
			return tabArray;
		}

		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < tabArray.size(); i++) {

			tabArray.get(i).trim();

			if (tabArray.get(i).isEmpty())
				continue;

			if (tabArray.get(i).indexOf('x') != -1) {
				String replaceLine = tabArray.get(i).replace('x', '0');
				tabArray.set(i, replaceLine);
			}

			for (int j = 0; j < tabArray.get(i).length(); j++) {
				if (tabArray.get(i).charAt(j) != '-' && tabArray.get(i).charAt(j) != '|'
						&& !(techniques.contains(tabArray.get(i).charAt(j)))
						&& !(Character.isDigit(tabArray.get(i).charAt(j))))
					tabArray.get(i).replace(tabArray.get(i).charAt(j), '-');
			}

			if (tabArray.get(i).indexOf('-') != -1 && tabArray.get(i).charAt(tabArray.get(i).length() - 1) == '|'
					&& tabArray.get(i).charAt(tabArray.get(i).length() - 2) == '|') {
				String deleteExtraBar = tabArray.get(i).substring(0, (tabArray.get(i).length() - 1));
				tabArray.set(i, deleteExtraBar);
			}

			if (tabArray.get(i).indexOf('-') != -1 && tabArray.get(i).indexOf('|') != -1) {
				String addLine = tabArray.get(i).substring(0, (tabArray.get(i).lastIndexOf('|') + 1));
				temp.add(addLine);
				// System.out.println(addLine);
			}
		}

		int numLines = temp.size() / numStrings;

		for (int i = 1; i < numLines; i++) {
			int insert = i * numStrings + (i - 1);
			String blank = " ";
			temp.add(insert, blank);
		}

		return temp;

	}

	public List<String> getTuning() {
		List<String> guitarTuning = new ArrayList<String>();
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

	public void setFile(File file) {
		this.file = file;
	}

	public String getTitle() {
		if (!title.equals("Title") || file == null) {
			return title;
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

					if (currentLine.charAt(k) != '-') {
						if (k == (currentLine.length() - 1)) {
							if (techniques.contains(currentLine.charAt(k)))
								continue;
							temp = currentLine.substring(k);
							int fret = Integer.valueOf(temp);
							Note note = new Note(j + 1, guitarTuning.get(j), fret, k);
							measure.addNote(note);
							noteCounter++;

							if (measure.size() > 1) {
								if (measure.getNotes().get(measure.getNotes().size() - 2).slurStart) {
									note.slurStop = true;
									if (measure.getNotes().get(measure.getNotes().size() - 2).pullStart)
										note.pullStop = true;
									if (measure.getNotes().get(measure.getNotes().size() - 2).hammerStart)
										note.hammerStop = true;
								}

								if (measure.getNotes().get(measure.getNotes().size() - 2).slideStart)
									note.slideStop = true;
								if (measure.getNotes().get(measure.getNotes().size() - 2).bend
										|| measure.getNotes().get(measure.getNotes().size() - 2).release)
									measure.getNotes().get(measure.getNotes().size() - 2).bendAlter = (note.fret
											- measure.getNotes().get(measure.getNotes().size() - 2).fret) * 4;

							}
							continue;
						}

						if (techniques.contains(currentLine.charAt(k))) {

							if (currentLine.charAt(k) == 'p') {
								if (measure.getNotes().isEmpty()) {
									Measure prevMeasure = measureElements.get(measureElements.size() - 1);
									Note lastNote = prevMeasure.getNotes().get(prevMeasure.size() - 1);
									lastNote.slurStart = true;
									lastNote.pullStart = true;
									continue;
								}
								measure.getNote(noteCounter - 1).slurStart = true;
								measure.getNote(noteCounter - 1).pullStart = true;
							}

							if (currentLine.charAt(k) == 'h') {
								if (measure.getNotes().isEmpty()) {
									Measure prevMeasure = measureElements.get(measureElements.size() - 1);
									Note lastNote = prevMeasure.getNotes().get(prevMeasure.size() - 1);
									lastNote.slurStart = true;
									lastNote.hammerStart = true;
									continue;
								}
								measure.getNote(noteCounter - 1).slurStart = true;
								measure.getNote(noteCounter - 1).hammerStart = true;
							}

							if (currentLine.charAt(k) == 's' || currentLine.charAt(k) == '/'
									|| currentLine.charAt(k) == '\\') {
								if (measure.getNotes().isEmpty()) {
									Measure prevMeasure = measureElements.get(measureElements.size() - 1);
									Note lastNote = prevMeasure.getNotes().get(prevMeasure.size() - 1);
									lastNote.slideStart = true;
									continue;
								}
								measure.getNote(noteCounter - 1).slideStart = true;
							}

							if (currentLine.charAt(k) == 'b') {
								if (measure.getNotes().isEmpty()) {
									Measure prevMeasure = measureElements.get(measureElements.size() - 1);
									Note lastNote = prevMeasure.getNotes().get(prevMeasure.size() - 1);
									lastNote.bend = true;
									continue;
								}
								measure.getNote(noteCounter - 1).bend = true;
							}

							if (currentLine.charAt(k) == 'r') {
								if (measure.getNotes().isEmpty()) {
									Measure prevMeasure = measureElements.get(measureElements.size() - 1);
									Note lastNote = prevMeasure.getNotes().get(prevMeasure.size() - 1);
									lastNote.release = true;
									continue;
								}
								measure.getNote(noteCounter - 1).release = true;
							}

							continue;
						}

						if (Character.isDigit(currentLine.charAt(k + 1))) {
							temp = currentLine.substring(k, k + 2);
							int fret = Integer.valueOf(temp);
							Note note = new Note(j + 1, guitarTuning.get(j), fret, k);
							measure.addNote(note);
							noteCounter++;

							if (measure.size() > 1) {
								if (measure.getNotes().get(measure.getNotes().size() - 2).slurStart) {
									note.slurStop = true;
									if (measure.getNotes().get(measure.getNotes().size() - 2).pullStart)
										note.pullStop = true;
									if (measure.getNotes().get(measure.getNotes().size() - 2).hammerStart)
										note.hammerStop = true;
								}
								if (measure.getNotes().get(measure.getNotes().size() - 2).slideStart)
									note.slideStop = true;
								if (measure.getNotes().get(measure.getNotes().size() - 2).bend
										|| measure.getNotes().get(measure.getNotes().size() - 2).release)
									measure.getNotes().get(measure.getNotes().size() - 2).bendAlter = (note.fret
											- measure.getNotes().get(measure.getNotes().size() - 2).fret) * 4;
							}

							if (k + 1 == currentLine.length())
								break;

							k++;
							continue;
						}

						if (currentLine.charAt(k + 1) == '-' || techniques.contains(currentLine.charAt(k + 1))) {

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
								if (measure.getNotes().get(measure.getNotes().size() - 2).bend
										|| measure.getNotes().get(measure.getNotes().size() - 2).release)
									measure.getNotes().get(measure.getNotes().size() - 2).bendAlter = (note.fret
											- measure.getNotes().get(measure.getNotes().size() - 2).fret) * 4;

							}

						}

					}

				}
			}

			if (measure.getNotes().isEmpty()) {
				measureElements.add(measure);
				continue;
			}

			noteCounter = 0;
			measure.sortArray();
			measure.setGrace();
			setDuration(measure);
			noteDuration(measure);
			noteType(measure);

			measureElements.add(measure);

		}
		return measureElements;
	}

	public List<Measure> makeDrumNotes() {
		List<Measure> measureElements = new ArrayList<Measure>();
		int amSize = allMeasures.size();

		for (int i = 0; i < amSize; i++) {
			ArrayList<String> measuresAsStrings = allMeasures.get(i);
			ArrayList<String> scoreInstruments = scoreInstrument.get(i);
			Measure measure = new Measure(i + 1);
			int mSize = measuresAsStrings.size();

			for (int j = 0; j < mSize; j++) {
				String currentLine = measuresAsStrings.get(j);
				String scoreIns = scoreInstruments.get(j);
				int lineLength = currentLine.length();

				for (int k = 0; k < lineLength; k++) {
					if (drumsetTechniques.contains(currentLine.charAt(k))) {

						Note note = new Note(scoreIns, Character.toString(currentLine.charAt(k)), k);
						measure.addNote(note);

					}

					if (measure.getIndexTotal() == 0)
						measure.setIndexTotal(currentLine.length());
				}
			}

			measure.sortArray();
			if (measure.getNotes().isEmpty()) {
				measureElements.add(measure);
				continue;
			}
			setDuration(measure);
			noteDuration(measure);
			noteType(measure);
			beamType(measure);
			measureElements.add(measure);
		}

		return measureElements;
	}

	public List<ArrayList<String>> splitMeasure(List<String> tabArray, int length) {
		List<ArrayList<String>> split = new ArrayList<ArrayList<String>>();
		ArrayList<String> splitDrum = new ArrayList<String>();
		HashMap<Integer, String> measure = new HashMap<Integer, String>();
		String line = "";
		int k = 0;

		if (TabReader.instrument.equals("Drumset")) {
			while (k < length) {
				line = tabArray.get(k);
//				String[] lineArray2 = line.split(line != null ? "HH" : "SD");
//				 lineArray2 = line.split(line != null ? "HT" : "MT");
//				 lineArray2 = line.split("BD");
				String[] lineArray2 = line.split("\\|");
				String Score = lineArray2[0];
				splitDrum.add(Score);
				// System.out.println(Score);

				for (int j = 1; j < lineArray2.length; j++) {
					if (measure.containsKey(j)) {
						measure.put(j, measure.get(j) + lineArray2[j] + "\n");

					} else {
						measure.put(j, lineArray2[j] + "\n");
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
				scoreInstrument.add(splitDrum);

			}
			return split;
		}

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

	/**
	 * 
	 * @param line
	 * @param start - inclusive
	 * @param end   - exclusive
	 * @return number of measures between start and end
	 */
	public int countMeasuresInRange(String line, int start, int end) {
		if (start >= end) {
			return 0;
		}

		int measures = 0;
		for (int i = start; i < end; i++) {
			if (line.charAt(i) == '|') {
				measures++;
			}
		}

		return measures;
	}

	public List<ArrayList<String>> compileMeasures() {
		List<ArrayList<String>> measures = new ArrayList<ArrayList<String>>();
		final int tabArraySize = tabArray.size();
		int i = 0;
		boolean repeat = false;
		repeats = new ArrayList<Repeat>();

		while (i < tabArraySize) {
			List<String> tabs = new ArrayList<String>();
			List<String> rawTabs = new ArrayList<String>();
			boolean tabsFound = false;

			while (i < tabArraySize) {
				String line = tabArray.get(i);
				String rawLine = rawTabArray.get(i);
				if (lineHasTabs(line)) {
					tabsFound = true;
					tabs.add(line);
					rawTabs.add(rawLine);
				}

				if (tabsFound && !lineHasTabs(line)) {
					break;
				}
				i++;
			}

			if (!tabs.isEmpty()) {
				int measureNumber = measures.size(); // zero-indexed
				String secondLine = rawTabs.get(1);
				int prevRepeatIndex = 0;
				int repeatIndex = secondLine.indexOf("||");

				while (repeatIndex >= 0) {
					measureNumber += countMeasuresInRange(secondLine, prevRepeatIndex, repeatIndex);
					if (repeat) {
						// end repeat
						int j = repeatIndex;
						String firstRawLine = rawTabs.get(0);
						while (firstRawLine.charAt(j) != '|') {
							j++;
						}

						int numRepeats = Integer.parseInt(firstRawLine.substring(repeatIndex, j));
						repeats.add(new Repeat(measureNumber - 1, true, numRepeats));

						// removing # of repeats from tabs
						StringBuilder builder = new StringBuilder(tabs.get(0));
						builder.delete(repeatIndex - 1, j);
//						System.out.println(tabs.get(0).charAt(repeatIndex));
						tabs.set(0, builder.toString());
//						System.out.println(tabs.get(0));
//						System.out.println(measureNumber-1);
					} else {
						// start repeat
						repeats.add(new Repeat(measureNumber, false, 0));
//						System.out.println(measureNumber);
					}

					repeat = !repeat;
					prevRepeatIndex = repeatIndex + 2;
					repeatIndex = secondLine.indexOf("||", repeatIndex + 1);
					measureNumber++;
				}

				measures.addAll(splitMeasure(tabs, tabs.size()));
			}
		}

		return measures;
	}

	public void beamType(Measure measure) {
		List<Note> noteArr = measure.getNotes();
		List<String> types = new ArrayList<String>();
		List<Note> beamNotes = new ArrayList<Note>();
		types.add("eighth");
		types.add("16th");
		types.add("32nd");
		types.add("64th");

		for (int i = 0; i < noteArr.size(); i++) {
			Note note = noteArr.get(i);
			if (!note.chord && types.contains(note.type) && !note.drag && !note.flam) {

				if (i == 0) {
					note.beamStart = true;
					beamNotes.add(note);
					continue;
				}

				if (i != noteArr.size() - 1) {
					if (!types.contains(noteArr.get(i + 1).type) || noteArr.get(i + 1).drag
							|| noteArr.get(i + 1).flam) {
						note.beamEnd = true;
						beamNotes.add(note);
						continue;
					}
				}

				if (i > 0) {
					Note prev;
					if (!beamNotes.isEmpty()) {
						prev = beamNotes.get(beamNotes.size() - 1);
					} else {
						prev = noteArr.get(i - 1);
					}

					if (prev.beamEnd) {
						note.beamStart = true;
						beamNotes.add(note);
						continue;
					}

					if (prev.beamStart) {
						note.beamContinue1 = true;
						beamNotes.add(note);
						continue;
					}
					if (prev.beamContinue1) {
						note.beamContinue2 = true;
						beamNotes.add(note);
						continue;
					}
					if (prev.beamContinue2) {
						note.beamEnd = true;
						beamNotes.add(note);
						continue;
					}

					else
						note.beamStart = true;
					beamNotes.add(note);
				}
			}
		}
		assignBeamNumber(beamNotes);
	}

	public void assignBeamNumber(List<Note> arr) {
		for (int i = 0; i < arr.size(); i++) {
			Note note = arr.get(i);
			if (note.type.equals("eighth")) {
				note.beam1 = true;
			}
			if (note.type.equals("16th")) {
				note.beam2 = true;
			}
			if (note.type.equals("32nd")) {
				note.beam3 = true;
			}
			if (note.type.equals("64th")) {
				note.beam4 = true;
			}
		}
	}

	public void setDuration(Measure measure) {
		
		double eachCharVal = 0;
		int indexTotal = measure.getIndexTotal();
		int firstIndex = measure.getNotes().get(0).charIndex;
		int totalChar = indexTotal - firstIndex;
		int eachBeatVal = totalChar / beats;
		if (eachBeatVal < 1)
			eachBeatVal = 1;
		
		
		if (beatType == 1)
			eachCharVal = 32 / (double) eachBeatVal;
		if (beatType == 2)
			eachCharVal = 16 / (double) eachBeatVal;
		if (beatType == 4)
			eachCharVal = 8 / (double) eachBeatVal;
		if (beatType == 8)
			eachCharVal = 4 / (double) eachBeatVal;
		if (beatType == 16)
			eachCharVal = 2 / (double) eachBeatVal;

		if (eachCharVal < 1) 
			eachCharVal = 1;
		
		measure.durationVal = eachCharVal;
	}

	public void noteDuration(Measure measure) {
		List<Note> noteArr = measure.getNotes();
		for (int i = 0; i < noteArr.size(); i++) {
			if (i == (noteArr.size() - 1)) {
				double noteDur = (measure.getIndexTotal() - noteArr.get(i).charIndex) * measure.durationVal;
				noteDur = Math.round(noteDur);
				noteArr.get(i).duration = (int) noteDur;
				if (noteArr.size() > 1 && (noteArr.get(noteArr.size() - 2).charIndex
						- noteArr.get(noteArr.size() - 1).charIndex == 0)) {
					noteArr.get(noteArr.size() - 1).chord = true;
				}
			} else {
				double noteDur = (noteArr.get(i + 1).charIndex - noteArr.get(i).charIndex) * measure.durationVal;
				noteDur = Math.round(noteDur);
				noteArr.get(i).duration = (int) noteDur;
			}
			if (noteArr.get(i).duration == 0) {
				double newDuration = 0;
				int indexForward = i + 1;
				while (newDuration == 0) {
					if (indexForward == noteArr.size() - 1) {
						newDuration = (measure.getIndexTotal() - noteArr.get(indexForward).charIndex)
								* measure.durationVal;
						newDuration = Math.round(newDuration);
						break;
					}
					newDuration = (noteArr.get(indexForward + 1).charIndex - noteArr.get(indexForward).charIndex)
							* measure.durationVal;
					newDuration = Math.round(newDuration);
					noteArr.get(indexForward).chord = true;
					indexForward++;
				}
				noteArr.get(i).duration = (int) newDuration;
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

			if (noteDur == 2)
				note.type = "16th";
			if (noteDur == 3) {
				note.type = "16th";
				note.dot = true;
			}
			if (noteDur == 4)
				note.type = "eighth";
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
			if (noteDur == 16)
				note.type = "half";
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
		for (String t : getTuning()) {
			if (!Pitch.ALL_NOTES_MAP.containsKey(t)) {
				return "Drumset";
			}
		}

		int lines = countNumStrings(tabArray);
		return lines == 4 ? "Bass" : "Classical Guitar";
	}

	/**
	 * Counts the number of guitars strings
	 * 
	 * @param tabArray - the tab input
	 * @return an integer representing the number of guitar strings
	 */
	public int countNumStrings(List<String> tabArray) {
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

		return lines;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	/**
	 * @param timeSignature timeSignature[0] must have beat timeSignature[1] must
	 *                      have beat time
	 */
	public void setTimeSignature(int[] timeSignature) {
		this.beats = timeSignature[0];
		this.beatType = timeSignature[1];
	}

	public void addRepeats() {
		for (Repeat r : repeats) {
			measureElements.get(r.getMeasureNumber()).setRepeat(r);
		}
	}

	public String toMXL() {
		StringBuilder builder = new StringBuilder();
		String drumsetParts = "\t\t<score-instrument id=\"P1-I36\">\n"
				+ "\t\t\t<instrument-name>Bass Drum 1</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I37\">\n" + "\t\t\t<instrument-name>Bass Drum 2</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I38\">\n"
				+ "\t\t\t<instrument-name>Side Stick</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I39\">\r\n" + "\t\t\t<instrument-name>Snare</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I42\">\n"
				+ "\t\t\t<instrument-name>Low Floor Tom</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I43\">\n" + "\t\t\t<instrument-name>Closed Hi-Hat</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I44\">\n"
				+ "\t\t\t<instrument-name>High Floor Tom</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I45\">\n" + "\t\t\t<instrument-name>Pedal Hi-Hat</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I46\">\n"
				+ "\t\t\t<instrument-name>Low Tom</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I47\">\n" + "\t\t\t<instrument-name>Open Hi-Hat</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I48\">\n"
				+ "\t\t\t<instrument-name>Low-Mid Tom</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I49\">\n" + "\t\t\t<instrument-name>Hi-Mid Tom</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I50\">\n"
				+ "\t\t\t<instrument-name>Crash Cymbal 1</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I51\">\n" + "\t\t\t<instrument-name>High Tom</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I52\">\n"
				+ "\t\t\t<instrument-name>Ride Cymbal 1</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I53\">\n" + "\t\t\t<instrument-name>Chinese Cymbal</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I54\">\n"
				+ "\t\t\t<instrument-name>Ride Bell</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I55\">\n" + "\t\t\t<instrument-name>Tambourine</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I56\">\n"
				+ "\t\t\t<instrument-name>Splash Cymbal</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I57\">\n" + "\t\t\t<instrument-name>Cowbell</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I58\">\n"
				+ "\t\t\t<instrument-name>Crash Cymbal 2</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I60\">\n" + "\t\t\t<instrument-name>Ride Cymbal 2</instrument-name>\n"
				+ "\t\t</score-instrument>\n" + "\t\t<score-instrument id=\"P1-I64\">\n"
				+ "\t\t\t<instrument-name>Open Hi Conga</instrument-name>\n" + "\t\t</score-instrument>\n"
				+ "\t\t<score-instrument id=\"P1-I65\">\n" + "\t\t\t<instrument-name>Low Conga</instrument-name>\n"
				+ "\t\t</score-instrument>\n";

		String headingMXL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "<work>\n" + "\t<work-title>" + title + "</work-title>\n"
				+ "</work>\n"
				+ (composer.equals("") ? ""
						: "<identification>\n\t<creator type=\"composer\">By: " + composer
								+ "</creator>\n</identification>\n")
				+ "<part-list>\n" + "\t<score-part id=\"P1\">\n" + "\t\t<part-name>" + TabReader.instrument
				+ "</part-name>\n" + (TabReader.instrument.equals("Drumset") ? drumsetParts : "") + "\t</score-part>\n"
				+ "</part-list>\n" + "<part id=\"P1\">\n";
		builder.append(headingMXL);

		for (Measure m : getMeasures()) {
			builder.append(m).append("\n");
		}

		builder.append("</part>\n</score-partwise>");
		return builder.toString();
	}
}