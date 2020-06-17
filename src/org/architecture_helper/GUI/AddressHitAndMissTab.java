package org.architecture_helper.gui;

import javax.swing.*;

public class AddressHitAndMissTab extends RunnableTab {
	private JLabel lblInput;
	private JLabel lblAddresses;
	private JLabel lblCacheType;
	private JLabel lblCacheSets;
	private JLabel lblBlockSize;
	private JLabel lblResult;

	public AddressHitAndMissTab() {
		lblInput = new JLabel("Source:");
		lblAddresses = new JLabel("Addresses:");
		lblCacheType = new JLabel("Cache Type:");
		lblCacheSets = new JLabel("Sets:");
		lblBlockSize = new JLabel("Block size:");
		lblResult = new JLabel("Result:");


		add(new JTextArea(10, 20));
		add(new JButton("RUN"));
	}

	@Override
	public void run() {
		System.out.println("STARTING HIT/MISS CHECK");
	}
}
