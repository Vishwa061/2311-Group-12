package org.testfx;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class ApplicationTest {
	
	
	@Start
     private void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/PrimaryStage.fxml"));
		primaryStage.setTitle("TAB2XML");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
    	catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Test if convert button is present
	 */
	@Test
	void should_contain_button_with_convert(FxRobot robot) {
		FxAssert.verifyThat("#convert", LabeledMatchers.hasText("Convert"));
	}
	/*
	 * Check presence of start button 
	 */
    @Test
	void should_contain_button_with_start(FxRobot robot) {
	    FxAssert.verifyThat("#startButton", LabeledMatchers.hasText("Start"));
    }
    /*
     * Check presence of help button
     */
    @Test
 	void should_contain_button_with_help(FxRobot robot) {
   	    FxAssert.verifyThat("#helpButton", LabeledMatchers.hasText("Help"));
    }
    /*
     * Check presence of save button
     */
    @Test 
    void should_contain_button_with_save(FxRobot robot) {
    	 FxAssert.verifyThat("#save", LabeledMatchers.hasText("Save"));
    }
    /*
     * Check presence of select button
     */
    @Test 
    void should_contain_button_with_select(FxRobot robot) {
    	 FxAssert.verifyThat("#select", LabeledMatchers.hasText("Select File"));
    }
    /*
     * Check presence of feature button
     */
    @Test 
    void should_contain_button_with_feature(FxRobot robot) {
    	 FxAssert.verifyThat("#featureButton", LabeledMatchers.hasText("Show More Features"));
    }
    /*
     * Check presence of time signiture button
     */
    @Test 
    void should_contain_button_with_timeSigButton(FxRobot robot) {
    	 FxAssert.verifyThat("#timeSigButton", LabeledMatchers.hasText("Select Time Signiture"));
    }
    /*
     * Check presence of key button
     */
    @Test 
    void should_contain_button_with_keyButton(FxRobot robot) {
    	 FxAssert.verifyThat("#keyButton", LabeledMatchers.hasText("Select Key"));
    }
    /*
     * Check presence of title button
     */
    @Test 
    void should_contain_button_with_titleButton(FxRobot robot) {
    	 FxAssert.verifyThat("#titleButton", LabeledMatchers.hasText("Specify Title "));
    }
    /*
     * Check presence of composer button
     */
    @Test 
    void should_contain_button_with_composerButton(FxRobot robot) {
    	 FxAssert.verifyThat("#composerButton", LabeledMatchers.hasText("Specify Composer"));
    }
    

	    
	    
}