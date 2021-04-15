package TAB2MXL;

public class TabError {
	private String exitCode;
	private int measureNumber;
	private String measure;

	public TabError(String exitCode, int measureNumber, String measure) {
		this.exitCode = exitCode;
		this.measureNumber = measureNumber;
		this.measure = measure;
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

	public int getMeasureNumber() {
		return measureNumber;
	}

	public String getMeasure() {
		return measure;
	}
}