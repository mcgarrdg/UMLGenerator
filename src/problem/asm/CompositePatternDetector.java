package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CompositePatternDetector implements IPatternDetector {

	@Override
	public void detectPatterns(ArrayList<UMLClass> classList) {
		for (UMLClass c1 : classList) {
			boolean listOfComponent = false;
			ArrayList<UMLClass> compositeSubset = new ArrayList<UMLClass>();
			for (UMLField f : c1.getFields()) {
				System.out.println();
				try {
					if (Collection.class.isAssignableFrom(Class.forName(f.getType().getFullName().replace('/', '.')))) {
//						for (UMLClass c2 : c1.getAllExtendsOrImplements()) {
//							// found composite object?
//							if (c2.getName().equals(f.getType().getExtendedName())) {
//								listOfComponent = true;
//								break;
//							}
//						}
						Class<?> clazz = Class.forName(c1.getName().replace('/', '.'));
						ArrayList<Class<?>> supers = new ArrayList<Class<?>>(Arrays.asList(clazz.getInterfaces()));
						Class<?> superClazz = clazz;
						while ((superClazz = superClazz.getSuperclass()) != null) {
							if (!supers.contains(superClazz)) {
								supers.add(superClazz);
							}
						}
						supers.remove(Object.class);
						for (Class<?> c2 : supers) {
							System.out.println(c2.getCanonicalName());
							System.out.println(f.getType().getFullBaseDataType());
						}
						if (listOfComponent) {
							// find component and leaves

						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
