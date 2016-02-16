package GUITest;

import PatternDetectors.AdapterPatternDetector;
import PatternDetectors.CompositePatternDetector;
import PatternDetectors.DecoratorPatternDetector;
import PatternDetectors.SingletonPatternDetector;
import Visitors.ClassDeclarationVisitor;
import Visitors.ClassFieldVisitor;
import Visitors.ClassMethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import problem.asm.UMLGraph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by tiefenaw on 2/15/2016.
 */
public class Main {

	private static String INPUT_FOLDER_KEY = "Input-Folder";
	private static String INPUT_CLASSES_KEY = "Input-Classes";
	private static String DOT_PATH_KEY = "Dot-Path";

	private static String outputFile;

	private static String outputDirectoryPath;

	public static String getOutputPathKey() {
		return OUTPUT_PATH_KEY;
	}

	private static String OUTPUT_PATH_KEY = "Output-Directory";

	/**
	 * A string that represents where the GraphViz dot.exe file is.
	 */
	private static String dotPath;// = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot";;

	private static String sdEditPath;

	public static void main(String[] args) throws IOException, InterruptedException {
				Properties props = new Properties();
		FileInputStream in = new FileInputStream("./files/prop.properties");
		props.load(in);
		in.close();

		ArrayList<File> files = new ArrayList<>();
		Files.walk(Paths.get(props.getProperty(INPUT_FOLDER_KEY))).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {

				if(filePath.toString().endsWith(".class"))
				{
					files.add(new File(filePath.toString()));
				}
			}
		});

		outputDirectoryPath = props.getProperty(OUTPUT_PATH_KEY);
		outputFile =  props.getProperty(OUTPUT_PATH_KEY) + "test.png";

		UMLGraph graph = visitFiles(files, props.getProperty(INPUT_CLASSES_KEY, "").trim().split(","));
		dotPath = props.getProperty(DOT_PATH_KEY);
		generateUMLPNG(graph.toGraphVizString(), props.getProperty(OUTPUT_PATH_KEY));

		DesignParserFrame f = new DesignParserFrame("Design Parser", props.getProperty(OUTPUT_PATH_KEY) + "test.png", graph);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
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
	public static UMLGraph visitFiles(ArrayList<File> files, String[] javaClasses) throws IOException {
		//TODO: Add some checking to be sure that the files are .class and (more importantly) the javaClasses strings are valid.
		//TODO: CHeck for class not found
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		graph.addPatternDetector(new DecoratorPatternDetector());
		graph.addPatternDetector(new AdapterPatternDetector());
		graph.addPatternDetector(new CompositePatternDetector());
		for (File f : files) {
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
		}

		for (String f : javaClasses) {
			if(f.isEmpty())
				continue;
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}

		graph.generateArrows();
		graph.detectPatterns();
		return graph;
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
	public static void generateUMLPNG(String s, String outputPath) throws IOException, InterruptedException {
//		String filePath = outputPath + "test";
		PrintWriter writer = new PrintWriter(outputDirectoryPath + ".dot", "UTF-8");
		writer.println(s);
		writer.flush();
		writer.close();
		File f = new File(outputDirectoryPath + ".dot");
		while (!f.exists());
		String command = "\"" + dotPath + "\" -Tpng " + outputDirectoryPath + ".dot -o " + outputDirectoryPath + "test.png";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		pr.waitFor();
		pr.destroy();
	}
}
