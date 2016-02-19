package GUI.Phases;

import PatternDetectors.DecoratorPatternDetector;
import Core.UMLItems.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectDecoratorPhase extends APhase {

	private DecoratorPatternDetector detector;

	public DetectDecoratorPhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new DecoratorPatternDetector();
		if(this.isActive())
			g.addPatternDetector(this.detector);
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
	public IPhase restart(UMLGraph g, Properties p) {
		return new DetectDecoratorPhase(g, p);
	}


}
