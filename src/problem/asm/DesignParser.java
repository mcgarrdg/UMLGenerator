package problem.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private static String dotPath;
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		locateGraphviz();
		String g = getDigraphString();
		System.out.println(g);
		generatePNG(g);
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
	
	//TODO Add confirmation popup if they select a file that already exists
	public static void generatePNG(String s) throws IOException, InterruptedException
	{
		JFileChooser choose = new JFileChooser();
		choose.setMultiSelectionEnabled(false);
		choose.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
		choose.setCurrentDirectory(new File("./files/"));
		choose.showSaveDialog(null);
		
		//TODO This could be prone to bugs if the user enters in odd names. Should we bother fixing?
		String filePath;
		if(choose.getSelectedFile().getName().lastIndexOf('.') == -1)
		{
			filePath = choose.getSelectedFile().getPath();
		}
		else
		{
			filePath = choose.getSelectedFile().getPath().substring(0, choose.getSelectedFile().getName().lastIndexOf('.') + 1);
		}
		
		//TODO make it store the file where the user wanted it
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
