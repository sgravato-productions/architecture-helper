package org.architecture_helper.gui;

import org.architecture_helper.backend.Instruction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PipelineDrawerTab extends RunnableTab{
	//region GUI
	private final String CODE = "lw s3, 4(s3)\n" +
			"add s3, s0, s1\n" +
			"lw t4, 8(s3)\n" +
			"sw t4, 8(t1)\n" +
			"add t7, t4, t1\n" +
			"lw t2, 8(t7)\n" +
			"add t2, t2, t1\n" +
			"add t2, t2, t7";

	JTextArea taSourceCode;
	JTextArea taResult;

	//endregion

	//region Functionality
	private final String spaces = "     ";
	private final String instructionSpaces = new String(new char[extendInstruction("").length()]).replace("\0", " ");

	private String currentLine = "";
	private String dependencyLine = "";
	//endregion


	public PipelineDrawerTab() {
		setLayout(new BorderLayout());

		//region creating top panel
		JPanel topPanel = new JPanel(new BorderLayout());

		topPanel.add(new JLabel("Source:"), BorderLayout.NORTH);

		taSourceCode = new JTextArea(CODE);
		taSourceCode.setFont(Main.MONOSPACED);
		topPanel.add(new JScrollPane(taSourceCode), BorderLayout.CENTER);
		//endregion

		//region creating bottom panel
		JPanel botPanel = new JPanel(new BorderLayout());

		botPanel.add(new JLabel("Result:"), BorderLayout.NORTH);

		taResult = new JTextArea();
		taResult.setFont(Main.MONOSPACED);
		botPanel.add(new JScrollPane(taResult), BorderLayout.CENTER);
		//endregion

		//region creating split pane
		JSplitPane topBotSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, botPanel);
		add(topBotSplit, BorderLayout.CENTER);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				topBotSplit.setDividerLocation(250);
			}
		});
		//endregion

	}

	@Override
	public void run() {
		List<Instruction> instructions = new ArrayList<>();

		for(String line : taSourceCode.getText().split("\n")) {
			line = line.trim();
			if ( line.length() == 0 || line.charAt(0) == '#') continue;

			instructions.add(new Instruction(line));
		}

		Instruction prevInstruction = null;
		int indentation = 0;
		for(Instruction instruction : instructions) {
			currentLine = "";
			dependencyLine = "";

			boolean forwarding = prevInstruction != null && (instruction.regRead && prevInstruction.regWrite && instruction.rs.contains(prevInstruction.rd));
			boolean stall = forwarding && prevInstruction.memRead;

			square("IF");
			if (stall) {
				square("ID", null, "<>");
			}
			square("ID");
			square("EX", forwarding ? "\\" : null);
			square("M ");
			square("WB", false);

			String indentationSpaces = new String(new char[indentation]).replace("\0", spaces);

			output(instructionSpaces + indentationSpaces + dependencyLine);
			output(extendInstruction(instruction.name) + indentationSpaces + currentLine);

			prevInstruction = instruction;
			indentation += stall ? 2 : 1;
		}

	}

	private void square(String phase){
		square(phase, null);
	}
	private void square(String phase, boolean separator){
		square(phase, null, separator);
	}

	private void square(String phase, String dependencies){
		square(phase, dependencies, "||", true);
	}
	private void square(String phase, String dependencies, boolean separator){
		square(phase, dependencies, "||", separator);
	}

	private void square(String phase, String dependencies, String square){
		square(phase, dependencies, square, true);
	}
	private void square(String phase, String dependencies, String square, boolean separator) {
		currentLine += "" + square.charAt(0) + phase + square.charAt(1) + (separator ? '-':' ');
		if(dependencies != null){
			dependencyLine = dependencyLine.substring(0,dependencyLine.length()-1) + dependencies;
		}else{
			dependencyLine += spaces;
		}

	}

	private void output(String output) {
		output(output, "\r\n");
	}
	private void output(String output, String end) {
		taResult.setText(taResult.getText() + output + end);
	}

	private String extendInstruction(String instruction) {
		return extendInstruction(instruction, 5);
	}

	private String extendInstruction(String instruction, int len) {
		return new String(new char[len - instruction.length()]).replace("\0", ":" + " ");
	}
}
