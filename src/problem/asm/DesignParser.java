package problem.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Design Parser
 * @author mcgarrdg and tiefenaw
 *
 */

public class DesignParser 
{
	/**
	 * A string that represents where the GraphViz dot.exe file is.
	 */
	private static String dotPath;
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		int mode = selectMode();
		if(mode == 0)
		{
			locateGraphviz();
			String g = getDigraphString();
			System.out.println(g);
			generatePNG(g);
		}
		else if(mode == 1)
		{
			//TODO make this more interactive
			String methodSig = JOptionPane.showInputDialog("Please input a fully qualified method signature:");
			//ClassReader reader = new ClassReader(className);
//			problem.asm.DesignParser
//			problem.asm.UMLClass 
//			problem.asm.UMLField 
//			problem.asm.UMLGraph 
//			problem.asm.IGraphItem
			//TODO Sequence diagram
		}
	}
	
	public static String getDigraphString() throws IOException
	{
		JFileChooser choose = new JFileChooser();
		choose.setMultiSelectionEnabled(true);
		choose.setFileFilter(new FileNameExtensionFilter("Class files", "class"));
		choose.setCurrentDirectory(new File("./files/"));
		choose.showOpenDialog(null);
		File[] files = choose.getSelectedFiles();
		return visitFiles(files).toGraphVizString();
	}
	
	/**
	 * Using asm visitors, all files in the files array are visited, and a UMLGraph is constructed.
	 * @param files	An array of {@link File Files} representing the Java .class files to make this UML from.
	 * @return	A completed UMLGraph.
	 * @throws IOException
	 */
	public static UMLGraph visitFiles(File[] files) throws IOException
	{
		UMLGraph graph = new UMLGraph("Test_UML", "BT");

		for(File f : files)
		{
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
		}
		
		return graph;
	}

	public static int selectMode()
	{
		Object[] options = {"UML", "Sequence Diagram"};
		int n = JOptionPane.showOptionDialog(null,
		    "Please select a mode.",
		    "Mode Selection",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
		return n;
	}
	
	/**
	 * A function that opens a window, prompting the user to locate Graphviz's dot.exe if it is not in the default path.
	 * The user does this with a {@link JFileChooser}.
	 */
	public static void locateGraphviz()
	{
		Object[] options = {"Yes", "No, select another path"};
		int n = JOptionPane.showOptionDialog(null,
		    "Is your Graphiz dot path C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot?",
		    "Locate Graphiz",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
		
		if (n == 1)
		{
			JFileChooser dotChooser = new JFileChooser();
			dotChooser.setMultiSelectionEnabled(false);
			dotChooser.setCurrentDirectory(new File("C:\\"));
			dotChooser.showOpenDialog(null);
			dotPath = dotChooser.getSelectedFile().getAbsolutePath();
		}
		else
		{
			dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot";
		}
	}
	
	/**
	 * Given a valid String for a GraphViz document, this uses dot.exe to generate a PNG of the UML diagram.
	 * @param s	The string to pass into GraphViz.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void generatePNG(String s) throws IOException, InterruptedException
	{
		JFileChooser choose = new JFileChooser();
		choose.setMultiSelectionEnabled(false);
		choose.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
		choose.setCurrentDirectory(new File("./files/"));
		choose.showSaveDialog(null);
		
		boolean confirmOverwrite = choose.getSelectedFile().exists();
		while(confirmOverwrite)
		{
			Object[] options = {"Yes", "No"};
			int n = JOptionPane.showOptionDialog(null,
			    "The file you have specified already exists. Are you sure you want to overwrite it?",
			    "Overwite file?",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
			
			if (n == 0) //User says yes
			{
				choose.getSelectedFile().delete();
				confirmOverwrite = false;
			}
			else if (n == 1) //User says no.
			{
				choose.showSaveDialog(null);
			}
		}
		//TODO This could be prone to bugs if the user enters in odd names. Should we bother fixing?
		String filePath;
		if(choose.getSelectedFile().getName().lastIndexOf('.') == -1)
		{
			filePath = choose.getSelectedFile().getPath();
		}
		else
		{
			filePath = choose.getSelectedFile().getPath().substring(0, choose.getSelectedFile().getAbsolutePath().lastIndexOf('.'));
		}
		
		PrintWriter writer = new PrintWriter(filePath + ".dot", "UTF-8");
		writer.println(s);
		writer.flush();
		writer.close();
		File f = new File(filePath + ".dot");
		while(!f.exists());
		String command = "\"" + dotPath + "\" -Tpng " + filePath + ".dot -o " + filePath + ".png";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		pr.waitFor();
		pr.destroy();
	}
}
