package PatternDetectors;

import Core.UMLItems.UMLClass;

import java.util.ArrayList;

public interface IPatternDetector {
	void detectPatterns(ArrayList<UMLClass> classList);

	String getPatternCatagoryName();

	String getPatternColor();
}
