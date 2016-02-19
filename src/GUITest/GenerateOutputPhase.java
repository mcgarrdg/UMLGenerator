package GUITest;

import problem.asm.UMLGraph;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public class GenerateOutputPhase extends APhase {

	public GenerateOutputPhase(UMLGraph g, Properties p)
	{
		super(g,p);
	}

	@Override
	public String getPhaseName() {
		return "Generate-Output";
	}

	@Override
	public String getPhaseDescription() {
		return "Generating output...";
	}

	@Override
	public void execute() {
		try {
			Utilities.generateUMLPNG(graph.toGraphVizString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void restart(UMLGraph g, Properties p) {
		this.graph = g;
		this.props = p;
	}
}
