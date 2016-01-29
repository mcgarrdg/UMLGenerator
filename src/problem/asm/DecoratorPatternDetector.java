package problem.asm;

import java.util.ArrayList;

public class DecoratorPatternDetector implements IPatternDetector {

	private boolean requireAbstractDecorator;
	private static String patternColor = "#09FF00";

	public DecoratorPatternDetector() {
		requireAbstractDecorator = true;
	}

	public void requireAbstractDecoractor(boolean val) {
		this.requireAbstractDecorator = val;
	}

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {
		for (UMLClass c1 : classList) {
			boolean hasAbstractComponent = false;
			boolean areConcreteComponents = false;
			boolean hasConcreteSubclasses = false;
			boolean hasSuperField = false;
			String superClass;
			UMLClass abstractComponent = null;
			ArrayList<UMLClass> decoratorSubset = new ArrayList<UMLClass>();
			superClass = c1.getExtension();
			decoratorSubset.add(c1);

			for (UMLField f : c1.getFields()) {
				if (f.getType().getFullName().equals(superClass)) {
					if (!requireAbstractDecorator) {
						hasConcreteSubclasses = true;
					}
					hasSuperField = true;
				}
			}
			for (UMLClass c2 : classList) {
				if (!c2.equals(c1)) {
					// check for asbstactComponent
					if (c2.getName().equals(superClass)) {
						abstractComponent = c2;
						hasAbstractComponent = true;
					}

					// check for decorator subclasses
					if (c2.getExtension().equals(c1.getName())) {
						hasConcreteSubclasses = true;
						decoratorSubset.add(c2);
						for (UMLField f : c2.getFields()) {
							if (f.getType().getFullName().equals(superClass)) {
								hasSuperField = true;
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

			if (hasSuperField && hasAbstractComponent && areConcreteComponents && hasConcreteSubclasses) {
				for (UMLClass c2 : decoratorSubset) {
					c2.setFillColor(patternColor);
					c2.addPatternName("decorator");
					for (UMLArrow arrow : c2.getUMLArrows()) {
						if (arrow.getEndClass().getName().equals(superClass) && arrow.isAssociationArrow()) {
							arrow.setLabel("decorates");
						}
					}
				}
				if (abstractComponent != null) {
					abstractComponent.setFillColor(patternColor);
					abstractComponent.addPatternName("component");
				}
			}
		}
	}

}
