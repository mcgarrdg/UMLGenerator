package GUITest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import problem.asm.UMLGraph;

/**
 * Created by tiefenaw on 2/15/2016.
 */
public class DesignParserFrame extends JFrame {

	public String getOutputPath() {
		return outputPath;
	}

	private String outputPath;
	private JLabel imageLabel;
	private UMLGraph umlGraph;
	private JPanel uiPanel;
	private ArrayList<IPhase> analyzePhases;

	public UMLGraph getUMLGraph() {
		return this.umlGraph;
	}
	public JScrollPane getScroll() {
		return scroll;
	}

	private JScrollPane scroll;

	public DesignParserFrame() throws HeadlessException {
	}

	public DesignParserFrame(GraphicsConfiguration gc) {
		super(gc);
	}

	public DesignParserFrame(String title) throws HeadlessException {
		super(title);
	}

	public DesignParserFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public DesignParserFrame(String title, String outputPath, UMLGraph g, ArrayList<IPhase> analyzePhases) throws HeadlessException {
		super(title);
		this.umlGraph = g;
//		this.getContentPane().setLayout(new GridBagLayout());
//		this.setLayout(new BorderLayout());
//		this.setLayout(new GridLayout(1,2,1,1));
		this.outputPath = outputPath;
		this.analyzePhases = analyzePhases;

		scroll = new JScrollPane();
		CheckboxPanel chk = new CheckboxPanel(g, this);

//		c.gridheight = GridBagConstraints.RELATIVE;
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.NORTH;

		ToolbarPanel tp = new ToolbarPanel(g, this);
		this.getContentPane().add(tp, BorderLayout.NORTH);

		this.uiPanel = new JPanel();


		GridBagConstraints c = new GridBagConstraints();
		this.uiPanel.setLayout(new GridBagLayout());
		c.gridy = 1;
		c.gridheight = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		c.anchor = GridBagConstraints.WEST;


		scroll.add(chk);
		scroll.setPreferredSize(new Dimension(200, 200));
		scroll.setViewportView(chk);


		this.uiPanel.add(scroll, c);

//		System.out.println(this.outputPath);
		ImageIcon image;
		try {
			image=new ImageIcon(ImageIO.read(new File(this.outputPath)));
			imageLabel=new JLabel(image);

//			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;

			scroll = new JScrollPane();
			scroll.add(imageLabel, c);
			scroll.setPreferredSize(new Dimension(200, 200));
			scroll.setViewportView(imageLabel);
			this.uiPanel.add(scroll, c);


//			this.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.getContentPane().add(this.uiPanel, BorderLayout.CENTER);

//		Image image = null;
//		image = ImageIO.read(new URL(url));
//		JLabel label=new JLabel(image);
//		ToolbarPanel tp = new ToolbarPanel(g);
//		this.getContentPane().add(tp);


		this.validate();
		this.pack();
	}

	public void refreshUMLImage()
	{
//		this.scroll.removeAll();

		try {
			Utilities.generateUMLPNG(umlGraph.toGraphVizString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		this.scroll.remove(imageLabel);

		GridBagConstraints c = new GridBagConstraints();

		c.gridheight = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		c.anchor = GridBagConstraints.WEST;

		ImageIcon image;
		try {
			image=new ImageIcon(ImageIO.read(new File(this.outputPath)));
			imageLabel=new JLabel(image);

//			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;

//			scroll = new JScrollPane();
			scroll.add(imageLabel, c);
//			scroll.setPreferredSize(new Dimension(200, 200));
			scroll.setViewportView(imageLabel);
//			this.getContentPane().add(scroll, c);


//			this.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.validate();
	}
	public ArrayList<IPhase> getAnalyzePhases() {
		return this.analyzePhases;
	}
}
