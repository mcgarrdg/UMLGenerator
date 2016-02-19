package GUITest;

import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class GenerateArrowsPhase extends APhase {

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
		System.out.println(graph.getName());
		this.graph.generateArrows();
	}

	@Override
	public IPhase restart(UMLGraph g, Properties p) {
		return new GenerateArrowsPhase(g,p);
	}
}
