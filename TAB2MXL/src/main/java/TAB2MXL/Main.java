package TAB2MXL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
		System.out.println("Savvy added.");
		System.out.println("Sara added to Java 1.8.");
		System.out.println("Push test");
		
		System.out.println("Nish made a change");
		System.out.println("Savvy changed Java version to 1.8");
		
		public Transcript(String inFile, String outFile) {
			inputFile = new File(inFile);
			outputFile = outFile;
			grade = new ArrayList<Object>();
			this.readFile();
		}// end of Transcript constructor

		/**
		 * This method reads a text file and add each line as an entry of grade
		 * ArrayList.
		 * 
		 * @exception FileNotFoundException if the file is not found.
		 */
		private void readFile() {
			Scanner sc = null;
			try {
				sc = new Scanner(inputFile);
				while (sc.hasNextLine()) {
					grade.add(sc.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
		} // end of readFile

		/**
		 * This method prints the transcript to a given file, it prints the transcript
		 * associated with the outputFile instance variable.
		 * 
		 * @param arrayStudent is the ArrayList of Student objects constructed from
		 *                     information extracted from the inputFile instance
		 *                     variable.
		 * 
		 */
		public void printTranscript(ArrayList<Student> arrayStudent) {

			// format information from the ArrayList so that it fits the format for the
			// transcript when printed

			outputFile = "";

			for (int i = 0; i < arrayStudent.size(); i++) {
				outputFile = outputFile.concat(arrayStudent.get(i).getName() + "\t" + arrayStudent.get(i).getID() + "\n"
						+ "--------------------" + "\n");
				for (int j = 0; j < arrayStudent.get(i).getCourseArray().size(); j++) {
					outputFile = outputFile.concat(arrayStudent.get(i).getCourseArray().get(j).getCode() + "\t"
							+ arrayStudent.get(i).getGradeArray().get(j)) + "\n";
				}
				outputFile = outputFile
						.concat("--------------------" + "\n" + "GPA: " + arrayStudent.get(i).weightedGPA() + "\n\n");
			}
		}

		/**
		 * This method returns the private instance variable outputFile, which cannot be
		 * printed otherwise.
		 * 
		 * @return A String copy of the outputFile instance variable.
		 */

		public String getOutputFile() {
			return this.outputFile;
		}

		/**
		 * This method extracts information from the File associated with the instance
		 * variable inputFile, builds an ArrayList of Students and assigns the extracted
		 * information to each respective Student.
		 * 
		 * @return An ArrayList of Students with assigned IDs, courses, and final grades
		 *         from a given text file.
		 * 
		 */

		public ArrayList<Student> buildStudentArray() {

			ArrayList<String> gradeStr = new ArrayList<String>();
			ArrayList<Student> allStudentArr = new ArrayList<Student>();
			ArrayList<String> allStudentNames = new ArrayList<String>();

			// Cast every Object from the original ArrayList instance variable grade to be a
			// String so we can access useful string methods

			for (int i = 0; i < this.grade.size(); i++) {
				String nextLine = (String) this.grade.get(i);
				gradeStr.add(nextLine);
			}

			// Create an ArrayList of just names (String) with no duplicates

			String studentName = "";
			for (int i = 0; i < this.grade.size(); i++) {
				int indexOfName = gradeStr.get(i).lastIndexOf(",") + 1;
				studentName = gradeStr.get(i).substring(indexOfName, gradeStr.get(i).length());
				if (!allStudentNames.contains(studentName))
					allStudentNames.add(studentName);
			}

			// Create an ArrayList of new Student objects, use the Arraylist of student
			// names (above) to assign instance variable name for each Student

			for (int j = 0; j < allStudentNames.size(); j++) {
				Student nextStudent = new Student();
				nextStudent.setName(allStudentNames.get(j));
				allStudentArr.add(nextStudent);
			}

			for (int k = 0; k < gradeStr.size(); k++) {

				// Use method split() with "," as delimiter, making each line into another new
				// array then convert to a list for use of more methods

				String[] eachLineElements = gradeStr.get(k).split(",");
				List<String> eachLineArray = Arrays.asList(eachLineElements);

				// initialize the arrays & objects to build on each line

				ArrayList<Assessment> eachLineAssess = new ArrayList<Assessment>();
				ArrayList<Double> eachLineGrades = new ArrayList<Double>();
				ArrayList<Integer> eachLineWeights = new ArrayList<Integer>();
				Course eachLineCourse = new Course();

				// Parse information from the indexes of the array sandwiched between course
				// code, course credit and student name and add the information to respective
				// arrays

				if (eachLineArray != null) {
					for (int m = 3; m < eachLineArray.size() - 1; m++) {

						char nextType = eachLineArray.get(m).charAt(0);

						int nextWeight = Integer
								.parseInt(eachLineArray.get(m).substring(1, eachLineArray.get(m).indexOf("(")));
						eachLineWeights.add(nextWeight);

						double nextGrade = Double.parseDouble(eachLineArray.get(m)
								.substring(eachLineArray.get(m).indexOf("(") + 1, eachLineArray.get(m).indexOf(")")));
						eachLineGrades.add(nextGrade);

						Assessment nextAssess = Assessment.getInstance(nextType, nextWeight);
						eachLineAssess.add(nextAssess);
					}
				}

				// Use array list of Assessments built in previous for loop for Course object
				// instance variable and use known indexes to assign code and credit

				eachLineCourse.setAssignment(eachLineAssess);
				eachLineCourse.setCode(eachLineArray.get(0));
				eachLineCourse.setCredit(Double.parseDouble(eachLineArray.get(1)));

				// loop through the array of Student objects until current line array last index
				// String matches instance variable name of one of the Students
				// set all values for matched Student

				for (int y = 0; y < allStudentNames.size(); y++) {
					if (allStudentArr.get(y).getName().equals(eachLineArray.get(eachLineArray.size() - 1))) {
						allStudentArr.get(y).setID(eachLineArray.get(2));
						allStudentArr.get(y).addCourse(eachLineCourse);
						allStudentArr.get(y).addGrade(eachLineGrades, eachLineWeights);

					}
				}

				// this ends iteration of the loop, which will repeat until all input file
				// information has been added to respective Student objects
			}

			return allStudentArr;

		}

	}
	}

}
