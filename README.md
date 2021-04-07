# Patrice - A Boolean Logic Visualizer

## What it does
This program will generate visualizations of boolean logic statements given a 
valid input from the user or generates a logical statement from a corresponding
valid visualization created by the user.

Visualizations will be in the form of interactive logical circuits, 
consisting of inputs, gates and outputs.


## Who's it for
The following groups of people might find some utility in this application:
- Struggling CPSC 121 Students
- Patrice Belleville himself hopefully, and other CPSC 121 Lecturers
- Students enrolled in other classes that involve boolean logic(PHIL 220)

## Why I chose to make this
CPSC 121 isn't easy at the start. They introduce concepts that most people
haven't had any experience with. The section on boolean logic was particularly
difficult to grasp, in my opinion. Visualization can go a long way in helping 
those who are having the most trouble with the early part of the course. 

I named the program in honour of Dr. Patrice Belleville, who made CPSC 121
a thoroughly more enjoyeable experience. <br> </br> <br>

## User Stories
- As a user, I want to be able to **create** a new logical circuit and add circuit components to it
- As a user, I want to be able to be able to **edit** connections between components in my logical circuit
- As a user, I want to be able to be able to **delete** circuit components
- As a user, I want to be able to have the program *generate a logical statement* from a logical circuit
- As a user, I want to be able to have the program *generate a logical circuit* from a logical statement
- As a user, I want to be able to save the data stored in a workspace to a file
- As a user, I want to be able to load workspace data from a file

## Phase 4: Task 2
**Type Hierarchy**<br>
Classes involved:
- *CircuitGate*
- *BinaryCircuitGate* extends *CircuitGate*
- *AndGate* extends *BinaryCircuitGate*
- *OrGate* extends *BinaryCircuitGate*
- *NotGate* extends *CircuitGate*
- *CircuitOutput* extends *CircuitGate*

## Phase 4: Task 3
**What I would change in the design if I had more time** 
<br>
- The GUI save and load mechanism; both rely on instantiating the CLI version of the app
and using the existing save/load infrastructure for that, so I would create a standalone
save/load system for the GUI version
- I would refactor the inner classes inside InteractiveCircuitArea that handle keyboard 
input by assigning their associated behaviors to the listener using lambda functions instead
- I would refactor the circuit -> expression translator implementation to be more easily modular
 and readable



