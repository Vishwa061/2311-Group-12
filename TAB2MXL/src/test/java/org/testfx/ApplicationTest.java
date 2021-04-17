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
	void should_contain_translate_button(FxRobot robot) {
		FxAssert.verifyThat("#convert", LabeledMatchers.hasText("Convert"));
	}
	
    @Test
	void should_contain_button_with_text(FxRobot robot) {
	    FxAssert.verifyThat("#startButton", LabeledMatchers.hasText("Start"));
    }
    @Test
  	void should_contain_button_with_help(FxRobot robot) {
   	    FxAssert.verifyThat("#helpButton", LabeledMatchers.hasText("Help"));
       }
	    
}