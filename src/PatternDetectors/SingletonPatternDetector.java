package PatternDetectors;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import Core.UMLItems.UMLClass;
import Core.UMLItems.UMLField;
import Core.UMLItems.UMLMethod;

public class SingletonPatternDetector implements IPatternDetector {

	private static String catagoryName = "Singleton";

	private static String patternColor = UMLClass.COLOR_BLUE;

	public SingletonPatternDetector() {

	}

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {

		for (UMLClass c : classList) {
			// check fields
			boolean privateConstructor = true;
			boolean getInstanceMethod = false;
			boolean instanceField = false;

			for (UMLMethod m : c.getMethods()) {
				// check constructors
				if (m.getName().equals("init")) {
					if (!((m.getAccessType() & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE)) {
						privateConstructor = false;
						break;
					}
				}

				// check other methods
				else {
					if (((m.getAccessType() & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE));
					else if (((m.getAccessType() & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED));
					else if (m.getReturnType().getFullName().equals(c.getName())) {
						getInstanceMethod = true;
						break;
					}
				}

			}
			if (privateConstructor && getInstanceMethod) {

				for (UMLField f : c.getFields()) {
					if (((f.getAccessType() & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC)
							&& (f.getType().getFullName().equals(c.getName()))) {
						instanceField = true;
						break;
					}
				}
			}
			if (privateConstructor && getInstanceMethod && instanceField) {
				// is a singleton
				c.setColor(patternColor);
				c.addPatternName("Singleton");
				c.addPatternCatagory(catagoryName);
			}

		}

	}

	@Override
	public String getPatternCatagoryName() {
		return catagoryName;
	}

	@Override
	public String getPatternColor() {
		return patternColor;
	}
}
