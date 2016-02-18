package PatternDetectors;

import problem.asm.UMLClass;

import java.util.ArrayList;

public interface IPatternDetector {
	void detectPatterns(ArrayList<UMLClass> classList);

	String getPatternCatagoryName();
}
