package GUITest;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import problem.asm.UMLGraph;

public class ToolbarPanel extends JMenuBar {
	private JMenu fileMenu;
	private JMenu helpMenu;
	private UMLGraph g;
	private DesignParserFrame frame;

	public ToolbarPanel(UMLGraph g, DesignParserFrame frame) {
		// Build the file menu.
		this.g = g;
		this.frame = frame;
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		

		JMenuItem menuItem = new JMenuItem("Import Properties", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				JFileChooser choose = new JFileChooser();
//				choose.setMultiSelectionEnabled(false);
//				choose.setFileFilter(new FileNameExtensionFilter("Properties File", "properties"));
//				choose.setCurrentDirectory(new File("./files/"));
//				choose.showOpenDialog(null);
//				File file = choose.getSelectedFile();
				
//				Properties props = new Properties();
//				try {
//					FileInputStream in = new FileInputStream(file);
//					props.load(in);
//					in.close();
//				} catch (IOException exception) {
//					exception.printStackTrace();
//				}
				
				ArrayList<IPhase> analyzePhases = ToolbarPanel.this.frame.getAnalyzePhases();
				UMLGraph oldGraph = ToolbarPanel.this.frame.getUMLGraph();
				LandingScreenFrame s = new LandingScreenFrame("Landing", analyzePhases, new UMLGraph(oldGraph.getName(), oldGraph.getRankdir()));
				s.getloadCfgButton().doClick();
				s.getAnalyzeButton().doClick();
				s.setVisible(true);

				
//				if(props == null)
//				{
//					JOptionPane.showMessageDialog(new JFrame(),
//							"No configuration has been loaded.",
//							"No configuration found",
//							JOptionPane.ERROR_MESSAGE);
//				}
//				else
//					if(analyzePhases == null || analyzePhases.isEmpty())
//				{
//					JOptionPane.showMessageDialog(new JFrame(),
//							"Either no phases were found in the configuration, or no configuration has been loaded.",
//							"No phases found",
//							JOptionPane.ERROR_MESSAGE);
//				}
//				else
//				{
//					UMLGraph oldGraph = ToolbarPanel.this.frame.getUMLGraph();
//					UMLGraph graph = new UMLGraph(oldGraph.getName(), oldGraph.getRankdir());
//					ArrayList<IPhase> tempPhases = new ArrayList<IPhase>();
//					for(IPhase phase : analyzePhases)
//					{
//						tempPhases.add(phase.restart(graph, props));
//					}
//					analyzePhases = tempPhases;
//					for(IPhase phase : analyzePhases)
//					{
////						System.out.println(phase.getPhaseName());
//						if(phase.isActive())
//						{
////							System.out.println(phase.getPhaseName());
////							phase.getPhaseName();
////							progressBar.setString(phase.getPhaseDescription());
//							phase.execute();
////							phase.getPhaseName();
////							try {
////								Thread.sleep(1000);
////							} catch (InterruptedException e) {
////								e.printStackTrace();
////							}
//						}
//					}
//
//
//					DesignParserFrame p = new DesignParserFrame("Design Parser", Utilities.outputFile, graph, analyzePhases);
//					p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					p.setVisible(true);
					ToolbarPanel.this.frame.dispose();
//				}
				
				
				
				
				
				
			}
			
		});
		fileMenu.add(menuItem);
		
		JMenu submenu = new JMenu("Export PNG");
		
		menuItem = new JMenuItem("...to Defualt", KeyEvent.VK_D);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Place holder, will just overwrite the PNG with a copy of itself. 
				// Also, not sure if passing in the UMLGraph is the best option. There may be a better way
				// to do this. 
				try {
					Utilities.generateUMLPNG(g.toGraphVizString());
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("...to Specified", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Also, not sure if passing in the UMLGraph is the best option. There may be a better way
				// to do this. 
				JFileChooser choose = new JFileChooser();
				choose.setMultiSelectionEnabled(false);
				choose.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
				choose.setCurrentDirectory(new File("./files/"));
				choose.showSaveDialog(null);

				boolean confirmOverwrite = choose.getSelectedFile().exists();
				while (confirmOverwrite) {
					Object[] options = { "Yes", "No" };
					int n = JOptionPane.showOptionDialog(null,
							"The file you have specified already exists. Are you sure you want to overwrite it?",
							"Overwite file?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);

					if (n == 0) // User says yes
					{
						choose.getSelectedFile().delete();
						confirmOverwrite = false;
					} else if (n == 1) // User says no.
					{
						choose.showSaveDialog(null);
					}
				}
				// This could be prone to bugs if the user enters in odd names.
				// Should we bother fixing?
				String filePath;
				if (choose.getSelectedFile().getName().lastIndexOf('.') == -1) {
					filePath = choose.getSelectedFile().getPath();
				} else {
					filePath = choose.getSelectedFile().getPath().substring(0,
							choose.getSelectedFile().getAbsolutePath().lastIndexOf('.'));
				}

				PrintWriter writer;
				try {
					writer = new PrintWriter(filePath + ".dot", "UTF-8");
					writer.println(g.toGraphVizString());
					writer.flush();
					writer.close();
					File f = new File(filePath + ".dot");
					while (!f.exists());
					//TODO: Undo hardcoding of graphviz path
					String command = "\"" + "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot" + "\" -Tpng " + filePath + ".dot -o " + filePath + ".png";
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec(command);
					pr.waitFor();
					pr.destroy();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		submenu.add(menuItem);
		fileMenu.add(submenu);
		this.add(fileMenu);
		
		
		
		this.helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		menuItem = new JMenuItem("Instructions", KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame(); 
				frame.setTitle("Instructions");
				File file = new File("./files/instructions.txt");
				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					JTextArea text = new JTextArea();
					text.read(in, null);
					text.setFont(new Font("Arial", Font.PLAIN, 18));
					frame.add(text);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.pack();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		helpMenu.add(menuItem);
		
		menuItem = new JMenuItem("About", KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(new JFrame(), "UMLGenerator\nCreated by Alec Tiefenthal and Daniel McGarry\n Winter Quarter 2015-2016", "About", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
			
		});
		helpMenu.add(menuItem);
		this.add(helpMenu);
		
	}
}
