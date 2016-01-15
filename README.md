# UMLGenerator

Design 
For the UMLGenerator, operates using the visitor pattern. 
DesignParser operates as the client code which helps choose the .class files have been chosen to construct a UML, and then creates a series of Visitors that decorate one another. 
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
-


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
-Updated Readme




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

