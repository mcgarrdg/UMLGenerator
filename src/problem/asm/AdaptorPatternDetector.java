package problem.asm;

import java.util.ArrayList;

/**
 * Created by tiefenaw on 1/28/2016.
 */
public class AdaptorPatternDetector implements IPatternDetector {
    @Override
    public void detectPatterns(ArrayList<UMLClass> classList) {
        for(UMLClass classOne : classList)
        {
            //The class cannot extend something and be an adaptor.
//            classOne.getExtension() !=
        }
    }
}
