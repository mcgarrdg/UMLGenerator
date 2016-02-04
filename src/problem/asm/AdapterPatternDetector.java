package problem.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tiefenaw on 1/28/2016.
 */
public class AdapterPatternDetector implements IPatternDetector {

    //TODO Allow this to change?
	/**
     * This represents the number of times a field can not be used in a method in the class
     * and still have the class be considered an adapter. Does not include constructors.
     */
    public static int FIELD_UNUSED_NUM_THRESHHOLD = 1;

    public static String ADAPTER_COLOR = "#990000";

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
                        System.out.println("FIELD: " + field.getType().getFullName());
                        System.out.println("IMPLEMENTS: "+ imp);
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

            boolean numUsedOk = true;
            for(Map.Entry<String, Integer> entry : numFoundMap.entrySet())
            {
                if(entry.getValue().intValue() < (totalMethods - FIELD_UNUSED_NUM_THRESHHOLD))
                {
//                    System.out.println("Here: " + classOne.getName());
//                    System.out.println(entry.getValue().intValue());
//                    System.out.println((totalMethods - FIELD_UNUSED_NUM_THRESHHOLD));
                    numUsedOk = false;
                    break;
                }
            }
            //There were too many instances of an implementation field not being used in a method.
            if(!numUsedOk)
            {
                continue;
            }

            for(UMLField field : classOne.getFields())
            {
                for(UMLArrow arrow : classOne.getUMLArrows())
                {
                    //TODO Is fullname what I really want?
                    if(arrow.getEndClass().getName().equals(field.getType().getFullName()))
                    {
                        arrow.setLabel("adapts");
                        arrow.getEndClass().addPatternName("adaptee");
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

            for(String s : classOne.getImplementations())
            {
                for(UMLArrow arrow : classOne.getUMLArrows())
                {
                    //TODO Is fullname what I really want?
                    if(arrow.getEndClass().getName().equals(s))
                    {
//                        arrow.setLabel("adapts");
                        arrow.getEndClass().addPatternName("target");
                        arrow.getEndClass().setFillColor(ADAPTER_COLOR);
                        break;
                    }
                }
            }
            classOne.addPatternName("adapter");
            classOne.setFillColor(ADAPTER_COLOR);
        }
    }
}
