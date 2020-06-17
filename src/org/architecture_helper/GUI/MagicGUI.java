package org.architecture_helper.gui;

import org.architecture_helper.gui.PipelineDrawerTab;

import java.awt.*;
import javax.swing.*;


public class MagicGUI {
	final static String PIPELINE = "Pipeline Drawer";
    final static String ADDRESSHM = "Address Hit / Miss";


	public static void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
 
        tabbedPane.addTab(PIPELINE, new PipelineDrawerTab());
        tabbedPane.addTab(ADDRESSHM, new AddressHitAndMissTab());
 
        pane.add(tabbedPane, BorderLayout.CENTER);
    }        

	// components
   	private static void createAndShowGui() {
      	JFrame frame = new JFrame("architecture-helper");

		//Create and set up the content pane.
        addComponentToPane(frame.getContentPane());
        
      	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	frame.pack();
      	frame.setLocationByPlatform(true);
      	frame.setVisible(true);
      	frame.setSize(950, 700);
   	}

    public static void main (String[] args) {
    	createAndShowGui();
  	}
}