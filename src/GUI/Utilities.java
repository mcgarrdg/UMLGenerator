package GUI;

import GUI.Phases.IPhase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
	public static String outputDirectoryPath;
	public static String dotPath;

	public static String OUTPUT_PATH_KEY = "Output-Directory";

		public static void generateUMLPNG(String graphVizString) throws IOException, InterruptedException {
		PrintWriter writer = new PrintWriter(outputDirectoryPath + ".dot", "UTF-8");
		writer.println(graphVizString);
		writer.flush();
		writer.close();
		File f = new File(outputDirectoryPath + ".dot");
		while (!f.exists());
		String command = "\"" + dotPath + "\" -Tpng " + outputDirectoryPath + ".dot -o " + outputDirectoryPath + ".png";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		pr.waitFor();
		pr.destroy();
	}
}
