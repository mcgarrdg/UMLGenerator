package GUI.Phases;

import Core.UMLItems.UMLGraph;

import java.util.Properties;

/**
 * Created by tiefenaw on 2/18/2016.
 */
public interface IPhase {
	String getPhaseName();
	String getPhaseDescription();
	void execute();
	IPhase restart(UMLGraph g, Properties p);
	public boolean isActive();
}
