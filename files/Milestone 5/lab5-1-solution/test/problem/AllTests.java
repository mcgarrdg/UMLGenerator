package problem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import problem.client.IteratorToEnumerationAdapterTest;
import problem.lib.LinearTransformerTest;

@RunWith(Suite.class)
@SuiteClasses({
	IteratorToEnumerationAdapterTest.class,
	LinearTransformerTest.class
})
public class AllTests {

}
