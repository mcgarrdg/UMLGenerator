package GUITest;

import PatternDetectors.IPatternDetector;
import problem.asm.UMLClass;
import problem.asm.UMLGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import static GUITest.Main.generateUMLPNG;

/**
 * Created by tiefenaw on 2/15/2016.
 */
public class CheckboxPanel extends JPanel {

	private DesignParserFrame frame;

	private HashMap<String, ArrayList<JCheckBox>> classCheckboxMap;

	private HashMap<String, ArrayList<JCheckBox>> patternCheckboxMap;

	public boolean isChangingChecks() {
		return isChangingChecks;
	}

	public void setChangingChecks(boolean changingChecks) {
		isChangingChecks = changingChecks;
	}

	private boolean isChangingChecks;

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
		this.classCheckboxMap = new HashMap<>();
		this.patternCheckboxMap = new HashMap<>();

		isChangingChecks = false;

		this.frame = frm;
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		int i = 0;

		for (IPatternDetector p : g.getPatternDetectors())
		{
			c.gridy = i++;
			c.insets = new Insets(0, 0, 0, 0);
			JCheckBox box = new JCheckBox(p.getPatternCatagoryName(), true);
			box.addActionListener(a -> {
				boolean setValue = box.isSelected();
				this.setChangingChecks(true);
				for(JCheckBox check : this.patternCheckboxMap.get(p.getPatternCatagoryName()))
				{
//					check.setSelected(setValue);
//					check.getActionListeners()[0].
					if(check.isSelected() != setValue)
					{
						check.doClick(1);
					}
				}
				this.setChangingChecks(false);
				this.refreshImage();
//				for(JCheckBox check : this.classCheckboxMap.get(cls.getName()))
//				{
//					check.setSelected(setValue);
//				}
//				cls.setActive(setValue);
			});

			this.patternCheckboxMap.putIfAbsent(p.getPatternCatagoryName(), new ArrayList<>());
			this.add(box, c);

//			JCheckBox box = new JCheckBox(cls.getName(), cls.isActive());
//			box.addActionListener(a -> {
//				cls.setActive(box.isSelected());
//				this.frame.refreshUMLImage();
//			});
//			this.add(box, c);

//			ArrayList<JCheckBox> classChecks = new ArrayList<>();
			for(UMLClass cls : g.getClasses())
			{
				if(cls.getPatternCatagories().contains(p.getPatternCatagoryName()))
				{
					this.classCheckboxMap.putIfAbsent(cls.getName(), new ArrayList<>());

					c.gridy = i++;
					c.insets = new Insets(0, 20, 0, 0);
					JCheckBox classCheckBox = new JCheckBox(cls.getName(), cls.isActive());
					classCheckBox.addActionListener(a -> {
						boolean setValue = classCheckBox.isSelected();
						for(JCheckBox check : this.classCheckboxMap.get(cls.getName()))
						{
							check.setSelected(setValue);
						}
						cls.setActive(setValue);
						this.refreshImage();
					});
					this.add(classCheckBox, c);

					this.patternCheckboxMap.get(p.getPatternCatagoryName()).add(classCheckBox);
					this.classCheckboxMap.get(cls.getName()).add(classCheckBox);
//					System.out.println(cls.getName());
//					if(cls.getName().equals("problem/asm/ClassDeclarationVisitor"))
//					{
//						for(String name : cls.getPatternCatagories())
//						{
//							System.out.println(name);
//						}
//						System.out.println("----");
//						for(String name : cls.getPatternNames())
//						{
//							System.out.println(name);
//						}
//					}
				}
			}


			//TODO Add classes that were not part of a pattern to some default catagory
		}

		c.gridy = i++;
		c.insets = new Insets(0, 0, 0, 0);
		JCheckBox box = new JCheckBox("Uncatagorized", true);
		box.addActionListener(a -> {
			boolean setValue = box.isSelected();
			this.setChangingChecks(true);
			for(JCheckBox check : this.patternCheckboxMap.get("Uncatagorized"))
			{
//					check.setSelected(setValue);
//					check.getActionListeners()[0].
				if(check.isSelected() != setValue)
				{
					check.doClick(1);
				}
			}
			this.setChangingChecks(false);
			this.refreshImage();
//				for(JCheckBox check : this.classCheckboxMap.get(cls.getName()))
//				{
//					check.setSelected(setValue);
//				}
//				cls.setActive(setValue);
		});

		this.patternCheckboxMap.putIfAbsent("Uncatagorized", new ArrayList<>());
		this.add(box, c);

//			JCheckBox box = new JCheckBox(cls.getName(), cls.isActive());
//			box.addActionListener(a -> {
//				cls.setActive(box.isSelected());
//				this.frame.refreshUMLImage();
//			});
//			this.add(box, c);

//			ArrayList<JCheckBox> classChecks = new ArrayList<>();
		for(UMLClass cls : g.getClasses())
		{
			if(cls.getPatternCatagories().isEmpty())
			{
				this.classCheckboxMap.putIfAbsent(cls.getName(), new ArrayList<>());

				c.gridy = i++;
				c.insets = new Insets(0, 20, 0, 0);
				JCheckBox classCheckBox = new JCheckBox(cls.getName(), cls.isActive());
				classCheckBox.addActionListener(a -> {
					boolean setValue = classCheckBox.isSelected();
					for(JCheckBox check : this.classCheckboxMap.get(cls.getName()))
					{
						check.setSelected(setValue);
					}
					cls.setActive(setValue);
					this.refreshImage();
				});
				this.add(classCheckBox, c);

				this.patternCheckboxMap.get("Uncatagorized").add(classCheckBox);
				this.classCheckboxMap.get(cls.getName()).add(classCheckBox);
//					System.out.println(cls.getName());
//					if(cls.getName().equals("problem/asm/ClassDeclarationVisitor"))
//					{
//						for(String name : cls.getPatternCatagories())
//						{
//							System.out.println(name);
//						}
//						System.out.println("----");
//						for(String name : cls.getPatternNames())
//						{
//							System.out.println(name);
//						}
//					}
			}
		}

//		for(UMLClass cls : g.getClasses())
//		{
//			c.gridy = i++;
//			JCheckBox box = new JCheckBox(cls.getName(), cls.isActive());
//			box.addActionListener(a -> {
//				cls.setActive(box.isSelected());
//				this.frame.refreshUMLImage();
//			});
//			this.add(box, c);
//		}



//		JCheckBox box = new JCheckBox("CheckBox");
//		box.addActionListener(a -> {
//			System.out.println("Checkbox checked");
//		});

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

	private void refreshImage()
	{
		if(this.isChangingChecks)
		{
			return;
		}
		else
		{
			this.frame.refreshUMLImage();
		}
	}
}
