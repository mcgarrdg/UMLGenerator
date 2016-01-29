package problem.lib;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.client.IteratorToEnumerationAdapter;

public class LinearTransformerTest {
	LinearTransformer<String> transformer;
	String expected;
	int size;
	
	@Before
	public void setUp() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Transformation in progress ...\n");

		size = 5;
		ArrayList<String> vect = new ArrayList<String>();
		for(int i = 1; i <= size; ++i) {
			String elem = "Tick Tick " + i; 
			vect.add(elem);
			buffer.append(i + " : " + elem + "\n");
		}
		
		buffer.append("Transformation complete!\n");
		expected = buffer.toString().trim();

		Enumeration<String> etion = new IteratorToEnumerationAdapter<String>(vect.iterator());
		transformer = new LinearTransformer<String>(etion);
	}

	@After
	public void tearDown() throws Exception {
		transformer = null;
	}

	@Test
	public final void testTransform() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		transformer.transform(out);		
		
		String actual = out.toString().trim();
		assertEquals(expected, actual);
	}
}
