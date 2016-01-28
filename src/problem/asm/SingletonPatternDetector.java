package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;

public class SingletonPatternDetector implements IPatternDetector {

	public SingletonPatternDetector() {

	}

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {

		for (UMLClass c : classList) {
			// check fields
			boolean privateConstructor = true;
			boolean getInstancemMethod = false;
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
						getInstancemMethod = true;
						break;
					}
				}

			}
			if (privateConstructor && getInstancemMethod) {

				for (UMLField f : c.getFields()) {
					if (((f.getAccessType() & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC)
							&& (f.getType().getFullName().equals(c.getName()))) {
						instanceField = true;
						break;
					}
				}
			}
			if (privateConstructor && getInstancemMethod && instanceField) {
				// is a singleton
				c.setColor(UMLClass.COLOR_BLUE);
				c.addPatternName("Singleton");
			}

		}

	}
}
