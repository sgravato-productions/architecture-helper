package org.architecture_helper.gui;

import javax.swing.*;
import java.awt.*;

public class PipelineDrawerTab extends RunnableTab{
	JTextArea taSourceCode;
	JTextArea taResult;

	public PipelineDrawerTab() {
		taSourceCode = new JTextArea(20, 5);
		taResult = new JTextArea(20, 5);

		setLayout(new GridLayout(5, 1));

		add(new JLabel("Source:"));
		add(taSourceCode);
		add(new JLabel("Result:"));
		add(taResult);
	}

	@Override
	public void run() {
		System.out.println("STARTING PIPELINE DRAW");
	}
}
