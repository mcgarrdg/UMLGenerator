package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;

public class DecoratorPatternDetector implements IPatternDetector {

	public DecoratorPatternDetector() {

	}

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {
		for (UMLClass c1 : classList) {
			// check to see if class is the abstractDecorator, if not, we will
			// procedd to next class.
			boolean hasAbstractComponent = false;
			boolean isAbstract = false;
			boolean areConcreteComponents = false;
			boolean hasConcreteSubclasses = false;
			String superClass;
			UMLClass abstractComponent = null;
			ArrayList<UMLClass> decoratorSubset = new ArrayList<UMLClass>();
			ArrayList<UMLArrow> decoratesArrows = new ArrayList<UMLArrow>();
			// if ((Opcodes.ACC_ABSTRACT & c1.getAccessType()) ==
			// Opcodes.ACC_ABSTRACT
			// && !c1.getExtension().equals("java/lang/Object")) {
			//
			superClass = c1.getExtension();
			decoratorSubset.add(c1);
			isAbstract = true;
//			for (UMLArrow arrow : c1.getUMLArrows()) {
//				if (arrow.isAssociationArrow() && arrow.getEndClass().getName().equals(superClass)) {
//					decoratesArrows.add(arrow);
//				}
//			}
			for (UMLClass c2 : classList) {
				if (!c2.equals(c1)) {
					// check for asbstactComponent
					if (c2.getName().equals(superClass)) {
						abstractComponent = c2;
						hasAbstractComponent = true;
					}

					// check for decorator subclasses
					if (c2.getExtension().equals(c1.getName())) {
//						for (UMLArrow arrow : decoratesArrows) {
//							if (arrow.getStartClass().getName().equals(c2.getExtension())) {
//								hasConcreteSubclasses = true;
//								decoratorSubset.add(c2);
//							}
//						}
						for (UMLArrow arrow : c2.getUMLArrows()) {
							// if (arrow.isAssociationArrow() &&
							if (arrow.getEndClass().getName().equals(superClass)) {
								hasConcreteSubclasses = true;
								decoratorSubset.add(c2);
								decoratesArrows.add(arrow);
							}
						}
					}

					// check for abstractComponent subclasses that aren't
					// the abstract decorator.
					if (c2.getExtension().equals(superClass)) {
						areConcreteComponents = true;
						hasAbstractComponent = true;
					}
				}
			}
			// }

			if (hasAbstractComponent && isAbstract && areConcreteComponents && hasConcreteSubclasses) {
				for (UMLClass c2 : decoratorSubset) {
					c2.setFillColor("#09FF00");
					c2.addPatternName("decorator");
				}
				for (UMLArrow arrow : decoratesArrows) {
					arrow.setLabel("decorates");
				}
				if (abstractComponent != null) {
					abstractComponent.setFillColor("#09FF00");
					abstractComponent.addPatternName("component");
				}
			}
		}
	}

}
