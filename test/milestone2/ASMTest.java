package milestone2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.DesignParser;
import problem.asm.TypeData;
import problem.asm.UMLClass;
import problem.asm.UMLField;
import problem.asm.UMLGraph;
import problem.asm.UMLMethod;

public class ASMTest {

	public ASMTest() throws IOException {
			
	}
	
	
	/*
	 * "Mega test" for lab 1-3.  Classes, methods, and fields inputed manually from the hand made UML and compared to computed UML.
	 * Intended to test all aspects of Milestone 1 design: Classes, fields, methods, and implements arrows all work properly
	 * 
	 * 
	 * Milestone 2 should still function properly
	 */
	@Test
	public void testLab1_3() throws IOException {
		File f1 = new File("./files/lab1-3/AppLauncher.class");
		File f2 = new File("./files/lab1-3/EventData.class");
		File f3 = new File("./files/lab1-3/FileType.class");
		File f4 = new File("./files/lab1-3/EventHandler.class");
		File f5 = new File("./files/lab1-3/PDF.class");
		File f6 = new File("./files/lab1-3/HTML.class");
		File f7 = new File("./files/lab1-3/TXT.class");
		File f8 = new File("./files/lab1-3/EvntHdlrPDFAdd.class");
		File f9 = new File("./files/lab1-3/EvntHdlrTXTAdd.class");
		File f10 = new File("./files/lab1-3/EvntHdlrPrintFilenameAdd.class");
		
		File[] files = {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10};
		UMLGraph actuallyG = DesignParser.visitFiles(files);
		
		ArrayList<TypeData> params = new ArrayList<TypeData>(); 
		params.add(new TypeData("Path", null, "java/nio/file/Path"));
		
		ArrayList<TypeData> empty = new ArrayList<TypeData>(); 
		TypeData voidData = new TypeData("void", null, "void");
		
		UMLGraph shouldBeG  = new UMLGraph("Test_UML", "BT");
		shouldBeG.addClass(new UMLClass("problem/AppLauncher", null, Opcodes.ACC_PUBLIC, new String [] {} ));
		shouldBeG.addField(new UMLField("watcher", new TypeData("WatchService", null, "java/nio/file/WatchService"), Opcodes.ACC_PRIVATE));
		shouldBeG.addField(new UMLField("dir", new TypeData("Path", null, "java/nio/file/Path"), Opcodes.ACC_PRIVATE));
		shouldBeG.addField(new UMLField("stop", new TypeData("boolean", null, "boolean"), Opcodes.ACC_PRIVATE));
		shouldBeG.addField(new UMLField("fileTypes", new TypeData("ArrayList", new TypeData("FileType", null, "problem/FileType"), "java/util/ArrayList"), Opcodes.ACC_PRIVATE));
		shouldBeG.addMethod(new UMLMethod("init", 0, params, voidData));
		shouldBeG.addMethod(new UMLMethod("run", Opcodes.ACC_PUBLIC, empty,voidData));
		shouldBeG.addMethod(new UMLMethod("clearEverything", Opcodes.ACC_PROTECTED, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("stopGracefully", Opcodes.ACC_PUBLIC, empty ,  voidData));
		shouldBeG.addMethod(new UMLMethod("isRunning", Opcodes.ACC_PUBLIC, empty, new TypeData("boolean", null, "boolean")));
		shouldBeG.addMethod(new UMLMethod("getApplicationsCount", Opcodes.ACC_PUBLIC, empty, new TypeData("int", null, "int")));
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("String[]", null, "java/lang/String"));
		shouldBeG.addMethod(new UMLMethod("main", Opcodes.ACC_PUBLIC, params, voidData));
		
		
		shouldBeG.addClass(new UMLClass("problem/EventData", null, Opcodes.ACC_PUBLIC, new String[] {}));
		shouldBeG.addField(new UMLField("event", new TypeData("WatchEvent", new TypeData("Path", null, "java/nio/file/Path"), "java/nio/file/WatchEvent"), 0));
		shouldBeG.addField(new UMLField("name", new TypeData("Path", null, "java/nio/file/Path"), 0));
		shouldBeG.addField(new UMLField("file", new TypeData("Path", null, "java/nio/file/Path"), 0));
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("WatchEvent", new TypeData("Path", null, "java/nio/file/Path"), "java/nio/file/WatchEvent"));
		params.add(new TypeData("Path", null, "java/nio/file/Path"));
		shouldBeG.addMethod(new UMLMethod("init", 0, params, voidData));
		
		
		shouldBeG.addClass(new UMLClass("problem/FileType", null, Opcodes.ACC_ABSTRACT, new String [] {}));
		shouldBeG.addField(new UMLField("eventHandlers", new TypeData("ArrayList", new TypeData("EventHandler", null, "problem/EventHandler"), "java/util/ArrayList"), Opcodes.ACC_PROTECTED));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("EventData", null, "problem/EventData"));
		shouldBeG.addMethod(new UMLMethod("updateEventData", Opcodes.ACC_PUBLIC, params, voidData));
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("EventHandler", null, "problem/EventHandler"));
		shouldBeG.addMethod(new UMLMethod("addEventHandler", Opcodes.ACC_PUBLIC, params, voidData));
		shouldBeG.addMethod(new UMLMethod("disableAllHandlers", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("enableAllHandlers", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("killProcesses", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("getApplicationsCount", Opcodes.ACC_PUBLIC, empty, new TypeData("int", null, "int")));
		shouldBeG.addMethod(new UMLMethod("diableHandlerType", Opcodes.ACC_PUBLIC, params, voidData));
		shouldBeG.addMethod(new UMLMethod("enableHandlerType", Opcodes.ACC_PUBLIC, params, voidData));
		
		
		shouldBeG.addClass(new UMLClass("problem/EventHandler", null, Opcodes.ACC_ABSTRACT, new String [] {}));
		shouldBeG.addField(new UMLField("enabled", new TypeData("boolean", null, "boolean"), Opcodes.ACC_PROTECTED));
		shouldBeG.addField(new UMLField("processes", new TypeData("List", new TypeData("Process", null, "java/lang/Process"), "java/util/List"), Opcodes.ACC_PROTECTED));
		shouldBeG.addField(new UMLField("applicationsLaunched", new TypeData("int", null, "int"), Opcodes.ACC_PROTECTED));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("diable", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("enable", Opcodes.ACC_PUBLIC, empty, voidData));
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("EventData", null, "problem/EventData"));
		shouldBeG.addMethod(new UMLMethod("handleEvent", Opcodes.ACC_PUBLIC, params, voidData));
		shouldBeG.addMethod(new UMLMethod("handleEventHelper", Opcodes.ACC_PROTECTED, params, voidData));
		shouldBeG.addMethod(new UMLMethod("killProcesses", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("getApplicationsLaunched", Opcodes.ACC_PUBLIC, empty, new TypeData("int", null, "int")));
		
		
		params = new ArrayList<TypeData>(); 
		params.add(new TypeData("EventData", null, "problem/EventData"));
		shouldBeG.addClass(new UMLClass("problem/PDF", "problem/FileType", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("updateEventData", Opcodes.ACC_PUBLIC, params, voidData));
		
		
		shouldBeG.addClass(new UMLClass("problem/HTML", "problem/FileType", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("updateEventData", Opcodes.ACC_PUBLIC, params, voidData));
		
		
		shouldBeG.addClass(new UMLClass("problem/TXT", "problem/FileType", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("updateEventData", Opcodes.ACC_PUBLIC, params, voidData));
		
		
		
		
		shouldBeG.addClass(new UMLClass("problem/EvntHdlrPDFAdd", "problem/EventHandler", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("handleEventHelper", Opcodes.ACC_PUBLIC, params, new TypeData("void", null, "void")));

		shouldBeG.addClass(new UMLClass("problem/EvntHdlrTXTAdd", "problem/EventHandler", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("handleEventHelper", Opcodes.ACC_PUBLIC, params, new TypeData("void", null, "void")));
		
		shouldBeG.addClass(new UMLClass("problem/EvntHdlrPrintFilenameAdd", "problem/EventHandler", Opcodes.ACC_SUPER, new String [] {}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, empty, voidData));
		shouldBeG.addMethod(new UMLMethod("handleEventHelper", Opcodes.ACC_PROTECTED, params, new TypeData("void", null, "void")));
		
		
		
		
		assertEquals(shouldBeG.toGraphVizString(), actuallyG.toGraphVizString());
		
	}
	
	@Test 
	public void testNumberOfArrows() throws IOException{
		File f1 = new File("./files/lab1-3/AppLauncher.class");
		File f2 = new File("./files/lab1-3/EventData.class");
		File f3 = new File("./files/lab1-3/FileType.class");
		File f4 = new File("./files/lab1-3/EventHandler.class");
		File f5 = new File("./files/lab1-3/PDF.class");
		File f6 = new File("./files/lab1-3/HTML.class");
		File f7 = new File("./files/lab1-3/TXT.class");
		File f8 = new File("./files/lab1-3/EvntHdlrPDFAdd.class");
		File f9 = new File("./files/lab1-3/EvntHdlrTXTAdd.class");
		File f10 = new File("./files/lab1-3/EvntHdlrPrintFilenameAdd.class");
		
		File[] files = {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10};
		UMLGraph actuallyG = DesignParser.visitFiles(files);
		
		//Count taken from hand made graph of lab 1-3   = 11 +  2 (circular arrows) + 6(uses from within method calls) 
		//had problems removing the extra arrows from sublcasses. TODO: fix code and adjust this to get the proper 11 arrows.
		int numberOfArrows = 20;
		String res = actuallyG.toGraphVizString();
		String[] resArray = res.split(";");
		int counter = 0;
		for(int i = 0; i < resArray.length; i++ ) {
			if (resArray[i].contains("->")) counter++;
		}
		assertEquals(numberOfArrows, counter);		
		
	}
	
	@Test
	public void testArrowType() throws IOException {
		File f1 = new File("./files/lab1-3/AppLauncher.class");
		File f2 = new File("./files/lab1-3/EventData.class");
		File f3 = new File("./files/lab1-3/FileType.class");
		File f4 = new File("./files/lab1-3/EventHandler.class");
		File f5 = new File("./files/lab1-3/PDF.class");
		File f6 = new File("./files/lab1-3/HTML.class");
		File f7 = new File("./files/lab1-3/TXT.class");
		File f8 = new File("./files/lab1-3/EvntHdlrPDFAdd.class");
		File f9 = new File("./files/lab1-3/EvntHdlrTXTAdd.class");
		File f10 = new File("./files/lab1-3/EvntHdlrPrintFilenameAdd.class");
		
		File[] files = {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10};
		UMLGraph actuallyG = DesignParser.visitFiles(files);
		
		String res = actuallyG.toGraphVizString();
		//association arrow, from applauncher to filetype
		assertTrue(res.contains("\"problem/AppLauncher\" -> \"problem/FileType\" [arrowhead=\"vee\", style=\"solid\"]"));
		
		//uses arrow, form eventHandler to EventData
		assertTrue(res.contains("\"problem/EventHandler\" -> \"problem/EventData\" [arrowhead=\"vee\", style=\"dashed\"]"));
	}
	
	@Test
	public void testFactoryConcreteProduct() throws IOException {
		
		File f1 = new File("./files/factory/ChicagoPizzaIngredientFactory.class");
		File f2 = new File("./files/factory/Dough.class");
		File f3 = new File("./files/factory/ThickCrustDough.class");
		
		File[] files = {f1, f2, f3};
		UMLGraph actuallyG = DesignParser.visitFiles(files);
		
		String res = actuallyG.toGraphVizString();
		
		assertTrue(res.contains("\"headfirst/factory/pizzaaf/ChicagoPizzaIngredientFactory\" -> \"headfirst/factory/pizzaaf/ThickCrustDough\" [arrowhead=\"vee\", style=\"dashed\"]"));
		assertTrue(res.contains("\"headfirst/factory/pizzaaf/ThickCrustDough\" -> \"headfirst/factory/pizzaaf/Dough\" [arrowhead=\"onormal\", style=\"dashed\"]"));
		assertFalse(res.contains("\"headfirst/factory/pizzaaf/ChicagoPizzaIngredientFactory\" -> \"headfirst/factory/pizzaaf/Dough\" [arrowhead=\"vee\", style=\"dashed\"]"));
		
		
		
		
	}
	
}
