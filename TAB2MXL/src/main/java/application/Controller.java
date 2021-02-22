
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.stage.Window;
import java.awt.Desktop;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private Button save;

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
			TabReader reader = new TabReader(file);
			System.out.println(reader.toMXL());
			textInputFileArea.appendText(reader.toMXL());

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

	private boolean checkTrue(File file) {
		if(!(file.length() == 0)) {
			textOutputAreaXML.setText(readFile(file));
			textOutputAreaXML.setWrapText(true);
			return true;
		}
		return false;
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
	void initialize() {
		assert textOutputAreaXML != null : "fx:id=\"textOutputAreaXML\" was not injected: check your FXML file 'Untitled'.";
		assert textInputFileArea != null : "fx:id=\"textInputFileArea\" was not injected: check your FXML file 'Untitled'.";
		assert select != null : "fx:id=\"submit\" was not injected: check your FXML file 'Untitled'.";
		assert convert != null : "fx:id=\"convert\" was not injected: check your FXML file 'Untitled'.";
		assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'Untitled'.";

	}
}

