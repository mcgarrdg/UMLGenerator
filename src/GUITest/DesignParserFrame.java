package GUITest;

import problem.asm.DesignParser;
import problem.asm.UMLGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

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

	public DesignParserFrame(String title, String outputPath, UMLGraph g) throws HeadlessException {
		super(title);
		this.umlGraph = g;
		this.getContentPane().setLayout(new GridBagLayout());
//		this.setLayout(new BorderLayout());
//		this.setLayout(new GridLayout(1,2,1,1));
		this.outputPath = outputPath;

		scroll = new JScrollPane();
		CheckboxPanel chk = new CheckboxPanel(g, this);

		GridBagConstraints c = new GridBagConstraints();

		c.gridheight = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		c.anchor = GridBagConstraints.WEST;


		scroll.add(chk);
		scroll.setPreferredSize(new Dimension(200, 200));
		scroll.setViewportView(chk);


		this.getContentPane().add(scroll, c);

		System.out.println(this.outputPath);
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
			this.getContentPane().add(scroll, c);


//			this.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		Image image = null;
//		image = ImageIO.read(new URL(url));
//		JLabel label=new JLabel(image);



		this.validate();
		this.pack();
	}

	public void refreshUMLImage()
	{
//		this.scroll.removeAll();

		try {
			Main.generateUMLPNG(umlGraph.toGraphVizString(), this.getOutputPath());
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
			this.getContentPane().add(scroll, c);


//			this.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.validate();
	}
}
