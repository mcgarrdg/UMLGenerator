package PatternDetectors;

import problem.asm.UMLClass;
import problem.asm.UMLField;

import java.util.ArrayList;
import java.util.Collection;

public class CompositePatternDetector implements IPatternDetector {

	private static String patternColor = "#FFFF00";

	private static String catagoryName = "Composite";
	
	public CompositePatternDetector() {
		
	}
	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {
		for (UMLClass c1 : classList) {
			boolean listOfComponent = false;
			ArrayList<UMLClass> compositeSubset = new ArrayList<UMLClass>();
			String component = "";
			UMLClass realComponent = null;
			for (UMLField f : c1.getFields()) {
				//simple if to remove primitives
				if (f.getType().getFullName().contains("/")) {
					try {
						if (f.getType().isArray() || 
								Collection.class.isAssignableFrom(Class.forName(f.getType().getFullName().replace('/', '.')))) {
							for (UMLClass c2 : c1.getAllExtendsOrImplements()) {
//								System.out.println(f.getType().getFullBaseDataType());
								if (c2.getName().equals(f.getType().getFullBaseDataType())) {
									listOfComponent = true;
									component = c2.getName();
									break;
								}
							}
							// for (Class<?> c2 : getSupers(c1)) {
							// if
							// (f.getType().getFullBaseDataType().equals(c2.getCanonicalName()))
							// {
							// listOfComponent = true;
							// component = c2.getCanonicalName();
							// break;
							// }
							// }
							if (listOfComponent) {
								// find component
								for (UMLClass c2 : classList) {
									if (c2.getName().equals(component)) {
										realComponent = c2;
										break;
									}
								}
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (realComponent != null) {
					for (UMLClass c2 : classList) {
						if (c1.equals(c2) || c2.checkExtendsOrImplements(c1)) {
							compositeSubset.add(c2);
							c2.addPatternName("composite");
							c2.addPatternCatagory(catagoryName);
							c2.setFillColor(patternColor);
						} else if (c2.checkExtendsOrImplements(realComponent)) {
							compositeSubset.add(c2);
							c2.addPatternName("leaf");
							c2.addPatternCatagory(catagoryName);
							c2.setFillColor(patternColor);
						}
					}
					realComponent.addPatternName("component");
					realComponent.addPatternCatagory(catagoryName);
					realComponent.setFillColor(patternColor);
				}
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

//	public ArrayList<Class<?>> getSupers(UMLClass c1) throws ClassNotFoundException {
//		Class<?> clazz = Class.forName(c1.getName().replace('/', '.'));
//		ArrayList<Class<?>> supers = new ArrayList<Class<?>>(Arrays.asList(clazz.getInterfaces()));
//		Class<?> superClazz = clazz;
//		while ((superClazz = superClazz.getSuperclass()) != null) {
//			if (!supers.contains(superClazz)) {
//				supers.add(superClazz);
//			}
//		}
//		supers.remove(Object.class);
//		return supers;
//	}

}
