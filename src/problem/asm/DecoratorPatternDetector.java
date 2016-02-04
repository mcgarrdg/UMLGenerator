package problem.asm;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DecoratorPatternDetector implements IPatternDetector {

	private static String patternColor = "#09FF00";

	public DecoratorPatternDetector() {
	}

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {
		for (UMLClass c1 : classList) {
			if (!c1.getName().equals("java/lang/Object")) {
				String component = c1.getExtension();
				boolean selfDecorator = false;
				UMLClass realComponent = null;
				ArrayList<UMLClass> decoratorSubset = new ArrayList<UMLClass>();

				boolean realComponentSpecified = false;
				for (UMLClass c2 : classList) {
					// c2 is c1
					if (c2.equals(c1)) {
						for (UMLField f : c1.getFields()) {
							if (f.getType().getFullName().equals(component) && !component.equals("java/lang/Object")) {
								for (UMLMethod m : c1.getMethods()) {
									if(m.getName().equals("init")) {
										for(TypeData d : m.getArgumentData()) {
											if (d.getFullName().equals(component)) {
												selfDecorator = false;
												decoratorSubset.add(c2);
												break;
											}
										}
									}
								}
							} else if (f.getType().getFullName().equals(c1.getName())) {
								for (UMLMethod m : c1.getMethods()) {
									if(m.getName().equals("init")) {
										for(TypeData d : m.getArgumentData()) {
											if (d.getFullName().equals(c1.getName())) {
												selfDecorator = true;
												decoratorSubset.add(c2);
												break;
											}
										}
									}
								}
							}
						}
					} else if (c2.getName().equals(component)) {
						realComponent = c2;
						realComponentSpecified = true;

					}
					// c2 extends c1
					else if (c2.getExtension().equals(c1.getName())) {
						for (UMLField f : c2.getFields()) {
							if (f.getType().getFullName().equals(component)) {
								decoratorSubset.add(c2);
							}
						}
					}
				}

				if (!realComponentSpecified) {
					// try to specify component
					if (!component.equals("java/lang/Object")) {
						UMLGraph newGraph = new UMLGraph("Test_UML", "BT");
						newGraph.addPatternDetector(new DecoratorPatternDetector());
						ClassReader reader;
						try {
							reader = new ClassReader(component);

							ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newGraph);
							ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newGraph);
							ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newGraph);

							reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

						} catch (IOException e) {
							e.printStackTrace();
						}

						newGraph.generateArrows();
						newGraph.detectPatterns();
						ArrayList<UMLClass> componentList = newGraph.getClasses();
						if (componentList.get(0).getPatternNames().contains("decorator")) {
							selfDecorator = false;
							decoratorSubset.add(c1);
						}

					}
				}

				if (!decoratorSubset.isEmpty()) {
					for (UMLClass c2 : decoratorSubset) {
						c2.addPatternName("decorator");
						c2.setFillColor(patternColor);
						if(c2.equals(c1) && selfDecorator) {
							c2.addPatternName("component");
						}
						for (UMLArrow arrow : c2.getUMLArrows()) {
							UMLClass start = c2;
							UMLClass end = realComponent;
							if (selfDecorator) {
								end = c1;
							}
							if (end != null && arrow.isAssociationArrow() && arrow.getStartClass().equals(start)
									&& arrow.getEndClass().equals(end)) {
								arrow.setLabel("decorates");
							}
						}

					}
					if (realComponentSpecified && !selfDecorator) {
						realComponent.addPatternName("component");
						realComponent.setFillColor(patternColor);
					}
				}
			}
		}
		for (UMLClass c1 : classList) {
			for (UMLClass c2 : c1.getAllExtendsOrImplements()) {
				if (c2.getPatternNames().contains("decorator")) {
					c1.addPatternName("decorator");
					c1.setFillColor(patternColor);
				}
			}

		}

	}

}
