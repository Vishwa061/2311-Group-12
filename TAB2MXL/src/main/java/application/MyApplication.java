package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MyApplication implements ActionListener {
	
	
	JButton submit;
	
	public MyApplication() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(750,650);
		frame.getContentPane().setBackground(new Color(222,185,185));
		frame.setVisible(true);
		
		
		JPanel panel = new JPanel();
		JPanel innerPanel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(31,150,638,350);
		panel.setBackground(new Color(0xFFFFFF));
		
		
		innerPanel.setBounds(29, 59, 567, 253);
		innerPanel.setBorder(BorderFactory.createDashedBorder(new Color(0x000000), 10, 10));
		
		JLabel title = new JLabel("Convertron");
		title.setFont(new Font("Righteous", Font.BOLD, 60));
		title.setBounds(43,28, 367, 50);
		
		JLabel innerSubtitle = new JLabel("Click the button below to select your file.");
		innerSubtitle.setBounds(100, 100, 750, 25);
		innerSubtitle.setFont(new Font("Righteous", Font.BOLD, 20));
		
		JLabel copyright = new JLabel("Copright 2021 @Convertron-Group-12");
		title.setFont(new Font("Ariel", Font.BOLD, 60));
		copyright.setBounds(160,750,367,46);
		
		submit = new JButton("Choose File");
		submit.setFont(new Font("Seriel", Font.BOLD, 20));
		submit.setBounds(179, 200, 224, 46);
//		submit.setBorder(new RoundBorder(20));
		submit.setBackground(new Color(0xFFFFFF));
		submit.addActionListener(this);
		submit.setFocusable(false);
		
		
		innerPanel.setLayout(null);
		innerPanel.add(submit);
		innerPanel.add(innerSubtitle);
		
		frame.add(panel);
		frame.add(title);
		frame.add(copyright);
		panel.add(innerPanel);
	}

/*
 * Method recognizes that when submit button is clicked, 
 * the computer file system must open 
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource() == submit) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			chooser.setDialogTitle("Select the tab text file");
			
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			System.out.println(f);
		}
	}
	
}