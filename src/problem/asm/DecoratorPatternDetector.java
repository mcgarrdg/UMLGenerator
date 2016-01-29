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
			if ((Opcodes.ACC_ABSTRACT & c1.getAccessType()) == Opcodes.ACC_ABSTRACT
					&& !c1.getExtension().equals("java/lang/Object")) {
				System.out.println();
				
				superClass = c1.getExtension();
				decoratorSubset.add(c1);
				isAbstract = true;
				for (UMLClass c2 : classList) {
					if (!c2.equals(c1)) {
						// check for asbstactComponent
						if (c2.getName().equals(superClass)) {
							System.out.println("Found abstact component " + c2.getName());
//							decoratorSubset.add(c2);
							abstractComponent = c2;
							hasAbstractComponent = true;
						}

						// check for decorator subclasses
						if (c2.getExtension().equals(c1.getName())) {
							System.out.println("possible decorator " + c2.getName());
							System.out.println(c2.getUMLArrows().isEmpty());
							for (UMLArrow arrow : c2.getUMLArrows()) {
								System.out.println(arrow.isAssociationArrow() + " arrow : " + arrow.getEndClass());
								if (arrow.isAssociationArrow() && arrow.getEndClass().getName().equals(superClass)) {
									System.out.println("Found decoractor subclass " + c2.getName());
									hasConcreteSubclasses = true;
									decoratorSubset.add(c2);
								}
							}
						}

						// check for abstractComponent subclasses that aren't
						// the abstract decorator.
						if (c2.getExtension().equals(superClass)) {
							System.out.println("Found concrete component " + c2.getName());
							areConcreteComponents = true;
							hasAbstractComponent = true;
//							decoratorSubset.add(c2);
						}
					}
				}

			}
			if(hasAbstractComponent && isAbstract && areConcreteComponents && hasConcreteSubclasses) {
				System.out.println("yeah");
				for(UMLClass c2 : decoratorSubset) {
					c2.setFillColor("#09FF00");
					c2.addPatternName("decorator");
				}
				if(abstractComponent != null) {
					abstractComponent.setFillColor("#09FF00");
					abstractComponent.addPatternName("component");
				}
			}
		}
	}

}
