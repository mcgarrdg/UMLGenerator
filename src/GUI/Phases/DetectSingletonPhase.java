package GUI.Phases;

import PatternDetectors.SingletonPatternDetector;
import Core.UMLItems.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectSingletonPhase extends APhase {

	private SingletonPatternDetector detector;

	public DetectSingletonPhase(UMLGraph g, Properties p) {
		super(g, p);
		this.detector = new SingletonPatternDetector();
		if(this.isActive()) {
			this.detector.setPatternColor(p.getProperty(this.detector.getPatternCatagoryName() + "-Color", this.detector.getPatternColor()));
			g.addPatternDetector(this.detector);
		}
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
	public IPhase restart(UMLGraph g, Properties p) {
		return new DetectSingletonPhase(g,p);
	}
}
