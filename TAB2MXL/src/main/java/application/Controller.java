package application;

import java.awt.Desktop;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Action;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import TAB2MXL.ReadFile;
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
    private Button submit; //select button 

    @FXML
    private Button convert; //indication to convert
    
    @FXML 
    private TextArea textInput;
    
    @FXML
    private File file;
    
    @FXML 
    private Button Save;

   TabReader outputXMLFile;

   private Window stage;
   BufferedReader input;
   StreamResult output;
	
	
    /*
     * Goal: Get the input file and give the output 
     */
    @FXML
    void ConvertClicked() throws IOException {
    	if(!textInput.getText().isEmpty() && convert.getText().equals("Convert")) {
    		
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
    

	
    public static String stringParse(String text) {
		// TODO Auto-generated method stub
    	return "This is a test.";
	}


	@FXML
    void submitClick() {
    	FileChooser fileChooser = new FileChooser(); //enables the user to select one or more files via a file explorer from the user's local computer

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"); //chooses only txt file 
		fileChooser.setInitialFileName("myfile.txt"); // sets the file name to download 
		fileChooser.getExtensionFilters().add(extFilter);

		file = fileChooser.showOpenDialog(submit.getScene().getWindow());
    	
		/*
		 * Verifies not an empty file is attached 
		 */
		if(!(file.length() == 0)) {
            System.out.println("get the text file on text area");
        }
		else {
			ErrorOutput(file);
		}
	}
	

	private void ErrorOutput(File file) {
		Alert errorAlert = new Alert(AlertType.ERROR); //creates a displayable error allert window 
		errorAlert.setHeaderText("Input not valid, cannot be empty"); 
		errorAlert.setContentText("Provide text file"); //Shows this stage and waits for it to be hidden (closed) before returning to the caller.
		errorAlert.showAndWait();
	}
	
	
	
	/*
	 * SAVE BUTTON: Takes the text area file, and saves it! 
	 */
	
	public void SaveClicked() {
	            FileChooser fileChooser = new FileChooser();
	            //Set extension filter
	            FileChooser.ExtensionFilter extFilter = 
	                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	            fileChooser.getExtensionFilters().add(extFilter);
	            //Show save file dialog
	            File file = fileChooser.showSaveDialog(stage);
	            if(file != null){
	                SaveFile(textInput.getText(), file);
	            }
	      
	}

    private void SaveFile(String text, File file2) {
		// TODO Auto-generated method stub
    	 try {
             FileWriter fileWriter;
              
             fileWriter = new FileWriter(file);
             fileWriter.write(text);
             fileWriter.close();
         } catch (IOException ex) {
             Logger.getLogger(Controller.class
                 .getName()).log(Level.SEVERE, null, ex);
         }
	}

    /*
     * Drag and drop file methods below listed 
     */

    void dragFile() {
    	textInput.setOnDragOver(e -> { //e -> dictates action needed 
			Dragboard db = e.getDragboard(); 
			if(db.hasFiles() && db.getFiles().size() == 1) {
				
				try {
					Path path = FileSystems.getDefault().getPath(db.getFiles().get(0).getPath());
					if(Files.probeContentType(path).equals("text/plain")) {
						e.acceptTransferModes(TransferMode.COPY);//copy data 
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				e.consume();
			}
		});
		textInput.setOnDragDropped(e->{
			Dragboard db = e.getDragboard();
			boolean success = false;
			if(db.hasFiles()) {
				success = true;
				
				for(File f : db.getFiles()) {
					file = f;
				}
			}
			e.setDropCompleted(success);
			e.consume();
		});

	}

}