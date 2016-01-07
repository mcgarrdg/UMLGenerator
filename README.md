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
-Implemented file choosing and saving for ease of use. 
-Added class documentation


Dan McGarry
Milestone 1: 
-Helped modify classes created in lab 3-1, as well as the other classes Alec created. 
-Added arrows that represent "extends". 
-Wrote documentation for the project within this "README". 
-Wrote the test cases for the project
-Added minor class documentation




Instructions: 
Step 1:
In order to use the UML generator, import the UMLGenerator into an eclipse workspace. 

Step 2:
With a program such as windows explorer, navigate to UMLGenerator/files. This folder contains .class files that the program uses to generate the UML. If the desired classes you wish to generate a UML of are in this folder, move to step 3. Else follow the next step to generate the proper .class files.

Step 2.1:
To generate class files, simply import the desired project into eclipse. If the project compiles, a "bin" folder will be created where ever the project is located. Move the desired .class files into the /files folder for easy selection.

Step 3:
Once UMLGenerator has been imported, run the main function within the DesignParser Class, supplying no arguments. This can either be done via command line or eclipses run button/command. 

Step 4: 
A file navigation window will appear, starting in the /files folder. Select the files you wish to generate a UML of and then click open. 

Step 4:  
A new popup window will appear asking for a save location and name. Clicking save will generate both a .png and a .dot file that contain the uml of the desired classes at the location you specify.

Step 5:
Open the .png file to view the UML of the classes you chose. 