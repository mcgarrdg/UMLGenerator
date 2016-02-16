package GUITest;

import problem.asm.UMLClass;
import problem.asm.UMLGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import static GUITest.Main.generateUMLPNG;

/**
 * Created by tiefenaw on 2/15/2016.
 */
public class CheckboxPanel extends JPanel {

	private DesignParserFrame frame;

	public CheckboxPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public CheckboxPanel(LayoutManager layout) {
		super(layout);
	}

	public CheckboxPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public CheckboxPanel(UMLGraph g, DesignParserFrame frm) {
		this.frame = frm;
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		int i = 0;
		for(UMLClass cls : g.getClasses())
		{
			c.gridy = i++;
			JCheckBox box = new JCheckBox(cls.getName(), cls.isActive());
			box.addActionListener(a -> {
				System.out.println(box.isSelected());
				cls.setActive(box.isSelected());
				this.frame.refreshUMLImage();
			});
			this.add(box, c);
		}



		JCheckBox box = new JCheckBox("CheckBox");
		box.addActionListener(a -> {
			System.out.println("Checkbox checked");
		});

//		GridBagConstraints c = new GridBagConstraints();
//		c.insets = new Insets(0, 0, 0, 0);
//		c.gridy = 0;
//		c.anchor = GridBagConstraints.NORTHWEST;
//		c.fill = GridBagConstraints.NONE;
//		c.fill = NONE

//		this.add(box, c);

//		box = new JCheckBox("CheckBox Indent");
//		box.addActionListener(a -> {
//			System.out.println("Checkbox checked");
//		});
//
//		c.insets = new Insets(0, 10, 0, 0);
//		c.gridy = 1;
//		this.add(box, c);
//		this.setPreferredSize(null);
//		this.add
	}
}
