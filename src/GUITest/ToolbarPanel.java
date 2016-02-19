package GUITest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import problem.asm.DesignParser;
import problem.asm.UMLGraph;

public class ToolbarPanel extends JMenuBar {
	private JMenu fileMenu;
	private JMenu helpMenu;
	private UMLGraph g;

	public ToolbarPanel(UMLGraph g) {
		// Build the file menu.
		this.g = g;
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		

		JMenuItem menuItem = new JMenuItem("Load Properties", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				choose.setMultiSelectionEnabled(false);
				choose.setFileFilter(new FileNameExtensionFilter("Properties File", "properties"));
				choose.setCurrentDirectory(new File("./files/"));
				choose.showOpenDialog(null);
				File file = choose.getSelectedFile();
			}
			
		});
		fileMenu.add(menuItem);
		
		JMenu submenu = new JMenu("Export PNG");
		
		JMenuItem subItem = new JMenuItem("...to Defualt", KeyEvent.VK_D);
		subItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		subItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Place holder, will just overwrite the PNG with a copy of itself. 
				// Also, not sure if passing in the UMLGraph is the best option. There may be a better way
				// to do this. 
				try {
					Main.generateUMLPNG(g.toGraphVizString());
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		submenu.add(subItem);
		
		subItem = new JMenuItem("...to Specified", KeyEvent.VK_S);
		subItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		subItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Should we remove Dependency on DesignParser? The code there does exacly what we need it to do.
				// Also, not sure if passing in the UMLGraph is the best option. There may be a better way
				// to do this. 
				try {
					DesignParser.generateUMLPNG(g.toGraphVizString());
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		submenu.add(subItem);
		fileMenu.add(submenu);
		this.add(fileMenu);
		

	}
}
