package Client;

import GUI.*;
import GUI.Phases.*;
import Core.UMLItems.UMLGraph;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tiefenaw on 2/15/2016.
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		UMLGraph g = new UMLGraph("UML", "BT");
		ArrayList<IPhase> phases = new ArrayList<>();
		phases.add(new LoadPhase(g, null));
		phases.add(new GenerateArrowsPhase(g,null));
		phases.add(new DetectCompositePhase(g,null));
		phases.add(new DetectSingletonPhase(g, null));
		phases.add(new DetectDecoratorPhase(g, null));
		phases.add(new GenerateOutputPhase(g, null));
		LandingScreenFrame  s = new LandingScreenFrame("Landing", phases, g);
		s.setVisible(true);
	}
}
