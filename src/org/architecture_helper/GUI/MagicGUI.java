import java.awt.*; 
import javax.swing.*;

class pipelineDrawerTab extends JPanel {
	JLabel lblSource;
	JLabel lblResult;
	JTextArea taSourceCode;
	JTextArea taResult;

	public pipelineDrawerTab() {
		lblSource = new JLabel("Source:");
		lblResult = new JLabel("Result:");
		taSourceCode = new JTextArea(20, 5);
		taResult = new JTextArea(20, 5);

		setLayout(new GridLayout(5, 1));

		add(lblSource);
		add(taSourceCode);
		add(lblResult);
		add(taResult);
		add(new JButton("RUN"));    	
	}
}

class addressHitAndMissTab extends JPanel {
	private JLabel lblInput;
	private JLabel lblAddresses;
	private JLabel lblCacheType;
	private JLabel lblCacheSets;
	private JLabel lblBlockSize;
	private JLabel lblResult;

	public addressHitAndMissTab() {
		lblInput = new JLabel("Source:");
		lblAddresses = new JLabel("Addresses:");
		lblCacheType = new JLabel("Cache Type:");
		lblCacheSets = new JLabel("Sets:");
		lblBlockSize = new JLabel("Block size:");
		lblResult = new JLabel("Result:");

		
		add(new JTextArea(10, 20));
		add(new JButton("RUN"));
	}
}


class MagicGUI {
	final static String PIPELINE = "Pipeline Drawer";
    final static String ADDRESSHM = "Address Hit / Miss";


	public static void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
 
        tabbedPane.addTab(PIPELINE, new pipelineDrawerTab());
        tabbedPane.addTab(ADDRESSHM, new addressHitAndMissTab());
 
        pane.add(tabbedPane, BorderLayout.CENTER);
    }        

	// components
   	private static void createAndShowGui() {
      	JFrame frame = new JFrame("architecture-helper");

		//Create and set up the content pane.
        addComponentToPane(frame.getContentPane());
        
      	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	frame.pack();
      	frame.setLocationByPlatform(true);
      	frame.setVisible(true);
      	frame.setSize(950, 700);
   	}

    public static void main (String[] args) {
    	createAndShowGui();
  	}
}