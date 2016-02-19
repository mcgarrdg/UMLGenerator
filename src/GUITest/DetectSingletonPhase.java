package GUITest;

import PatternDetectors.SingletonPatternDetector;
import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectSingletonPhase extends APhase {

	SingletonPatternDetector detector;

	public DetectSingletonPhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new SingletonPatternDetector();
	}

	@Override
	public String getPhaseName() {
		return "Singleton-Detector";
	}

	@Override
	public String getPhaseDescription() {
		return "Detecting Singletons...";
	}

	@Override
	public void execute() {
		this.detector.detectPatterns(this.graph.getClasses());
	}

	@Override
	public void restart(UMLGraph g, Properties p) {

	}
}
