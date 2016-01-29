package problem.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.Test;

public class IteratorToEnumerationAdapterTest {

	@Test
	public final void testHasMoreElementsEmpty() {
		ArrayList<String> list = new ArrayList<String>();
		IteratorToEnumerationAdapter<String> adapter = new IteratorToEnumerationAdapter<String>(list.iterator());
		assertFalse(adapter.hasMoreElements());
	}

	@Test
	public final void testHasMoreElementsNonEmpty() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		IteratorToEnumerationAdapter<String> adapter = new IteratorToEnumerationAdapter<String>(list.iterator());
		assertTrue(adapter.hasMoreElements());
	}
	
	@Test(expected=NoSuchElementException.class)
	public final void testNextElementEmpty() {
		ArrayList<String> list = new ArrayList<String>();
		IteratorToEnumerationAdapter<String> adapter = new IteratorToEnumerationAdapter<String>(list.iterator());
		adapter.nextElement();
	}

	@Test
	public final void testNextElementNonEmpty() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Test");
		IteratorToEnumerationAdapter<String> adapter = new IteratorToEnumerationAdapter<String>(list.iterator());
		assertEquals("Test",adapter.nextElement());
	}
}
