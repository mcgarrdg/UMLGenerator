package milestone3;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Core.SD.SDGraphMethodData;
import Core.TypeData;

public class SDGraphMethodDataTest {

	String classCalledFrom;
	String methodName;
	ArrayList<TypeData> argData;
	String classCalledOn;
	String returnName;
	public SDGraphMethodDataTest() {
		
	}
	
	@Test
	public void testFields() {
		
		this.classCalledFrom = "problem.myClass";
		this.methodName = "valueOf";
		this.argData = new ArrayList<TypeData>();
		this.classCalledOn = "java.lang.String";
		this.returnName = "boolean";		
		SDGraphMethodData data = new SDGraphMethodData(classCalledFrom, methodName, classCalledOn, returnName, argData);
		
		assertEquals(data.getClassCalledFrom(), this.classCalledFrom);
		assertEquals(data.getClassCalledOn(), this.classCalledOn);
		assertEquals(data.getMethodName(), this.methodName);
		assertEquals(data.getReturnName(), this.returnName);
	}
	
}
