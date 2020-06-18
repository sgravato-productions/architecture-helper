package org.architecture_helper.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Main extends JFrame {
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final JButton runButton;

   	public Main() {
   		//region FRAME
      	super("Architecture Helper");
		setSize(950, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationByPlatform(true);

		setVisible(true);
		//endregion

		//region BUILDING GUI
		//setting the content pane layout
		Container contentPane = getContentPane();
      	contentPane.setLayout(new BorderLayout());

      	//creating the tab control and the tabs
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Pipeline Drawer", new PipelineDrawerTab());
		tabbedPane.addTab("Address Hit / Miss", new AddressHitAndMissTab());
		tabbedPane.addTab("TLB Cache Calc", new TLBCacheCalculator());

		//creating the run button
		runButton = new JButton("Run");
		contentPane.add(runButton, BorderLayout.SOUTH);
		//endregion

		revalidate();
		repaint();

		//region HOOKING EVENTS
		runButton.addActionListener(runButtonEvent);
		//endregion
   	}

   	public final ActionListener runButtonEvent = event ->{
		((RunnableTab)tabbedPane.getSelectedComponent()).run();
	};

    public static void main (String[] args) {
    	new Main();
  	}
}