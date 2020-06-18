package org.architecture_helper.gui;

import javax.swing.*;
import java.awt.*;

public class TLBCacheCalculator extends RunnableTab {
    // common components
    private final JSpinner spNumberOfElements;
    private final JSpinner spMemoryAccesses;
    private final JSpinner spPageSize;
    private final JSpinner spElementSize =  new JSpinner();
    private final JTextArea taResult;

    public TLBCacheCalculator() {
        setLayout(new BorderLayout());

        // region creating top panel
        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));

        // region virtual memory panel
        JPanel virtualMemPanel = new JPanel(new GridBagLayout());
        topPanel.add(virtualMemPanel);

        virtualMemPanel.setBorder(BorderFactory.createTitledBorder("Virtual Memory Data:"));

        //setting constraints for the first column
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.ipadx = 115;
        //constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;

        //first column
        JLabel temp = new JLabel("Page Size:");
        temp.setSize(new Dimension(600,500));
        virtualMemPanel.add(temp, constraints);

        //setting constraints for the second column
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;

        //second column
        spPageSize = new JSpinner(new SpinnerNumberModel(4096,1,Integer.MAX_VALUE,1));
        ((JSpinner.DefaultEditor)spPageSize.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        virtualMemPanel.add(spPageSize, constraints);
        //endregion

        //region program data
        JPanel programDataPanel = new JPanel(new GridBagLayout());
        topPanel.add(programDataPanel);

        programDataPanel.setBorder(BorderFactory.createTitledBorder("Program Data:"));

        //setting constraints for the first column
        constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.ipadx = 10;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;

        //first column
        programDataPanel.add(new JLabel("Memory access (code+data):"), constraints);
        constraints.gridy++;
        programDataPanel.add(new JLabel("Number of elements:"), constraints);
        constraints.gridy++;
        programDataPanel.add(new JLabel("Kind of data structure:"), constraints);
        constraints.gridy++;
        programDataPanel.add(new JLabel("Element size (byte):"), constraints);

        //setting constraints for the second column
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;

        //second column
        spMemoryAccesses = new JSpinner(new SpinnerNumberModel(6,1,Integer.MAX_VALUE,1));
        ((JSpinner.DefaultEditor) spMemoryAccesses.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        programDataPanel.add(spMemoryAccesses, constraints);

        constraints.gridy++;
        spNumberOfElements = new JSpinner(new SpinnerNumberModel(4096, 1,Integer.MAX_VALUE,1));
        ((JSpinner.DefaultEditor)spNumberOfElements.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        programDataPanel.add(spNumberOfElements, constraints);


        //region radioButtons
        constraints.gridy++;
        JPanel rbPanel = new JPanel(new FlowLayout());
        programDataPanel.add(rbPanel, constraints);

        JButton rbVector = new JButton("Vector");
        JButton rbList = new JButton("List");
        JButton rbTree = new JButton("Tree (binary)");

        rbPanel.add(rbVector);
        rbPanel.add(rbList);
        rbPanel.add(rbTree);

        rbVector.addActionListener(e -> spElementSize.setValue(4));
        rbList.addActionListener(e -> spElementSize.setValue(8));
        rbTree.addActionListener(e -> spElementSize.setValue(12));

        rbList.setSelected(true);
        //endregion

        constraints.gridy++;
        spElementSize.setModel(new SpinnerNumberModel(8, 1,Integer.MAX_VALUE,1));
        ((JSpinner.DefaultEditor)spElementSize.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        programDataPanel.add(spElementSize, constraints);

        //endregion

        //endregion

        //region creating bottom panel
        JPanel botPanel = new JPanel(new BorderLayout());
        add(botPanel, BorderLayout.CENTER);

        botPanel.add(new JLabel("Result:"), BorderLayout.NORTH);

        taResult = new JTextArea();
        taResult.setFont(Main.MONOSPACED);
        botPanel.add(new JScrollPane(taResult), BorderLayout.CENTER);
        //endregion
    }

    // effective code
    public void run() {
        //getting data from UI
        int numberOfElements = (int) spNumberOfElements.getValue();

        //calculating size of the program data segment
        int totalDataSegmentWeight = numberOfElements * (int) spElementSize.getValue();

        //calculating the number of pages necessary to fit in the data segment
        int dataPages = totalDataSegmentWeight / (int) spPageSize.getValue();

        //assuming that the text segment is only 1 page big
        int totalPages = dataPages + 1;

        int memoryAccesses = numberOfElements * (int) spMemoryAccesses.getValue();

        //missRate = pages / total memory access (assuming it's a cycle accesses = elements*accessesPerCycle)
        //multiplying by 100 to get it in percentage and not in rateo
        double missRate = totalPages * 100.0 / memoryAccesses ;

        String output = "";
        output += "MEMORY ACCESSES:\n";
        output += "Number of elements: " + numberOfElements + "\n";
        output += "Memory accesses: " + memoryAccesses + "\n";
        output += "\n";
        output += "TLB:\n";
        output += "Total data segment weight: " + totalDataSegmentWeight + "\n";
        output += "Data pages: " + dataPages + "\n";
        output += "Total pages: " + totalPages + "\n";
        output += "Miss rate: " + totalPages + "\n";

        taResult.setText(output);
    }
}
