package PatternDetectors;

import Core.*;
import Core.UMLItems.UMLArrow;
import Core.UMLItems.UMLClass;
import Core.UMLItems.UMLField;
import Core.UMLItems.UMLMethod;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tiefenaw on 1/28/2016.
 */
public class AdapterPatternDetector implements IPatternDetector {

    //TODO Allow this to change?

    public static int getFieldUnusedNumThreshold() {
        return FIELD_UNUSED_NUM_THRESHOLD;
    }

    public static void setFieldUnusedNumThreshold(int fieldUnusedNumThreshold) {
        FIELD_UNUSED_NUM_THRESHOLD = fieldUnusedNumThreshold;
    }

    /**
     * This represents the number of times a field can not be used in a method in the class
     * and still have the class be considered an adapter. Does not include constructors.
     */
    public static int FIELD_UNUSED_NUM_THRESHOLD = 1;

    public static int getNamMethodsNotImplementedThreshold() {
        return NAM_METHODS_NOT_IMPLEMENTED_THRESHOLD;
    }

    public static void setNamMethodsNotImplementedThreshold(int namMethodsNotImplementedThreshold) {
        NAM_METHODS_NOT_IMPLEMENTED_THRESHOLD = namMethodsNotImplementedThreshold;
    }

    public static int NAM_METHODS_NOT_IMPLEMENTED_THRESHOLD = 1;

    public static String ADAPTER_COLOR = "#990000";

    private static String catagoryName = "Adapter";

    @Override
    public void detectPatterns(ArrayList<UMLClass> classList) {
        for(UMLClass classOne : classList)
        {
            //TODO This would change if adapters can extend classes, abstract or otherwise.
            //If the class extends anything other than Object, it isn't an adapter.
//            if(!classOne.getExtension().equals("java/lang/Object"))
//            {
//                continue;
//            }
            //Must extend or implement exactly one thing to be an adapter, and that thing must exist on our graph
            if(classOne.getAllExtendsOrImplements().size() != 1)
            {
                continue;
            }

            if(classOne.getFields().size() == 0)
            {
                continue;
            }

            boolean matchingFound = false;
            HashMap<String, Integer> numFoundMap = new HashMap<>();

            //If the class has a field of the same type that it extends or implements, it is not an adapter.
            for(String imp : classOne.getImplementations())
            {

//                boolean wasFound = false;
                for(UMLField field : classOne.getFields())
                {
//                    //TODO We may not want FullBaseDataType here, as an ArrayList of X would still work, which it shouldn't(???) with the adapter pattern.
                    if(field.getType().getFullName().equals(imp) || field.getType().getFullName().equals(classOne.getExtension()))
                    {
//                        System.out.println("FIELD: " + field.getType().getFullName());
//                        System.out.println("IMPLEMENTS: "+ imp);
                        matchingFound = true;
                        break;
                    }
                }
                if(matchingFound)
                {
//                    matchingFound = true;
                    break;
                }
//                numFoundMap.put(imp, 0);
            }
            if(matchingFound)
            {
//                System.out.println("Match found: " + classOne.getName());
                continue;
            }


            for(UMLField field : classOne.getFields())
            {
                //TODO We may not want FullBaseDataType here, as an ArrayList of X would still work, which it shouldn't(???) with the adapter pattern.
                numFoundMap.put(field.getType().getFullName(), 0);
            }
            int totalMethods = 0; //Number of non constructor methods.
            for(UMLMethod meth : classOne.getMethods())
            {
                //Ignore constructors.
                if(meth.getName().equals("init"))
                {
                    continue;
                }
                totalMethods++;
                for(TypeData used : meth.getClassesUsed())
                {
                    //TODO Again, not sure that we ewant the getFullBaseDataType.
                    if(numFoundMap.containsKey(used.getFullName()))
                    {
                        numFoundMap.replace(used.getFullName(), numFoundMap.get(used.getFullName()) + 1);
                    }
                    //TODO This doesn't account for the exception case or anything fancy.
                    //Exception case: Field not used in a method as that method cannot be supported by the class being adapted,
                    //so it throws an exception instead.
                }
            }

//            boolean numUsedOk = false;
//            for(Map.Entry<String, Integer> entry : numFoundMap.entrySet())
//            {
//                if(entry.getValue().intValue() < (totalMethods - FIELD_UNUSED_NUM_THRESHHOLD))
//                {
//                    numFoundMap.remove(entry.getKey());
////                    System.out.println("Here: " + classOne.getName());
////                    System.out.println(entry.getValue().intValue());
////                    System.out.println((totalMethods - FIELD_UNUSED_NUM_THRESHHOLD));
////                    numUsedOk = true;
//
////                    break;
//                }
//            }

            //There were too many instances of an implementation field not being used in a method.
//            if(!numUsedOk)
//            {
//                continue;
//            }

//            if()

            UMLClass superclass = classOne.getAllExtendsOrImplements().get(0);
            int implementedCount = 0;
            int superMethods = 0;
            for(UMLMethod m : superclass.getMethods())
            {
                if(!m.getName().equals("init"))
                {
                    if((m.getAccessType() & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE)
                    {
                        continue;
                    }
                    superMethods++;
                    for(UMLMethod m2 : classOne.getMethods())
                    {
                        if(m.sameSignature(m2))
                        {
                            implementedCount++;
                            break;
                        }
                    }
                }
            }

            if(superMethods - implementedCount > NAM_METHODS_NOT_IMPLEMENTED_THRESHOLD)
            {
                continue;
            }

            //TODO Uncomment this
            for(String type : numFoundMap.keySet()) {
                boolean found = false;
                for (UMLClass c : classList) {
//                    System.out.println(c.getName());
//                    System.out.println(type);
                    if (c.getName().equals(type) && c.isActive())
                    {
//                        System.out.println("Hello");
//                        if(c.getMethods())
                        found = true;
                    }
                }
                if(!found)
                {
                    numFoundMap.replace(type, -1);
                }
            }

            Iterator<Map.Entry<String, Integer>> it = numFoundMap.entrySet().iterator();
//            for(Map.Entry<String, Integer> entry : numFoundMap.entrySet())
            while(it.hasNext())
            {
                Map.Entry<String, Integer> entry = it.next();
                boolean wasFound = false;
                for(UMLClass c : classList)
                {
                    if(c.getName().equals(entry.getKey()))
                    {
                        wasFound = true;
                    }
                }

                if(entry.getValue().intValue() < (totalMethods - FIELD_UNUSED_NUM_THRESHOLD) || !wasFound)
                {
                    it.remove();
//                    numFoundMap.remove(entry.getKey());
//                    System.out.println("Here: " + classOne.getName());
//                    System.out.println(entry.getValue().intValue());
//                    System.out.println((totalMethods - FIELD_UNUSED_NUM_THRESHHOLD));
//                    numUsedOk = true;

//                    break;
                }
            }

            if(numFoundMap.isEmpty())
            {
                continue;
            }

//            boolean superclassExists = false;
//            for(String s : classOne.getImplementations())
//            {
                for(UMLArrow arrow : classOne.getUMLArrows())
                {
                    //TODO Is fullname what I really want?
                    if(arrow.getEndClass().getName().equals(superclass.getName()))
                    {
//                        arrow.setLabel("adapts");
                        arrow.setLabel("targets");
                        arrow.getEndClass().addPatternName("target");
                        arrow.getEndClass().addPatternCatagory(catagoryName);
                        arrow.getEndClass().setFillColor(ADAPTER_COLOR);
//                        superclassExists = true;
                        break;
                    }
                }
//            }

//            for(UMLArrow arrow : classOne.getUMLArrows())
//            {
//                //TODO Is fullname what I really want?
//                if(arrow.getEndClass().getName().equals(classOne.getExtension()))
//                {
////                        arrow.setLabel("adapts");
//                    arrow.setLabel("targets");
//                    arrow.getEndClass().addPatternName("target");
//                    arrow.getEndClass().addPatternCatagory(catagoryName);
//                    arrow.getEndClass().setFillColor(ADAPTER_COLOR);
//                    superclassExists = true;
//                    break;
//                }
//            }
//
//            if(!superclassExists)
//                return;
            for(UMLField field : classOne.getFields())
            {
                for(UMLArrow arrow : classOne.getUMLArrows())
                {
                    //TODO Is fullname what I really want?
                    if(arrow.getEndClass().getName().equals(field.getType().getFullName()))
                    {
                        arrow.setLabel("adapts");
                        arrow.getEndClass().addPatternName("adaptee");
                        arrow.getEndClass().addPatternCatagory(catagoryName);
                        arrow.getEndClass().setFillColor(ADAPTER_COLOR);
                        break;
                    }
                }

//                for(UMLClass classOne : classList)
//                {
//
//                }
//                //TODO We may not want FullBaseDataType here, as an ArrayList of X would still work, which it shouldn't(???) with the adapter pattern.
//                numFoundMap.put(field.getType().getFullName(), 0);
            }

            classOne.addPatternName("adapter");
            classOne.addPatternCatagory(catagoryName);
            classOne.setFillColor(ADAPTER_COLOR);
        }
    }

    @Override
    public String getPatternCatagoryName() {
        return catagoryName;
    }

    @Override
    public String getPatternColor() {
        return this.ADAPTER_COLOR;
    }
}
