package GUITest;

import PatternDetectors.AdapterPatternDetector;
import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class DetectAdapterPhase extends APhase {

	private AdapterPatternDetector apd;

	public DetectAdapterPhase(UMLGraph g, Properties p) {
		super(g, p);
		this.apd = new AdapterPatternDetector();
		apd.setFieldUnusedNumThreshold(Integer.parseInt(p.getProperty(Utilities.ADAPTER_UNUSED_THRESHOLD_KEY, "" + AdapterPatternDetector.getFieldUnusedNumThreshold())));
		apd.setNamMethodsNotImplementedThreshold(Integer.parseInt(p.getProperty(Utilities.ADAPTER_UNIMPLEMENTED_THRESHOLD_KEY, "" + AdapterPatternDetector.getNamMethodsNotImplementedThreshold())));
	}

	@Override
	public String getPhaseName() {
		return "Adapter-Detector";
	}

	@Override
	public String getPhaseDescription() {
		return "Detecting Adapters...";
	}

	@Override
	public void execute() {
		apd.detectPatterns(graph.getClasses());
	}

	@Override
	public void restart(UMLGraph g, Properties p) {

	}
}
