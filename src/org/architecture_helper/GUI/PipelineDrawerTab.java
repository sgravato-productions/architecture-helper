package org.architecture_helper.gui;

import javax.swing.*;
import java.awt.*;

public class PipelineDrawerTab extends RunnableTab{
	JLabel lblSource;
	JLabel lblResult;
	JTextArea taSourceCode;
	JTextArea taResult;

	public PipelineDrawerTab() {
		lblSource = new JLabel("Source:");
		lblResult = new JLabel("Result:");
		taSourceCode = new JTextArea(20, 5);
		taResult = new JTextArea(20, 5);

		setLayout(new GridLayout(5, 1));

		add(lblSource);
		add(taSourceCode);
		add(lblResult);
		add(taResult);
	}

	@Override
	public void run() {
		System.out.println("STARTING PIPELINE DRAW");
	}
}
