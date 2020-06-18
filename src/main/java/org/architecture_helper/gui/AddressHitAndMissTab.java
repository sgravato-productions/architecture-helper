package org.architecture_helper.gui;

import javax.swing.*;
import java.awt.*;

public class AddressHitAndMissTab extends RunnableTab {
	//Constants
	private final String DEFAULT_ADDRESSES = "36, 120, 48, 100, 4, 92, 84, 28, 76, 92, 64, 0, 96, 28, 32, 52, 92, 72, 16, 72, 124, 108, 64, 88";

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

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.ipadx = 10;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;

		constraints.gridy = 0;
		topPanel.add(new JLabel("Addresses:"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Cache Type (ways):"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Cache Sets:"), constraints);
		constraints.gridy++;
		topPanel.add(new JLabel("Block size (byte):"), constraints);

		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;

		constraints.gridy = 0;
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
		System.out.println("STARTING HIT/MISS CHECK");
	}
}
