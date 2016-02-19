# UMLGenerator

Design 
For the UMLGenerator, operates using the visitor pattern. 
DesignParser operates as the client code which helps choose the .class files have been chosen to construct a UML, and then creates a series of Core.Visitors that decorate one another.
A ClassReader object (from ASM's package) works as the traverser in the design, which uses one decorated Visitor to store the relevant information from each class. 

Specifically three Visitor classes have been made, ClassDeclarationVisitor, ClassFieldVisitor, and ClassMethodVisitor that each implement ASM's ClassVisitor. 
The ClassDeclarationVistor collects information based on the declaration of each class, including the title, the interfaces they implement, and their superclasses. 
ClassFieldVistor collects information of the a classes' field, including names and access modifiers.
ClassMethodVisitor collects information on the classes' methods including names, access modifies, and parameters. 
Each of these classes have a UMLGraph Field so that they may pass relevant information onto the graph.
All of these visitor classes have a constructor which accepts a ClassVisitor object so that they may decorate one another properly.

UMLGraph is the class that acts as the storage structure for all the information collected from classes. It stores each interpreted class by using a UMLClass object. 
Each UMLClass object stores its respective declaration, fields (via a list of UMLField objects), and methods (via a list of UMLMethod objects). 
The set of classes UMLGraph, UMLClass, UMLField, and UMLMethod all extend GraphItem, an abstract class with two methods: toGraphVizString(), and getAccessTypeSymbol(int accessType).
Each of these classes implement toGraphVizString() such that the return string represents itself in a way that can be interpreted by GraphViz. 
The getAccessTypeSymbol method is just a helper method, used to determine the appropriate access level string for various GraphItems.
UMLGraph's toGraphVizString() is responsible for returning the full string inputed into GraphViz. 

Milestone 2:
The design of our tool didn't really change much from milestone one to milestone two. One class was added, the methodVisitor class. 
This class allowed us to look through the code of methods to help with uses arrows. Sadly, we couldn't get it to work how we wanted, so it doesn't really do anything.

Milestone 3: 
Once again, our tool design didn't change substantially between the Milestones 1 and 2. 
The largest notable changes were the addition of the new class SDGraphMethodData, a new interface SDGraphItem, and multiple new methods within pre-existing classes.
These changes were used to support the building of a Sequence Diagram instead of just UML's from the previous milestones. 
The structures of UMLGraph and UMLMethod already have many of the needed methods and store the data required
 to build a sequence diagram chose to keep using these classes despite their names being less accurate. 
UMLMethod's changes consists of keeping a list of method calls within a specific method, as well as printing methods for the Sequence Diagram. 
UMLGraph contains a method generateCallSequence which finds a specified method and class and then creates visitors to get the data 
of the next classes and methods in the calling sequence. This method also implements the callDepth capability of the new tool. 
To support some new data not accounted for in the existing UMLClasses, SDGraphMethodData helps box that data while providing a frame to easily make strings for the Sequence Diagram.
SDGraphItem simply provides the base for printing out the required strings to build a sequence diagram.

In terms of design patterns, our project still largely implements the visitor pattern. However, unlike the code that builds UML graphs, the sequence diagram tool
only visits classes as they are discovered by method calls. The nature of sequence diagrams also forced our project to rely more heavily upon the MethodVisitor. 
 
Milestone 4: 
Few changes were made during this milestone. The changes made include: the addition of a PatternDetector interface, a SingletonPatternDetector, new method and field in 
UMLGraph, and a new field within UMLClass. We coded to an interface with the SingletonPatternDetector,
which simply searches through the collected class data stored in UMLClass to find the singleton pattern. 
Because UMLGraph contained the necessary data for finding the Singleton Pattern, we connected and ran the PatternDectors within a new UMLGraph method: detectPatterns. 
If more patterns were wished to be added, the user would add a pattern to the ArrayList within DesignParser which is then passed to UMLGraph. 
UMLClasses now store a list of patterns that they implement, which allows a user to see which patterns a class may help implement. 

Milestone 5: 
Very few modifications to the pre-existing classes were made as the design from previous milestones made adding new pattern detectors require minimal work. 
Modifications to the existing structure include: 
1) Slight reordering of UMLGraph code to allow Pattern Detectors to interact with arrows. 
2) A new field label, with getter and setter methods to allow additions of labels to arrows. 
3) Fixes to logic of the removal of "redundant" arrows. 
New additions to the project in this milestone are the two new PatternDetectors, which implement IPatternDetector from Milestone 4. 
These pattern detectors are DecoratorPatternDetector and AdapterPatternDetector which identify the Decorator and Adapter patterns respectively. 

Milestone 6: 
Few changes were made during this milestone to pre-existing code as our previous structure supported the easy addition of a CompositePatternDetector. 
The largest change of previous project components has been made to the type data class, as issues arose when handling fields that are arrays. 
Thus the class now supports a method isArray that tells if the field is an array or not. 
The only new class within the project is the CompositePatternDetector which implements the IPatternDetector interface and detects Composite Patterns with UMLS.

Milestone 7: 
Out of pre-existing code, few changes were made to support the addition of the GUI elements. 
UMLArrows can now be coloured, and the classes have been organized into packages based on functionality.
To support different operations within the GUI, an new package of classes has been added.
Main works as the client code, and serves as an example of how to build our project with new functionality.
LandingScreenFrame is the first frame built by the client, and allows the user to select properties and analyze classes..
This frame then builds a new frame: DesignParserFrame which contains CheckboxPanel and ToolbarPanel.
DesignParserFrame displays the UML in the right field, the CheckboxPanel in the left field and a ToolbarPanel at the top.
CheckboxPanel and ToolbarPanel implement the functionality of the GUI as described in the milestone Requirements. 
The Utilities class just encapsulates many constants read in by the property file. 
To support the addition of phases, IPhase and APhase serve as the interface for which the concrete phases code to. 
Multiple phases have been built, and their functionality can be read in the instructions.txt file. 
ImageProxy serves as a proxy for the ImageIcon class, providing an image if the UML has not been fully built. 
The building of phases implements the strategy pattern, while ImageProxy follows the Proxy pattern. 

Who did what: 
Alec Tiefenthal
Milestone 1: 
-Created GraphItem, UMLClass, UMLField, UMLGraph, and UMLMethod, modified the other classes given in lab 3-1 to support the drawing of classes, fields, and methods. 
-Added arrows that represent "implements". 
-Implemented file choosing and saving for ease of use. 
-Added class documentation.
-Created UML diagram.
Milestone 2:
-Made addociation arrows work
-Made preliminary uses arrows work
Milestone 3: 
-Improved the drawing of arrows for the UML diagrams.
-Implemented building a sequence diagram string for SDEdit. 
Milestone 4: 
-Modified the Sequence Diagram code to method returns (not needed for project, but wished to fix for visual accuracy)
-Created the PatternDector interface 
-Changed UMLMethod, UMLClass, and DesignParser to support design patterns.
Milestone 5:
-wrote the AdapterPatternDetector
-refactored Design parser
Milestone 6: 
-Modified TypeData to handle arrays easier.
-helped write CompositePatternDetector class
Milestone 7: 
-Coded the structure for the GUI including DesignParserFrame, LandingScreenFrame, and CheckboxPanel.
-Coded the API and implemented concrete versions of Phases. 
-Coded the Utilities class


Dan McGarry
Milestone 1: 
-Helped modify classes created in lab 3-1, as well as the other classes Alec created. 
-Added arrows that represent "extends". 
-Wrote documentation for the project within this "README". 
-Wrote the test cases for the project
-Added minor class documentation
Milestone 2:
-Created the methodVisitor.
-Worked on finalized uses arrows
*-Fixed and included test cases missing in milestone 
Milestone 3: 
-Improved the drawing of arrows for the UML diagrams. 
-Fixed some issues with arrows for the UML diagrams.
-Helped implement building a sequence diagram string for SDEdit
-Updated README
Milestone 4: 
-Created the SingletonPatternDetector class. 
-Wrote the test cases for the milestone. 
-Updated README
Milestone 5: 
-Wrote the DecoratorPatternDetector
-Wrote the test cases for the milestone
-updated README
Milestone 6: 
-Wrote the CompositePatternDetector class
-Wrote test cases for the milestone. 
-updated README
Milestone 7: 
-Coded the ToolbarPanel class 
-Helped implement DesignParserFrame and LandingScreenFrame
-Coded the ImageProxy class
-Updated README


Instructions: 
Generating UML Diagrams
Step 1:
In order to use the UML generator, import the UMLGenerator into an eclipse workspace. 

Step 2:
With a program such as windows explorer, navigate to UMLGenerator/files. This folder contains .class files that the program uses to generate the UML. 
If the desired classes you wish to generate a UML of are in this folder, move to step 3. Else follow the next step to generate the proper .class files.

Step 2.1:
To generate class files, simply import the desired project into eclipse. If the project compiles, a "bin" folder will be created where ever the project is located.
If you do not want to navigate to the bin folder using the java filechooser, move the desired .class files into the /files folder for easy selection.

Step 3:
Once UMLGenerator has been imported, run the main function within the DesignParser Class, supplying no arguments. This can either be done via command line or eclipses run button/command. 

Step 4: 
An option box will appear asking if you wish to build a UML or a Sequence Diagram. Choose the UML option.

Step 5:
A box will appear confirming that the GraphViz dot.exe is in it's default path. If it isn't, hit 'No' and proceed to find it with the file navigator.

Step 6: 
A file navigation window will appear, starting in the /files folder. Select the files you wish to generate a UML of and then click open. 
As a note, you can select multiple files using shift and/or control + click.

Step 7:  
A new popup window will appear asking for a save location and name. Clicking save will generate both a .png and a .dot file that contain the uml of the desired classes at the location you specify.

Step 8:
Open the .png file to view the UML of the classes you chose. 

Generating Sequence Diagrams 
Step 1: 
In order to build Sequence diagrams, import the UMLGenerator into an eclipse workspace. 

Step 2: 
Run the main function within the DesignParser Class, supplying no arguments. 

Step 3: 
An option box will appear asking if you wish to build a UML or a Sequence Diagram. Choose the Sequence Diagram option. 

Step 4:
A box will appear confirming that the SDEdit SDedit.exe is in it's default path. If it isn't, hit 'No' and proceed to find it with the file navigator.

Step 5: 
A new prompt will appear asking for a fully qualified method signature. Input this method signature in the box to run the sequence diagram tool. 

Step 6:
A new popup window will appear asking for a save location and name. Clicking save will generate both a .png and a .dot file that contain the SD of the desired method you specified.

Step 7:
Open the .png file to view the SD of the method you chose. 

Running GUI
Step 1: 
In order to run main, import the UMLGenerator into an eclipse workspace. 

Step 2: 
Run the main function within the Main Class, supplying no arguments. 

Step 3: 
A frame will appear asking for Load Config and Analyze.
Select Load Config and choose an appropriate .properties file. 
Select Analyze and wait for the project to build. 

Step 4:
Play with settings in the new window. Select Help->Instructions on how to operate the Design Parser Frame. 


