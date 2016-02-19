package GUITest;

import PatternDetectors.CompositePatternDetector;
import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectCompositePhase extends APhase {

	CompositePatternDetector detector;

	public DetectCompositePhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new CompositePatternDetector();
	}

	@Override
	public String getPhaseName() {
		return "Composite-Detector";
	}

	@Override
	public String getPhaseDescription() {
		return "Detecting Composites...";
	}

	@Override
	public void execute() {
		this.detector.detectPatterns(this.graph.getClasses());
	}

	@Override
	public void restart(UMLGraph g, Properties p) {

	}
}
