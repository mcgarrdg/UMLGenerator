package Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Core.UMLItems.UMLGraph;
import PatternDetectors.AdapterPatternDetector;
import PatternDetectors.CompositePatternDetector;
import PatternDetectors.DecoratorPatternDetector;
import PatternDetectors.SingletonPatternDetector;
import Core.Visitors.ClassDeclarationVisitor;
import Core.Visitors.ClassFieldVisitor;
import Core.Visitors.ClassMethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Design Parser
 * 
 * @author mcgarrdg and tiefenaw
 *
 */

@Deprecated
public class DesignParser {
	/**
	 * A string that represents where the GraphViz dot.exe file is.
	 */
	private static String dotPath;
	
	private static String sdEditPath;

	public static void main(String[] args) throws IOException, InterruptedException {
		int mode = selectMode();
		if (mode == 0) {
			locateGraphviz();
			String g = getDigraphString();
//			System.out.println(g);
			generateUMLPNG(g);
		} else if (mode == 1) {
			// TODO make this more interactive
			String methodSig = JOptionPane.showInputDialog("Please input a fully qualified method signature:");
			String classSig = methodSig.substring(0, methodSig.lastIndexOf("."));
			
			//TODO Check for user input errors.
			int callDepth = Integer.parseInt(JOptionPane.showInputDialog("Please input the call depth:", "5"));
			System.out.println(callDepth);
			locateSDEdit();

			ClassReader reader = new ClassReader(classSig);
			UMLGraph graph = new UMLGraph("Test_SD", "BT");

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			graph.generateCallSequence(methodSig, callDepth);
			
			generateSDEditPNG(graph.toSDEditString());
		}
	}

	public static String getDigraphString() throws IOException {
		JFileChooser choose = new JFileChooser();
		choose.setMultiSelectionEnabled(true);
		choose.setFileFilter(new FileNameExtensionFilter("Class files", "class"));
		choose.setCurrentDirectory(new File("./files/"));
		choose.showOpenDialog(null);
		File[] files = choose.getSelectedFiles();
		return visitFiles(files).toGraphVizString();

//		JFileChooser choose = new JFileChooser();
//		choose.setMultiSelectionEnabled(false);
//		choose.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
//		choose.setCurrentDirectory(new File("./files/"));
//		choose.showSaveDialog(null);

//		FileListAccessory accessory = new FileListAccessory(choose);
//		choose.setAccessory(accessory);
//
//		int open = choose.showOpenDialog(choose);
//		File[] files = null;
//		if (open == JFileChooser.APPROVE_OPTION) {
//			files = (File[])(accessory.getModel().toArray());
////			DefaultListModel model = accessory.getModel();
////			for (int i = 0; i < model.getSize(); i++) {
////				System.out.println(((File)model.getElementAt(i)).getName());
////			}
//		}

//		File[] files = choose.getSelectedFiles();
//		return visitFiles(files).toGraphVizString();
	}

	/**
	 * Using asm visitors, all files in the files array are visited, and a
	 * UMLGraph is constructed.
	 * 
	 * @param files
	 *            An array of {@link File Files} representing the Java .class
	 *            files to make this UML from.
	 * @return A completed UMLGraph.
	 * @throws IOException
	 */
	public static UMLGraph visitFiles(File[] files) throws IOException {
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		graph.addPatternDetector(new DecoratorPatternDetector());
		graph.addPatternDetector(new AdapterPatternDetector());
		graph.addPatternDetector(new CompositePatternDetector());
		for (File f : files) {
//			System.out.println(f.getCanonicalPath());
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
		}
		graph.generateArrows();
		graph.detectPatterns();
		return graph;
	}

	public static int selectMode() {
		Object[] options = { "UML", "Sequence Diagram" };
		return JOptionPane.showOptionDialog(null, "Please select a mode.", "Mode Selection", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * A function that opens a window, prompting the user to locate Graphviz's
	 * dot.exe if it is not in the default path. The user does this with a
	 * {@link JFileChooser}.
	 */
	public static void locateGraphviz() {
		Object[] options = { "Yes", "No, select another path" };
		int n = JOptionPane.showOptionDialog(null,
				"Is your Graphiz dot path C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot?", "Locate Graphiz",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (n == 1) {
			JFileChooser dotChooser = new JFileChooser();
			dotChooser.setMultiSelectionEnabled(false);
			dotChooser.setCurrentDirectory(new File("C:\\"));
			dotChooser.showOpenDialog(null);
			dotPath = dotChooser.getSelectedFile().getAbsolutePath();
		} else {
			dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot";
		}
	}
	
	public static void locateSDEdit() {
		Object[] options = { "Yes", "No, select another path" };
		int n = JOptionPane.showOptionDialog(null,
				"Is your SDEdit dot path C:\\Program Files (x86)\\SDEdit\\sdedit-4.2-beta1?", "Locate SDEdit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 1) {
			JFileChooser sdChooser = new JFileChooser();
			sdChooser.setMultiSelectionEnabled(false);
			sdChooser.setCurrentDirectory(new File("C:\\"));
			sdChooser.showOpenDialog(null);
			sdEditPath = sdChooser.getSelectedFile().getAbsolutePath();
		} else {
			sdEditPath = "C:\\Program Files (x86)\\SDEdit\\sdedit-4.2-beta1";
		}
	}

	/**
	 * Given a valid String for a GraphViz document, this uses dot.exe to
	 * generate a PNG of the UML diagram.
	 * 
	 * @param s
	 *            The string to pass into GraphViz.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void generateUMLPNG(String s) throws IOException, InterruptedException {
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

		PrintWriter writer = new PrintWriter(filePath + ".dot", "UTF-8");
		writer.println(s);
		writer.flush();
		writer.close();
		File f = new File(filePath + ".dot");
		while (!f.exists());
		String command = "\"" + dotPath + "\" -Tpng " + filePath + ".dot -o " + filePath + ".png";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		pr.waitFor();
		pr.destroy();
	}
	
	public static void generateSDEditPNG(String s) throws IOException, InterruptedException {
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

		PrintWriter writer = new PrintWriter(filePath + ".sd", "UTF-8");
		writer.println(s);
		writer.flush();
		writer.close();
		File f = new File(filePath + ".sd");
		while (!f.exists());
		
		String command = "\"" + sdEditPath + "\" -o " + filePath + ".png -t png " + filePath + ".sd";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		pr.waitFor();
		pr.destroy();
	}
}
