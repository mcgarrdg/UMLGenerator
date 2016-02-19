package GUITest;

import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class GenerateArrowsPhase extends APhase {
	UMLGraph graph;

	public GenerateArrowsPhase(UMLGraph g, Properties p)
	{
		super(g,p);
	}

	@Override
	public String getPhaseName() {
		return "Generate-Arrows";
	}

	@Override
	public String getPhaseDescription() {
		return "Generating Arrows...";
	}

	@Override
	public void execute() {
		this.graph.generateArrows();
	}

	@Override
	public void restart(UMLGraph g, Properties p) {
		this.graph = g;
		this.props = p;
	}
}
