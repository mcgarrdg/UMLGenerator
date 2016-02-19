package GUITest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class LandingScreenFrame extends JFrame
{
	ArrayList<IPhase> analyzePhases;
	public LandingScreenFrame(String title, ArrayList<IPhase> possiblePhases) throws HeadlessException {
		super(title);
		this.analyzePhases = new ArrayList<>();

		this.getContentPane().setLayout(new GridBagLayout());

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
//		c.ipadx = 15;
		c.insets = new Insets(20, 20, 20, 20);

		JButton loadCfg = new JButton("Load Config");
		buttonPanel.add(loadCfg, c);

		JButton analyze = new JButton("Analyze");
		analyze.addActionListener(a -> {
			if(this.analyzePhases == null || this.analyzePhases.isEmpty())
			{
				JOptionPane.showMessageDialog(new JFrame(),
						"Either no phases were found in the configuration, or no configuration has been loaded.",
						"No phases found",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		buttonPanel.add(analyze, c);

		c.gridy = 1;

		this.getContentPane().add(buttonPanel, c);

		c.gridy = 3;

		JProgressBar progressBar = new JProgressBar();
		this.getContentPane().add(progressBar, c);

		progressBar.setValue(50);
		progressBar.setStringPainted(true);
		progressBar.setString("Loading");

		this.pack();
	}

	public void setAnalyzePhases(ArrayList<IPhase> phases)
	{
		this.analyzePhases = phases;
	}
}
