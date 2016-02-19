package GUITest;

import PatternDetectors.*;
import problem.asm.UMLGraph;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class Utilities {
	private ArrayList<IPhase> restartPhases;

	public static String INPUT_FOLDER_KEY = "Input-Folder";
	public static String INPUT_CLASSES_KEY = "Input-Classes";
	public static String DOT_PATH_KEY = "Dot-Path";
	public static String PHASE_KEY = "Phases";
	public static String SEPARATOR = ", ";

	public static String ADAPTER_UNUSED_THRESHOLD_KEY = "Adapter-Unused-Threshold";
	public static String ADAPTER_UNIMPLEMENTED_THRESHOLD_KEY = "Adapter-Unimplemented-Threshold";

	public static String outputFile;

	public static String outputDirectoryPath;

	public static String dotPath;

	public static String filename;

//	public static String getOutputPathKey() {
//		return OUTPUT_PATH_KEY;
//	}

	public static String OUTPUT_PATH_KEY = "Output-Directory";

		public static void generateUMLPNG(String graphVizString) throws IOException, InterruptedException {
//		String filePath = outputPath + "test";
		PrintWriter writer = new PrintWriter(outputDirectoryPath + ".dot", "UTF-8");
		writer.println(graphVizString);
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

//	public Utilities(ArrayList<IPhase> possiblePhases, String propertyFilePath) throws IOException {
//		this.restartPhases = new ArrayList<>();
////		this.restartPhases = restartPhases;
//
//		Properties props = new Properties();
//		FileInputStream in = new FileInputStream(propertyFilePath);
//		props.load(in);
//		in.close();
//
//		ArrayList<File> files = new ArrayList<>();
//		Files.walk(Paths.get(props.getProperty(INPUT_FOLDER_KEY))).forEach(filePath -> {
//			if (Files.isRegularFile(filePath)) {
//
//				if(filePath.toString().endsWith(".class"))
//				{
//					files.add(new File(filePath.toString()));
//				}
//			}
//		});
//
//		outputDirectoryPath = props.getProperty(OUTPUT_PATH_KEY);
//		outputFile =  props.getProperty(OUTPUT_PATH_KEY) + "test.png";
//
////		ArrayList<IPatternDetector> patDetect = new ArrayList<>();
////		patDetect.add(new SingletonPatternDetector());
////		patDetect.add(new DecoratorPatternDetector());
////		patDetect.add(new AdapterPatternDetector());
////		patDetect.add(new CompositePatternDetector());
////
////		UMLGraph graph = visitFiles(files, props.getProperty(INPUT_CLASSES_KEY, "").trim().split(","), patDetect);
////		dotPath = props.getProperty(DOT_PATH_KEY);
////		generateUMLPNG(graph.toGraphVizString());
//
//		UMLGraph graph = new UMLGraph("UML", "BT");
//
//		DesignParserFrame f = new DesignParserFrame("Design Parser", props.getProperty(OUTPUT_PATH_KEY) + "test.png", graph);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setVisible(true);
//
//	}
//
//
//
//
//	public void restart()
//	{
//		for(IPhase p : this.restartPhases)
//		{
//			p.execute();
//		}
//	}

}
