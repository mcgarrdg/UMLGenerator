package GUI;

import GUI.Phases.IPhase;
import Core.UMLItems.UMLGraph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class LandingScreenFrame extends JFrame
{
	private ArrayList<IPhase> analyzePhases;
	private Properties props;
	private UMLGraph graph;
	private JProgressBar progressBar;
	private JButton analyze;
	private JButton loadCfg;
	
	public JButton getAnalyzeButton() {
		return this.analyze;
	}
	public JButton getloadCfgButton() {
		return this.loadCfg;
	}
	public LandingScreenFrame(String title, ArrayList<IPhase> possiblePhases, UMLGraph g) throws HeadlessException {
		super(title);
		props = null;
		this.graph = g;
		this.analyzePhases = possiblePhases;

		this.getContentPane().setLayout(new GridBagLayout());

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 20, 20);

		loadCfg = new JButton("Load Config");
		loadCfg.addActionListener(a -> {
			JFileChooser choose = new JFileChooser();
			choose.setMultiSelectionEnabled(false);
			choose.setFileFilter(new FileNameExtensionFilter("Properties File", "properties"));
			choose.setCurrentDirectory(new File("./files/"));
			choose.showOpenDialog(null);
			File file = choose.getSelectedFile();
			this.props = new Properties();
			try {
				FileInputStream in = new FileInputStream(file);
				this.props.load(in);
				in.close();

				Utilities.dotPath = props.getProperty(Utilities.DOT_PATH_KEY);
				Utilities.outputDirectoryPath = props.getProperty(Utilities.OUTPUT_PATH_KEY);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		buttonPanel.add(loadCfg, c);

		analyze = new JButton("Analyze");
		analyze.addActionListener(a -> {
			if(this.props == null)
			{
				JOptionPane.showMessageDialog(new JFrame(),
						"No configuration has been loaded.",
						"No configuration found",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(this.analyzePhases == null || this.analyzePhases.isEmpty())
			{
				JOptionPane.showMessageDialog(new JFrame(),
						"Either no phases were found in the configuration, or no configuration has been loaded.",
						"No phases found",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				this.graph = new UMLGraph(this.graph.getName(), this.graph.getRankdir());
				ArrayList<IPhase> tempPhases = new ArrayList<IPhase>();
				for(IPhase phase : this.analyzePhases)
				{
					tempPhases.add(phase.restart(this.graph, this.props));
				}
				this.analyzePhases = tempPhases;
				Thread t = new Thread (new Runnable() {
					@Override
					public void run() {
						progressBar.setMinimum(0);
						progressBar.setMaximum(analyzePhases.size());
						for(IPhase phase : analyzePhases)
						{
							try {
								SwingUtilities.invokeAndWait(new Runnable() {
									@Override
									public void run() {
										if(phase.isActive())
										{
											progressBar.setString(phase.getPhaseDescription());
											phase.execute();
										}			
										progressBar.setValue(progressBar.getValue()+1);
									}
								});
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
						}
						DesignParserFrame p = new DesignParserFrame("Design Parser", graph, analyzePhases);
						p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						p.setVisible(true);
						dispose();
						
					
					}
				});
				t.start();	
			}

		});
		buttonPanel.add(analyze, c);

		c.gridy = 1;

		this.getContentPane().add(buttonPanel, c);

		c.gridy = 3;

		progressBar = new JProgressBar();
		this.getContentPane().add(progressBar, c);

		progressBar.setStringPainted(true);
		progressBar.setString("Loading");

		this.pack();
	}

	public void setAnalyzePhases(ArrayList<IPhase> phases)
	{
		this.analyzePhases = phases;
	}
	
}
