package application;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

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
public class Controller {

    @FXML
    private Button submit;

    @FXML
    private Button convert;
    
    @FXML 
    private TextArea textInput;
    
    @FXML
    private File file;

    public static final String xmlFilePath = "C:\\Users\\xmlfile.musicxml";

	private Desktop dt = Desktop.getDesktop(); //User Desktop 
	BufferedReader input;
	StreamResult output;
	TransformerHandler th;
	File fi;

	private Window primaryStage;

	private FileChooser fc;
	
	
	private boolean accept(File filename) { //Helper Method to determine if a file is a textfile
		if(filename.getName().endsWith(".txt")) { 
			return true;
		}
		else {
			Alert errorAlert = new Alert(AlertType.ERROR); //creates a displayable error allert window 
			errorAlert.setHeaderText("Input not valid"); 
			errorAlert.setContentText("Provide text file"); //Shows this stage and waits for it to be hidden (closed) before returning to the caller.
			errorAlert.showAndWait();
			return false;
		}
	}
	
	
	
    @FXML
    void ConvertClicked() {
    	System.out.println("UGH");
    }
    
	
    @FXML
    void submitClick() {
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Tab File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(submit.getScene().getWindow());
		
		
    }
    
    @FXML
    void dragFile() {
    	System.out.println("UGH");
	}

}