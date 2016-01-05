# UMLGenerator

Design 
For the UMLGenerator, operates using the visitor pattern. DesignParser operates as the client code which helps choose the .class files have been chosen to construct a UML, and then creates a series of Visitors that decorate one another. A ClassReader object (from ASM's package) works as the traverser in the design, which uses one decorated Visitor to store the relevant information from each class. 

Specifically three Visitor classes have been made, ClassDeclarationVisitor, ClassFieldVisitor, and ClassMethodVisitor that each implement ASM's ClassVisitor. The ClassDeclarationVistor collects information based on the declaration of each class, including the title, the interfaces they implement, and their superclasses. ClassFieldVistor collects information of the a classes' field, including names and access modifiers. ClassMethodVisitor collects information on the classes' methods including names, access modifies, and parameters. Each of these classes have a UMLGraph Field to store the relevant information. All of these visitor classes have a constructor which accepts a ClassVisitor object so that they may decorate one another properly.

UMLGraph is the class that acts as the storage structure for all the information collected from classes. It stores each interpreted class by using a UMLClass object. Each UMLClass object stores its respective declaration, fields (via a list of UMLField objects), and methods (via a list of UMLMethod objects). The set of classes UMLGraph, UMLClass, UMLField, and UMLMethod all implement IGraphItem, an interface with only one method: toGraphVizString(). Each of these classes implement this method such that the return string represents itself in a way that can be interpreted by GraphViz. UMLGraph's toGraphVizString() is responsible for returning the full string inputed into GraphViz. 

Who did what: 
Alec Tiefenthal
Milestone 1: 
-Created IGraphItem, UMLClass, UMLField, UMLGraph, and UMLMethod, modified the other classes given in lab 3-1 to support the drawing of classes, fields, and methods. 
-Added arrows that represent "implements". 
-Implemented file choosing for ease of use. 


Dan McGarry
Milestone 1: 
-Helped modify classes created in lab 3-1, as well as the other classes Alec created. 
-Added arrows that represent "extends". 
-Wrote documentation for the project with this "README". 




Instructions: 
Step 1:
In order to use the UML generator, import the UMLGenerator into an eclipse workspace. 
Step 2;
Once UMLGenerator has been imported, run the main function within the DesignParser Class, supplying no arguments. This can either be done via command line or eclipses run button/command. 
Step 3: 
A file navigation window will appear, starting in the files folder. This folder contains groups of .class files that can be converted by the UMLGenerator. Once related files are chosen, click open. 
Step 4:  
UMLGenerator will print a string to the console. Copy this string for use in the next step.
Step 5: 
Open gvedit.exe of Graphviz, create a new graph, and paste the string into this graph. Finish by clicking the layout button to show a UML of the classes you chose. 