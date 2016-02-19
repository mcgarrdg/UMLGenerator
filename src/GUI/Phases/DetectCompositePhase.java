package GUI.Phases;

import PatternDetectors.CompositePatternDetector;
import Core.UMLItems.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectCompositePhase extends APhase {

	private CompositePatternDetector detector;

	public DetectCompositePhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new CompositePatternDetector();
		if(this.isActive())
			g.addPatternDetector(this.detector);
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
	public IPhase restart(UMLGraph g, Properties p) {
		return new DetectCompositePhase(g, p);
	}
}
