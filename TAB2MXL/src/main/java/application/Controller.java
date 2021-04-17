package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.transform.stream.StreamResult;
import TAB2MXL.TabReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Window;
import javafx.scene.control.TextField;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

	/*
	 * Translated File + Other Important Imports 
	 */
	private Window stage;
	BufferedReader input;
	StreamResult output;
	static int count = 0;


	/* 
	 * All FXML attributes from PrimaryStage below. 
	 */

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private File file; 

	@FXML
	private TextArea inputBox, outputBox;

	@FXML
	private Button select, convert, save, featureButton, startButton;

	@FXML
	private Button helpButton, timeSigButton, keyButton, titleButton, composerButton;

	@FXML 
	private Label UploadFileLabel;

	/*
	 * All FXML attributes from HelpWindow called below. 
	 */

	@FXML
	private Button closeButton;

	@FXML 
	private TextArea helpTextArea;

	/*
	 * All FXML attributes from TimeSigniture called below.  
	 */

	@FXML
	private Button cancelButton, SaveTimeSig;

	@FXML 
	private MenuButton beatOption, beatTimeOption;

	@FXML 
	private RadioMenuItem beat1,beat2,beat3,beat4,beat5,beat6;

	@FXML
	private RadioMenuItem beatTime1, beatTime2,beatTime3,beatTime4,beatTime5;

	int beat = 0;

	int beatTime = 0;
	/*
	 *  All FXML attributes from Title called below. 
	 */

	@FXML
	private Button cancelButtonTitle, SaveTitle;

	@FXML
	private TextField titleTextField;

	String title = "";

	/*
	 *  All FXML attributes from Composer + Attributes related called below. 
	 */

	@FXML
	private Button cancelButtonComposer, SaveComposer;

	@FXML 
	private TextField composerText;

	String ComposerName = "";

	/*
	 *  All FXML attributes from Key + Attributes related called below. 
	 */

	@FXML
	private Button cancelKey, saveKey;

	@FXML 
	private MenuButton keyOption;

	@FXML 
	private RadioMenuItem key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,key11,key12,key13,key14; 

	static int keyFifths = 0;

	String keySelected = "";
	/*
	 *  All FXML attributes from AlertFileUpload related called below. 
	 */
	@FXML
	private Button continueButton;

	/*
	 *  All FXML attributes for convert success. 
	 */

	@FXML
	private Button continueButtonConvert;	

	/*
	 * Tab parser
	 */
	private static TabReader reader = new TabReader();

	/*
	 * 
	 *  PrimaryStage Methods related to the FXML documents.  	
	 *
	 */

	@FXML
	void ConvertClicked() {
		outputBox.setDisable(false);
		save.setDisable(false);
		if(convert.getText().equals("Convert") && checkTrue(file) == true) {		
			reader.setInput(inputBox.getText());
			reader.convertTabs();
			outputBox.setText(reader.toMXL());
			displaySuccessConvert();
			save.setVisible(true);
		}

	}

	@FXML
	void SaveClicked() {
		if(startButton.isPressed()== false && file == null) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("NoFileUploaded.fxml"));
				//new stage created with modality(events delivered)
				// all stages created this way
				final Stage popup = new Stage();
				popup.initModality(Modality.APPLICATION_MODAL);
				popup.setTitle("Error");
				popup.setTitle("Success");
				popup.setScene(new Scene(root, 334, 226));
				popup.show();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if( checkTrue(file) == true) {
			try { 
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("musicXML files (*.musicxml)","*.musicxml");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showSaveDialog(stage);
				if(file != null){
					FileWriter myWriter = new FileWriter(file);
					myWriter.write(outputBox.getText());
					myWriter.close();
					//	SaveFile(textInput.getText(), file);
					Alert errorAlert = new Alert(AlertType.INFORMATION); //creates a displayable error allert window 
					errorAlert.setHeaderText("File is saved. Happy making music!"); 
					errorAlert.showAndWait();
				}
			}
			catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}}
	}

	int size =1;
	@FXML
	void dragFile() {
		inputBox.setOnDragOver(e -> { //e -> dictates action needed 
			Dragboard dragBoard = e.getDragboard(); 
			if(dragBoard.hasFiles() && dragBoard.getFiles().size() == size) {

				try {
					Path path = FileSystems.getDefault().getPath(dragBoard.getFiles().get(0).getPath());

					if(Files.probeContentType(path).equals("text")) {

						e.acceptTransferModes(TransferMode.COPY);//copy data 
					}

				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
			else {
				e.consume();
			}
		});
		inputBox.setOnDragDropped(e->{
			Dragboard db = e.getDragboard();
			boolean success = false;
			if(db.hasFiles()) {
				success = true;

				for(File f : db.getFiles()) {
					file = f;
					outputBox.clear();
					inputBox.setText(readFile(file));
					checkTrue(file);
					//step3Label.setVisible(true);
					convert.setDisable(false); 
					featureButton.setDisable(false);

				}
			}
			e.setDropCompleted(success);
			e.consume();
		});

	}

	private String readFile(File file) {
		String fileName = file.getAbsolutePath();

		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text);
				stringBuffer.append("\n"); //makes the input on next line 
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {

		} finally {
			try {
				bufferedReader.close();
			} catch (IOException ex) {
			}
		}

		return stringBuffer.toString();
	}

	@FXML
	public void select() {
		FileChooser fileChooser = new FileChooser(); 
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"); //chooses only txt file 
		fileChooser.setInitialFileName("myfile.txt"); // sets the file name to download 
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(select.getScene().getWindow()); 	
		//String fileName = file.getAbsolutePath();

		if(!(file.length() == 0)) {
			outputBox.clear();
			inputBox.clear();
			inputBox.setText(readFile(file));

			if (checkTrue(file)) {
				reader = new TabReader();
				reader.setFile(file);
			}
			//step3Label.setVisible(true);
			convert.setDisable(false); 
			featureButton.setDisable(false);
		}
		else {
			ErrorOutput(file);
		}
	}

	private boolean checkTrue(File file) {

		if(!(file.length() == 0)) {
			showOtherButtons();
			UploadFileLabel.setText("File Uploaded");

			if (count == 0) {	
				displayErrorPage();
				showOtherButtons();
				count++;
			}
			return true;

		}
		return false;
	}
	private boolean showOtherButtons() {
		if (count>0) {
			convert.setVisible(true);
			featureButton.setVisible(true);
			return true;
		}
		return false;
	}

	private void displayErrorPage(){
		Parent root;
		try {
			// used the same style for all popup windows 
			root = FXMLLoader.load(getClass().getResource("AlertFileUploadSuccess.fxml"));
			Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Error");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();
			count ++;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displaySuccessConvert() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("ConvertSuccess.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			//popup.setTitle("Error");
			popup.setTitle("Success");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void ErrorOutput(File file) {
		Alert errorAlert = new Alert(AlertType.ERROR); //creates a displayable error alert window 
		errorAlert.setHeaderText("Input file is not valid. Please ensure your input file is not empty"); 
		errorAlert.setContentText("Provide text file"); //Shows this stage and waits for it to be hidden (closed) before returning to the caller.
		errorAlert.showAndWait();
	}



	@FXML 
	void startClick() {
		timeSigButton.setVisible(false);
		keyButton.setVisible(false);
		titleButton.setVisible(false);
		composerButton.setVisible(false);
		convert.setVisible(false);
		save.setVisible(false);
		featureButton.setVisible(false);
		UploadFileLabel.setText("No File Uploaded");
		inputBox.clear();
		inputBox.setDisable(false);
		outputBox.clear();
		outputBox.setDisable(true);
		select.setDisable(false);
	
	}


	//	@FXML
	public  void initialize() {
		assert inputBox != null : "fx:id=\"textOutputAreaXML\" was not injected: check your FXML file 'Untitled'.";
		if (inputBox != null) {
			inputBox.setDisable(true);
		}		

		assert outputBox != null : "fx:id=\"textInputFileArea\" was not injected: check your FXML file 'Untitled'.";
		if (outputBox != null) {
			outputBox.setDisable(true);
		}	

		if (select != null) { 
			select.setDisable(true);
		}	
		assert select != null : "fx:id=\"submit\" was not injected: check your FXML file 'Untitled'.";
		assert convert != null : "fx:id=\"convert\" was not injected: check your FXML file 'Untitled'.";
		if (convert != null) { 
			convert.setDisable(true);
		}


		assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'Untitled'.";
		if (save != null) { 
			save.setDisable(true);
		}

		assert helpButton != null : "fx:id=\"helpButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";

		assert timeSigButton != null : "fx:id=\"timeSigButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		if (timeSigButton != null) {
			timeSigButton.setDisable(true);
		}

		assert keyButton != null : "fx:id=\"keyButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		if (keyButton != null) {
			keyButton.setDisable(true);
		}	

		assert titleButton != null : "fx:id=\"titleButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		if (titleButton != null) {
			titleButton.setDisable(true);
		}
		assert composerButton != null : "fx:id=\"composerButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		if (composerButton != null) {
			composerButton.setDisable(true);
		}

		assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert featureButton != null : "fx:id=\"featureButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";

		if (featureButton != null) {
			featureButton.setDisable(true);
		}

	}


	/*
	 * All methods are for help page 
	 */

	@FXML
	void helpclick() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("HelpWindow.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Help");
			popup.setScene(new Scene(root, 600, 340));
			popup.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void closeHelp() {
		closeButton.getScene().getWindow().hide();;
	}


	/*
	 * General Main Page 
	 */
	@FXML
	void selectComposer() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Composer.fxml"));
			final Stage popup = new Stage();
			popup.setTitle("Composer");
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectKey() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Keys.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Key");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectTimeSig() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("TimeSigniture.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Time Signiture");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectTitle() {
		if (select.isVisible() == true) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("Title.fxml"));
				final Stage popup = new Stage();
				popup.initModality(Modality.APPLICATION_MODAL);
				popup.setTitle("Title");
				popup.setScene(new Scene(root, 334, 226));
				popup.show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("skipped homie");
		}
	}

	/*
	 * All methods below are for the time signature page 
	 */
	@FXML
	void cancelTimeSig() {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void saveTimeSigClicked() {
		int[] timeSig = new int[2];
		timeSig[0] = 4;
		timeSig[1] = 4;
		
		try {
			timeSig[0] = Integer.parseInt(beatOption.getText());
			timeSig[1] = Integer.parseInt(beatTimeOption.getText());
		} catch(Exception e) {}
		
		reader.setTimeSignature(timeSig);
		SaveTimeSig.getScene().getWindow().hide();
		
//		System.out.println("beat: " + timeSig[0]);
//		System.out.println("beatTime: " + timeSig[1]);
	}

	@FXML
	void beat1Select(ActionEvent event) {
		beat = 1;
		beat1.setSelected(true);
		beatOption.setText(beat1.getText());
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		beat5.setSelected(false);
		beat6.setSelected(false);
	}
	
	@FXML
	void beat2Select(ActionEvent event) {
		beat1.setSelected(false);
		beat2.setSelected(true);
		beatOption.setText(beat2.getText());
		beat3.setSelected(false);
		beat4.setSelected(false);
		beat5.setSelected(false);
		beat6.setSelected(false);
	}

	@FXML
	void beat3Select(ActionEvent event) {
		beat =3;
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat3.setSelected(true);
		beatOption.setText(beat3.getText());
		beat4.setSelected(false);
		beat5.setSelected(false);
		beat6.setSelected(false);
	}

	@FXML
	void beat4Select(ActionEvent event) {
		beat =4;
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(true);
		beatOption.setText(beat4.getText());
		beat5.setSelected(false);
		beat6.setSelected(false);
	}

	@FXML
	void beat5Select(ActionEvent event) {
		beat = 5;
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		beat5.setSelected(true);
		beatOption.setText(beat5.getText());
		beat6.setSelected(false);
	}

	@FXML
	void beat6Select(ActionEvent event) {
		beat = 6;
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		beat5.setSelected(false);
		beat6.setSelected(true);
		beatOption.setText(beat6.getText());
	}

	@FXML
	void beatTime1(ActionEvent event) {
		beatTime = 1;
		beatTime1.setSelected(true);
		beatTimeOption.setText(beatTime1.getText());
		beatTime2.setSelected(false);
		beatTime3.setSelected(false);
		beatTime4.setSelected(false);
		beatTime5.setSelected(false);
	}

	@FXML
	void beatTime2(ActionEvent event) {
		beatTime =2;
		beatTime1.setSelected(false);
		beatTime2.setSelected(true);
		beatTimeOption.setText(beatTime2.getText());
		beatTime3.setSelected(false);
		beatTime4.setSelected(false);
		beatTime5.setSelected(false);
	}

	@FXML
	void beatTime3(ActionEvent event) {
		beatTime =3;
		beatTime1.setSelected(false);
		beatTime2.setSelected(false);
		beatTime3.setSelected(true);
		beatTimeOption.setText(beatTime3.getText());
		beatTime4.setSelected(false);
		beatTime5.setSelected(false);
	}

	@FXML
	void beatTime4(ActionEvent event) {
		beatTime =4;
		beatTime1.setSelected(false);
		beatTime2.setSelected(false);
		beatTime3.setSelected(false);
		beatTime4.setSelected(true);
		beatTimeOption.setText(beatTime4.getText());
		beatTime5.setSelected(false);
	}

	@FXML
	void beatTime5(ActionEvent event) {
		beatTime = 5;
		beatTime1.setSelected(false);
		beatTime2.setSelected(false);
		beatTime3.setSelected(false);
		beatTime4.setSelected(false);
		beatTime5.setSelected(true);
		beatTimeOption.setText(beatTime5.getText());
	}

	/*
	 * All methods below for title page 
	 */

	@FXML
	void cancelTitle() {
		cancelButtonTitle.getScene().getWindow().hide();;
	}

	@FXML
	void saveTitleClick() {
		SaveTitle.setOnAction( e -> setTitleName(titleTextField.getText()));
		title = titleTextField.getText();
//		System.out.println(title);
		reader.setTitle(title);
		SaveTitle.getScene().getWindow().hide();
		
	}

	private void setTitleName(String title) {
		title = titleTextField.getText();
		this.title = title;
	}
	
	public void returnTitle() {
		System.out.println(title);
	}

	public String getTitle() {
		return title;
	}


	/*
	 * All methods below for composer
	 */

	@FXML
	void cancelComposer() {
		cancelButtonComposer.getScene().getWindow().hide();;
	}

	@FXML
	void saveComposer() {
		ComposerName = composerText.getText();
//		System.out.println(ComposerName);
		reader.setComposer(ComposerName);
		SaveComposer.getScene().getWindow().hide();
	}


	public String getComposerName() {
		return ComposerName;
	}


	/*
	 * All methods for keys 
	 */

	@FXML
	void cancelKey() {
		cancelKey.getScene().getWindow().hide();

	}

	@FXML
	void saveKey() {
		reader.setKey(keyFifths);
		setKey(keySelected);
		saveKey.getScene().getWindow().hide();
		System.out.println(keySelected);
	}

	public String getKey(String key) {
		return keySelected;
	}

	public void setKey(String key) {
		if (key == null) {
			keySelected = "C Minor";
		}
		keySelected = key;
	}

	@FXML
	void chosenKey10(ActionEvent event) {
		keyFifths = 10;
		setKey(key10.getText());
		key10.setSelected(true); // need to add to all. 
		keyOption.setText(key10.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey11(ActionEvent event) {
		keyFifths = 11;
		key11.setSelected(true);
		keyOption.setText(key11.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey12(ActionEvent event) {
		keyFifths = 12;
		key12.setSelected(true);
		keyOption.setText(key12.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey13(ActionEvent event) {
		keyFifths = 13;
		key13.setSelected(true);
		keyOption.setText(key13.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey14(ActionEvent event) {
		keyFifths = 14;
		key14.setSelected(true);
		keyOption.setText(key14.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
	}

	@FXML
	void chosenKey2(ActionEvent event) {
		keyFifths = 2;
		key2.setSelected(true);
		keyOption.setText(key2.getText());
		key1.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey3(ActionEvent event) {
		keyFifths = 3;
		key3.setSelected(true);
		keyOption.setText(key3.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey4(ActionEvent event) {
		keyFifths = 4;
		key4.setSelected(true);
		keyOption.setText(key4.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey5(ActionEvent event) {
		keyFifths = 5;
		key5.setSelected(true);
		keyOption.setText(key5.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey6(ActionEvent event) {
		keyFifths = 6;
		key6.setSelected(true);
		keyOption.setText(key6.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey7(ActionEvent event) {
		keyFifths = 7;
		key7.setSelected(true);
		keyOption.setText(key7.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey8(ActionEvent event) {
		keyFifths = 8;
		key8.setSelected(true);
		keyOption.setText(key8.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenKey9(ActionEvent event) {
		keyFifths = 9;
		key9.setSelected(true);
		keyOption.setText(key9.getText());
		key1.setSelected(false);
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);
	}

	@FXML
	void chosenkey1() {
		keyFifths = 1;
		key1.setSelected(true);
		keyOption.setText(key1.getText());
		key2.setSelected(false);
		key3.setSelected(false);
		key4.setSelected(false);
		key5.setSelected(false);
		key6.setSelected(false);
		key7.setSelected(false);
		key8.setSelected(false);
		key9.setSelected(false);
		key10.setSelected(false);
		key11.setSelected(false);
		key12.setSelected(false);
		key13.setSelected(false);
		key14.setSelected(false);

	}


	/*
	 * Upload File Success Page 
	 */
	
	@FXML
	void continuePage() {
		continueButton.getScene().getWindow().hide();
	}


	@FXML
	private void showFeature() {
		timeSigButton.setDisable(false);
		timeSigButton.setVisible(true);
		keyButton.setDisable(false);
		keyButton.setVisible(true);
		titleButton.setDisable(false);
		titleButton.setVisible(true);
		composerButton.setDisable(false);
		composerButton.setVisible(true);


	}

	/*
	 * Converted File Success
	 */
	@FXML
	void ContinueToSave() {
		continueButtonConvert.getScene().getWindow().hide();
	}

}

