package GUITest;

import Visitors.ClassDeclarationVisitor;
import Visitors.ClassFieldVisitor;
import Visitors.ClassMethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import problem.asm.UMLGraph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class LoadPhase extends APhase {
//	private ArrayList<File> files;
//	private String[] javaClasses;
//	private ArrayList<IPatternDetector> patternDetectors; //I don't think we actually need this, but lets keep it for now.
//	private UMLGraph graph;

	public LoadPhase(UMLGraph g, Properties p)
	{
		super(g,p);


//		outputDirectoryPath = props.getProperty(OUTPUT_PATH_KEY);
//		outputFile =  props.getProperty(OUTPUT_PATH_KEY) + "test.png";

//		ArrayList<IPatternDetector> patDetect = new ArrayList<>();
//		patDetect.add(new SingletonPatternDetector());
//		patDetect.add(new DecoratorPatternDetector());
//		patDetect.add(new AdapterPatternDetector());
//		patDetect.add(new CompositePatternDetector());
//
//		UMLGraph graph = visitFiles(files, props.getProperty(INPUT_CLASSES_KEY, "").trim().split(","), patDetect);
//		dotPath = props.getProperty(DOT_PATH_KEY);
//		generateUMLPNG(graph.toGraphVizString());

//		this.files = files;
//		this.javaClasses = javaClasses;
//		this.patternDetectors = patternDetectors;
//		this.graph = graph;
	}

	@Override
	public String getPhaseName() {
		return "Load";
	}

	@Override
	public String getPhaseDescription() {
		return "Loading...";
	}

	@Override
	public void execute() {
		//TODO: Add some checking to be sure that the files are .class and (more importantly) the javaClasses strings are valid.
		//TODO: CHeck for class not found
//		UMLGraph graph = new UMLGraph("Test_UML", "BT"); //TODO Pass these thigns in as arguments?
//		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
//		graph.addPatternDetector(new DecoratorPatternDetector());
//		graph.addPatternDetector(new AdapterPatternDetector());
//		graph.addPatternDetector(new CompositePatternDetector());
//		for (IPatternDetector p : patternDetectors)
//		{
//			graph.addPatternDetector(p);
//		}
		ArrayList<File> files = new ArrayList<>();
		try {
			Files.walk(Paths.get(props.getProperty(Utilities.INPUT_FOLDER_KEY))).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					if(filePath.toString().endsWith(".class"))
					{
						files.add(new File(filePath.toString()));
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

//		this.files = files;
//		this.javaClasses = props.getProperty(Utilities.INPUT_CLASSES_KEY, "").trim().split(",");
		String[] javaClasses = props.getProperty(Utilities.INPUT_CLASSES_KEY, "").trim().split(",");

		for (File f : files) {
			InputStream in = null;
			try {
				in = new FileInputStream(f);
				ClassReader reader = new ClassReader(in);

				ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (String f : javaClasses) {
			if(f.isEmpty())
				continue;
			ClassReader reader = null;
			try {
				reader = new ClassReader(f);
			} catch (IOException e) {

				e.printStackTrace();
			}

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
	}

	@Override
	public void restart(UMLGraph g, Properties p) {
//		this = new LoadPhase(g,p);
		this.graph = g;
		this.props = p;
//
//		ArrayList<File> files = new ArrayList<>();
//		try {
//			Files.walk(Paths.get(props.getProperty(Utilities.INPUT_FOLDER_KEY))).forEach(filePath -> {
//				if (Files.isRegularFile(filePath)) {
//
//					if(filePath.toString().endsWith(".class"))
//					{
//						files.add(new File(filePath.toString()));
//					}
//				}
//			});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		this.files = files;
//		this.javaClasses = props.getProperty(Utilities.INPUT_CLASSES_KEY, "").trim().split(",");
	}
}
