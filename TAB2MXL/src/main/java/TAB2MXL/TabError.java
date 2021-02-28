package TAB2MXL;

public class TabError {
	private String exitCode;
	private int lineNumber;

	public TabError(String exitCode, int lineNumber) {
		this.exitCode = exitCode;
		this.lineNumber = lineNumber;
	}

	/**
	 * Gets the a string representing the exit code </br>
	 * Exit codes: </br>
	 * done - the tabs were successfully converted </br>
	 * empty - no tabs found </br>
	 * instrument - the instrument was not found </br>
	 * tuning - the tuning was not found </br>
	 * measure - the measure format was incorrect </br>
	 * @return errorCode
	 */
	public String getExitCode() {
		return exitCode;
	}

	/**
	 * Gets the line number for which the error occurs on
	 * @return lineNumber
	 */
	public int getLine() {
		return lineNumber;
	}

}