package org.architecture_helper.gui;

import org.architecture_helper.backend.Instruction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PipelineDrawerTab extends RunnableTab{
	//region
	JTextArea taSourceCode;
	JTextArea taResult;

	private final Font textAreasFont = new Font("monospaced", Font.PLAIN, 12);
	//endregion

	public PipelineDrawerTab() {
		setLayout(new BorderLayout());

		//region creating top panel
		JPanel topPanel = new JPanel(new BorderLayout());

		topPanel.add(new JLabel("Source:"), BorderLayout.NORTH);

		taSourceCode = new JTextArea();
		taSourceCode.setFont(textAreasFont);
		topPanel.add(new JScrollPane(taSourceCode), BorderLayout.CENTER);
		//endregion

		//region creating bottom panel
		JPanel botPanel = new JPanel(new BorderLayout());

		botPanel.add(new JLabel("Result:"), BorderLayout.NORTH);

		taResult = new JTextArea();
		taResult.setFont(textAreasFont);
		botPanel.add(new JScrollPane(taResult), BorderLayout.CENTER);
		//endregion

		//region creating split pane
		JSplitPane topBotSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, botPanel);
		add(topBotSplit, BorderLayout.CENTER);

		//setting split pane to 0.5
		topBotSplit.setResizeWeight(0.5);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				topBotSplit.setDividerLocation(0.5);
			}
		});
		//endregion

	}

	@Override
	public void run() {

	}
}
