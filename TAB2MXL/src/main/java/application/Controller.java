
package application;

import java.io.BufferedReader;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.transform.stream.StreamResult;
import java.lang.Object;
import TAB2MXL.TabReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.stage.Window;
import java.awt.Desktop;
import java.awt.TextField;
import java.awt.datatransfer.SystemFlavorMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import TAB2MXL.TabReader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Popup;

public class Controller {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea textOutputAreaXML;

	@FXML
	private TextArea textInputFileArea;//outputbox

	@FXML
	private Button select;

	@FXML
	private Button convert;

	@FXML
	private Button save, featureButton, startButton;

	/*
	 * Below this are help button fxml 
	 */
	@FXML
	private Button helpButton;

	@FXML
	private Button timeSigButton;

	@FXML
	private Button keyButton;

	@FXML
	private Button titleButton;

	@FXML
	private Button composerButton;

	@FXML
	private Button closeButton;

	@FXML 
	private TextArea helpTextArea;

	/*
	 * Below is time sig 
	 */
	@FXML
	private Button cancelButton;

	@FXML
	private Button SaveTimeSig;

	/*
	 * Below is fxml for Title 
	 */

	@FXML
	private Button cancelButtonTitle;

	@FXML
	private Button SaveTitle;

	/*
	 * Below fxml for Composer
	 */

	@FXML
	private Button cancelButtonComposer;

	@FXML
	private Button SaveComposer;

	/*
	 * below for keys 
	 */

	@FXML
	private Button cancelKey;

	@FXML
	private Button saveKey;
	
	
	/*
	 * Below for alertFileUploadSuccess
	 */
	@FXML
	    private Button continueButton;
	
	/*
	 * Below for convert success
	 */
	 @FXML
	    private Button continueButtonConvert;	
	TabReader outputXMLFile;

	private Window stage;
	BufferedReader input;
	StreamResult output;

	@FXML
	private File file; //needed

	@FXML
	void ConvertClicked(ActionEvent event) {
		if(convert.getText().equals("Convert") && checkTrue(file) == true) {
			System.out.println("yas");
			//reads the file provided thr
			TabReader reader = new TabReader();
			reader.setInput(file);
			reader.convertTabs();
			System.out.println(reader.toMXL());
			textInputFileArea.appendText(reader.toMXL());
			displaySuccessConvert();
			save.setVisible(true);
		}
		else  {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("musicXML files (*.musicxml)", "*.musicxml");
			fileChooser.getExtensionFilters().add(extFilter);
			File savefile = fileChooser.showSaveDialog(convert.getScene().getWindow());
			System.out.println("Successfully wrote to the file.");
		}
	}

	@FXML
	void SaveClicked(ActionEvent event) {
		try { 
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("musicXML files (*.musicxml)","*.musicxml");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(stage);
			if(file != null){
				FileWriter myWriter = new FileWriter(file);
				myWriter.write(textInputFileArea.getText());
				myWriter.close();
				//	SaveFile(textInput.getText(), file);


				Alert errorAlert = new Alert(AlertType.INFORMATION); //creates a displayable error allert window 
				errorAlert.setHeaderText("File is saved. Happy making music!"); 
				errorAlert.showAndWait();
			}
		}
		catch (IOException ex) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	void dragFile() {
		textOutputAreaXML.setOnDragOver(e -> { //e -> dictates action needed 
			Dragboard db = e.getDragboard(); 
			if(db.hasFiles() && db.getFiles().size() == 1) {

				try {
					Path path = FileSystems.getDefault().getPath(db.getFiles().get(0).getPath());
					if(Files.probeContentType(path).equals("text/plain")) {
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
		textOutputAreaXML.setOnDragDropped(e->{
			Dragboard db = e.getDragboard();
			boolean success = false;
			if(db.hasFiles()) {
				success = true;

				for(File f : db.getFiles()) {
					file = f;
					textInputFileArea.clear();
					textOutputAreaXML.setText(readFile(file));
					textOutputAreaXML.clear();
					checkTrue(file);
				}
			}
			e.setDropCompleted(success);
			e.consume();
		});

	}


	@FXML
	void select() {
		FileChooser fileChooser = new FileChooser(); //enables the user to select one or more files via a file explorer from the user's local computer
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"); //chooses only txt file 
		fileChooser.setInitialFileName("myfile.txt"); // sets the file name to download 
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(select.getScene().getWindow()); 	

		if(!(file.length() == 0)) {
			textInputFileArea.clear();
			textOutputAreaXML.clear();
			checkTrue(file);
		}
		else {
			ErrorOutput(file);
		}
	}
	
	 static int count = 0;
	private boolean checkTrue(File file) {
		if(!(file.length() == 0)) {
		//	counter(count);
			textOutputAreaXML.setText(readFile(file));
			textOutputAreaXML.setWrapText(true);
			showOtherButtons();
			
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
				
				root = FXMLLoader.load(getClass().getResource("AlertFileUploadSuccess.fxml"));
			    Stage popup = new Stage();
				popup.initModality(Modality.APPLICATION_MODAL);
				popup.setTitle("Help");
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
			popup.setTitle("Tranlation Options");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private String readFile(File file){

		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text);
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



	private void ErrorOutput(File file) {
		Alert errorAlert = new Alert(AlertType.ERROR); //creates a displayable error alert window 
		errorAlert.setHeaderText("Input file is not valid. Please ensure your input file is not empty"); 
		errorAlert.setContentText("Provide text file"); //Shows this stage and waits for it to be hidden (closed) before returning to the caller.
		errorAlert.showAndWait();
	}


    @FXML 
    void startClick(){
    	timeSigButton.setVisible(false);
    	keyButton.setVisible(false);
    	titleButton.setVisible(false);
    	composerButton.setVisible(false);
    	convert.setVisible(false);
    	save.setVisible(false);
    	featureButton.setVisible(false);
    }
    
/*	@FXML
    void initialize() {
		assert textOutputAreaXML != null : "fx:id=\"textOutputAreaXML\" was not injected: check your FXML file 'Untitled'.";
		assert textInputFileArea != null : "fx:id=\"textInputFileArea\" was not injected: check your FXML file 'Untitled'.";
		assert select != null : "fx:id=\"submit\" was not injected: check your FXML file 'Untitled'.";
		assert convert != null : "fx:id=\"convert\" was not injected: check your FXML file 'Untitled'.";
		assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'Untitled'.";
		assert helpButton != null : "fx:id=\"helpButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert timeSigButton != null : "fx:id=\"timeSigButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert keyButton != null : "fx:id=\"keyButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert titleButton != null : "fx:id=\"titleButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert composerButton != null : "fx:id=\"composerButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
		assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'PrimaryStage.fxml'.";
}*/
	

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
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Tranlation Options");
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
			popup.setTitle("Tranlation Options");
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
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Title.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Tranlation Options");
			popup.setScene(new Scene(root, 334, 226));
			popup.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * All methods below are for the time page 
	 */
	@FXML
	void cancelTimeSig() {
		cancelButton.getScene().getWindow().hide();;
	}

	@FXML
	void saveTimeSigClicked() {
		SaveTimeSig.getScene().getWindow().hide();;
	}

	/*
	 * All methods below for title page 
	 */

	@FXML
	void cancelTitle() {
		cancelButtonTitle.getScene().getWindow().hide();;
	}

	@FXML
	void saveInputTitle(ActionEvent event) {

	}

	@FXML
	void saveTitleClick() {
		SaveTitle.getScene().getWindow().hide();;
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
		SaveComposer.getScene().getWindow().hide();;
	}

	@FXML
	void saveInputComposer(ActionEvent event) {

	}

	/*
	 * All methods for keys 
	 */
	
	@FXML
	void cancelKey() {
		cancelKey.getScene().getWindow().hide();;
		
	}

	@FXML
	void saveKey() {
		saveKey.getScene().getWindow().hide();;
	}
	
	/*
	 * Upload File Success Page 
	 */
	int a=0;
	@FXML
	void continuePage() {
		continueButton.getScene().getWindow().hide();
		
	}
	
	
	@FXML
	private void showFeature() {
		timeSigButton.setVisible(true);
    	keyButton.setVisible(true);
        titleButton.setVisible(true);
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
