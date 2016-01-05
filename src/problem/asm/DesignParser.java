package problem.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Design Parser
 * @author mcgarrdg and tiefenaw
 *
 */
public class DesignParser {
	public static void main(String[] args) throws IOException {
		System.out.println(getDigraphString(args));
	}
	
	public static String getDigraphString(String[] args) throws IOException
	{
		JFileChooser choose = new JFileChooser();
		choose.setMultiSelectionEnabled(true);
		choose.setCurrentDirectory(new File("./bin/problem/asm"));
		choose.showOpenDialog(null);
		File[] files = choose.getSelectedFiles();
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		//for(String className : args)
		for(File f : files)
		{
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);
			//ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);
			//ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, declVisitor);
			//ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
			//System.out.println(graph.toGraphVizString());
			//reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);

		}
		return graph.toGraphVizString();
	}

}
