package GUITest;

import PatternDetectors.DecoratorPatternDetector;
import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectDecoratorPhase extends APhase {

	DecoratorPatternDetector detector;

	public DetectDecoratorPhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new DecoratorPatternDetector();
	}

	@Override
	public String getPhaseName() {
		return "Decorator-Detector";
	}

	@Override
	public String getPhaseDescription() {
		return "Detecting Decorators...";
	}

	@Override
	public void execute() {
		this.detector.detectPatterns(this.graph.getClasses());
	}

	@Override
	public void restart(UMLGraph g, Properties p) {

	}
}
