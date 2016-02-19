package GUITest;

import problem.asm.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public abstract class APhase implements IPhase{
	protected UMLGraph graph;
	protected Properties props;
	protected boolean isActive;

	public APhase(UMLGraph g, Properties p)
	{
		this.graph = g;
		this.props = p;
		this.isActive = false;
		if(p != null) {
			for (String s : p.getProperty(Utilities.PHASE_KEY).split(Utilities.SEPARATOR)) {
				if (s.equals(this.getPhaseName())) {
					this.isActive = true;
					break;
				}
			}
		}
	}

	@Override
	public boolean isActive()
	{
		return this.isActive;
	}
}
