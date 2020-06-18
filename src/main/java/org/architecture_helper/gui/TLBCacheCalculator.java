package org.architecture_helper.gui;

import javax.swing.*;
import java.awt.*;

public class TLBCacheCalculator extends RunnableTab {
    // common components
    private final ButtonGroup group = new ButtonGroup();

    private final JSpinner spNumberOfElements;
    private final JSpinner spInstructions;
    private final JSpinner spPageSize;
    private final JTextArea taResult;

    public TLBCacheCalculator() {
        /* RADIO BUTTONS */
        JRadioButton rbVector = new JRadioButton("Vector");
        rbVector.setActionCommand("vector");

        JRadioButton rbList = new JRadioButton("List");
        rbList.setActionCommand("list");
        rbList.setSelected(true);

        JRadioButton rbTree = new JRadioButton("Tree");
        rbTree.setActionCommand("tree");

        // Group the radio buttons.
        group.add(rbVector);
        group.add(rbList);
        group.add(rbTree);
        /* END RADIO BUTTONS */

        setLayout(new BorderLayout());

        // region creating top panel
        JPanel topPanel = new JPanel(new FlowLayout());

        // creating input panel
        JPanel inputPanel = new JPanel(new GridLayout(4,2));

        inputPanel.add(new JLabel("Page Size:"));
        inputPanel.add(spPageSize = new JSpinner(new SpinnerNumberModel(4096,1,Integer.MAX_VALUE,1)));

        inputPanel.add(new JLabel("Number of instructions (code+data):"));
        inputPanel.add(spInstructions = new JSpinner(new SpinnerNumberModel(6,1,Integer.MAX_VALUE,1)));

        inputPanel.add(new JLabel("Number of elements:"));
        inputPanel.add(spNumberOfElements = new JSpinner(new SpinnerNumberModel(4096, 1,Integer.MAX_VALUE,1)));

        inputPanel.add(new JLabel("Kind of data structure:"));

        // add radiobuttons sequentially
        JPanel rbPanel = new JPanel(new FlowLayout());
        rbPanel.add(rbVector);
        rbPanel.add(rbList);
        rbPanel.add(rbTree);

        inputPanel.add(rbPanel);
        topPanel.add(inputPanel);
        //endregion

        //region creating bottom panel
        JPanel botPanel = new JPanel(new BorderLayout());

        botPanel.add(new JLabel("Result:"), BorderLayout.NORTH);

        taResult = new JTextArea();
        botPanel.add(new JScrollPane(taResult), BorderLayout.CENTER);
        //endregion

        //region creating split pane
        JSplitPane topBotSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, botPanel);
        add(topBotSplit, BorderLayout.CENTER);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                topBotSplit.setDividerLocation(150);
            }
        });
        //endregion
    }

    // effective code
    public void run() {
        // determining what kind of data structure has been used
        String selection = group.getSelection().getActionCommand();
        int weight;

        switch (selection) {
            // vector
            case "vector":
                weight = 4;
                break;
            // list
            case "list":
                weight = 8;
                break;
            // tree
            default:
                weight = 12;
        }

        int numberOfElements = (int) spNumberOfElements.getValue();
        int totalDataSegmentWeight = numberOfElements * weight;
        int dataPages = totalDataSegmentWeight / (int) spPageSize.getValue();
        int totalPages = dataPages + 1;
        int numberOfInstructions = (int) spInstructions.getValue();
        double missRate = (double) totalPages / (double) (numberOfElements * numberOfInstructions) * 100;

        taResult.setText("Number of elements: " + numberOfElements + "\n"
            + "Total data segment weight: " + totalDataSegmentWeight + "\n"
            + "Data pages: " + dataPages + "\n"
            + "Total pages: " + totalPages + "\n"
            + "Miss rate: " + ((int) (missRate * 100) / 100.0) + "%");
    }
}
