package org.architecture_helper.gui;

import org.architecture_helper.backend.CacheSimulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AddressHitAndMissTab extends RunnableTab {
	//Constants
	private final String DEFAULT_ADDRESSES = "[24, 64, 164, 32, 208, 128, 44, 192, 432, 452, 88, 212, 504, 384, 32, 52, 292, 232, 388, 400, 404, 288, 40, 376]";

	//Class fields
	public JTextArea taResult;

	public JTextField addressesTextBox;
	public JSpinner cacheTypeSpinner;
	public JSpinner cacheSetsSpinner;
	public JSpinner blockSizeSpinner;


	public AddressHitAndMissTab() {
		setLayout(new BorderLayout());

		//region Input panel
		JPanel topPanel = new JPanel(new GridBagLayout());
		add(topPanel, BorderLayout.NORTH);

		topPanel.setBorder(BorderFactory.createTitledBorder("Input:"));

		//setting constraints for the first column
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;

		topPanel.add(new JLabel("Addresses:"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Cache Type (ways):"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Cache Sets:"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Block size (byte):"), constraints);

		//setting constraints for the second column
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;

		addressesTextBox = new JTextField(DEFAULT_ADDRESSES);
		topPanel.add(addressesTextBox, constraints);

		constraints.gridy++;
		cacheTypeSpinner = new JSpinner(new SpinnerNumberModel(2,1,Integer.MAX_VALUE,1));
		((JSpinner.DefaultEditor)cacheTypeSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
		topPanel.add(cacheTypeSpinner, constraints);

		constraints.gridy++;
		cacheSetsSpinner = new JSpinner(new SpinnerNumberModel(8,1,Integer.MAX_VALUE,1));
		((JSpinner.DefaultEditor)cacheSetsSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
		topPanel.add(cacheSetsSpinner, constraints);

		constraints.gridy++;
		blockSizeSpinner = new JSpinner(new SpinnerNumberModel(32,1,Integer.MAX_VALUE,1));
		((JSpinner.DefaultEditor)blockSizeSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
		topPanel.add(blockSizeSpinner, constraints);


		add(topPanel, BorderLayout.NORTH);

		//endregion

		//region Result panel
		JPanel botPanel = new JPanel(new BorderLayout());
		botPanel.setBorder(BorderFactory.createTitledBorder("Result:"));
		add(botPanel, BorderLayout.CENTER);

		taResult = new JTextArea();
		taResult.setFont(Main.MONOSPACED);
		botPanel.add(taResult, BorderLayout.CENTER);
		//endregion

	}

	@Override
	public void run() {
		outputClear();

		//input data
		int cache_type = (Integer)cacheTypeSpinner.getValue();
		int cache_sets = (Integer)cacheSetsSpinner.getValue();
		int cache_block_size = (Integer)blockSizeSpinner.getValue();

		List<Integer> addresses = formatAddressList(addressesTextBox.getText());

		//preparing for simulation
		CacheSimulator cache = new CacheSimulator(cache_sets, cache_block_size,cache_type);

		int hits = 0, misses = 0;

		boolean hit;
		int memBlock, cacheBlock, wayIndex;
		output("ADDRESS\tBLOCK\tSET\tHIT/MISS");

		//running simulation
		for (int address : addresses) {
			Object[] findResult = cache.find(address);
			hit = (boolean)findResult[0];
			memBlock = (int)findResult[1];
			cacheBlock = (int)findResult[2];
			wayIndex = (int)findResult[3];
			if (hit) {
				hits++;
			} else {
				misses++;
			}

			String wayIndexStr = cache_type == 1 ? "" : ("["+wayIndex+"]");
			output(""+address+"\t"+memBlock+"\t"+cacheBlock+wayIndexStr+"\t"+(hit ? "Hit" : "Miss"));
		}

		output("HITS: "+hits +" MISSES: "+misses+"");
	}

	private void outputClear() {
		taResult.setText("");
	}

	private void output(String text) {
		output(text, "\r\n");
	}

	private void output(String text, String end) {
		taResult.setText(taResult.getText() + text + end);
	}

	List<Integer> formatAddressList(String addressListString) {
		List<Integer> addresses = new ArrayList<Integer>();
		boolean lastWasNumeric = false;
		String buffer = "";

		for (char character : addressListString.toCharArray()) {
			if (Character.isDigit(character)){
				buffer += character;
				lastWasNumeric = true;
			} else {
				if (lastWasNumeric) {
					addresses.add(Integer.parseInt(buffer));
					buffer = "";
				}
				lastWasNumeric = false;
			}
		}

		if (lastWasNumeric) {
			addresses.add(Integer.parseInt(buffer));
		}

		return addresses;
	}

}
