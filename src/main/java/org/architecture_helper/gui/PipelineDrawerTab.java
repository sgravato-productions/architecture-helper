package org.architecture_helper.gui;

import org.architecture_helper.backend.Instruction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

	private ArrayList<String> lines = new ArrayList<>();
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
		//clearing previous execution data
		lines.clear();
		taResult.setText("");

		//region parsing instructions
		List<Instruction> instructions = new ArrayList<>();

		for(String line : taSourceCode.getText().split("\n")) {
			line = line.trim();
			if ( line.length() == 0 || line.charAt(0) == '#') continue;

			instructions.add(new Instruction(line));
		}

		//endregion

		LinkedList<Instruction> prevInstructions = new LinkedList<>();
		prevInstructions.add(null);
		prevInstructions.add(null);

		boolean previousStall = false;

		int indentation = 0;
		for(Instruction instruction : instructions) {
			String indentationSpaces = new String(new char[indentation]).replace("\0", spaces);

			lines.add(instructionSpaces + indentationSpaces);
			lines.add(extendInstruction(instruction.name) + indentationSpaces);

			boolean forwardingMemToEx = instruction.checkDependency(prevInstructions.get(0));
			boolean forwardingWbToEx = instruction.checkDependency(prevInstructions.get(1));

			boolean stall = /*!previousStall &&*/ forwardingMemToEx && prevInstructions.get(0).memRead;

			square("IF");
			if (stall) {
				square("ID", "<>");
			}
			square("ID");
			if(forwardingMemToEx){
				lines.set(lines.size()-2, lines.get(lines.size()-2) + "\\");
			} else if(forwardingWbToEx){
				int slashPosition = lines.get(lines.size()-2).length() -1;
				lines.set(lines.size()-1, insertChatAt(slashPosition, lines.get(lines.size()-1), '\\'));
				lines.set(lines.size()-2, insertChatAt(slashPosition, lines.get(lines.size()-2), '|'));
				lines.set(lines.size()-3, insertChatAt(slashPosition, lines.get(lines.size()-3), '|'));
				lines.set(lines.size()-4, insertChatAt(slashPosition, lines.get(lines.size()-4), '|'));
				lines.set(lines.size()-5, insertChatAt(slashPosition, lines.get(lines.size()-5), '\\'));
			}

			square("EX");
			square("M ");
			square("WB", false);

			//preparing data for next iteration
			prevInstructions.add(0,instruction);
			prevInstructions.removeLast();
			previousStall = stall;

			indentation += stall ? 2 : 1;
		}

		//outputting lines to taResult
		taResult.setText(String.join("\r\n", lines));

	}

	private String insertChatAt(int slashPosition, String line, char character) {
		//inserting \ in the middle of the string
		if (line.length() > slashPosition){
			return line.substring(0,slashPosition) + character + line.substring(slashPosition+1);
		}else if(line.length() < slashPosition){
			//extending the line of slashPosition - line.length() spaces
			line += new String(new char[slashPosition - line.length()]).replace(' ',' ');
		}
		return  line + character;
	}

	private void square(String phase){
		square(phase, "||");
	}
	private void square(String phase, boolean separator){
		square(phase, "||", separator);
	}

	private void square(String phase, String square){
		square(phase, square, true);
	}
	private void square(String phase, String square, boolean separator) {
		//drawing current line
		String pipelineStage = "" + square.charAt(0) + phase + square.charAt(1) + (separator ? '-':' ');
		lines.set(lines.size()-1, lines.get(lines.size()-1) + pipelineStage);

		//drawing previous line (dependencies)
		lines.set(lines.size()-2, lines.get(lines.size()-2) + spaces);

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
		return instruction + ":" + new String(new char[len - instruction.length()]).replace("\0", " ");
	}
}
