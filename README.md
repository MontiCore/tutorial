# Introduction
MontiCore is a language workbench for the efficient development of domain-specific languages (DSLs).
It processes an extended grammar format which defines the DSL and generates Java components for processing the DSL documents.
Examples for these components are parsers, AST classes, symboltables or pretty printers.
This enables a user to rapidly define a language and use it together with the MontiCore framework to build domain specific tools.

Some MontiCore advantages are the reusability of predefined language components, conservative extension and composition mechanisms, and an optimal integration of handwritten code into the generated tools.
Its grammar languages are comfortable to use.

This Getting Started aims to give a good and short introduction to all main features of MontiCore.
For this, we will consider multiple domain-specific languages and show you how these languages can be built with the help of MontiCore.
This is designed to be interactive.
This means that at some point you yourself have to write Java code to build a specific language or even design your own language.

In each chapter, a new language is presented.
If this is your first time using MontiCore, then we suggest completing the first language before trying anything else because it introduces you to the basics of working with a DSL in MontiCore.

Chapter 2 introduces the Automaton language.
It is one of the easiest and yet one of the most useful languages and describes finite-state automata.
With the help of the Automaton language, we hope to show you the basics of the grammars describing a DSL and their connection to the models of a DSL.
Further, we will describe the concepts of an abstract syntax tree (AST) and its traversal with the help of MontiCore's visitor and traverser infrastructure.
At last, the Automaton language is used to explain the concept of a symbol table and its uses for DSLs.

In Chapter 3, you will learn how to create a very limited version of the Java programming language.
With the help of this language, we want to teach you how MontiCore handles the creation of a symbol table from an AST.
Furthermore, we want to create a TypeCheck for this language and write CoCos based on that TypeCheck.

Chapter 4 composes the two previously designed languages.
It should explain MontiCore's grammar composition.
Additionally, it should give you an idea of when it is possible to use the code of the part languages and when it is better to write your own code for the composed language.

Another possible DSL is presented in Chapter 5.
It can be used to model a website.
Here, you will get multiple models of the language.
You will create the grammar for the language yourself by abstracting it from the model contents.
After creating the grammar, generating a website from the models is the main focus of this chapter.
For this, you will implement a generator with the help of FreeMarker templates and MontiCore's generation engine.

#### Exercise 0
This exercise will get you started with regard to installing software.
Checkout/Clone the repository using git.
Next, install Gradle version 7.6 on your computer.
We recommend the use of Java 11 (but other versions might work too).

Depending on what IDE you use, it should be able to import the project.
The various chapters are added as subprojects.
Perform a gradle build (using your IDE) on the automata subproject.
Otherwise, execute  `gradle 01.automata:build` in the projects folder in a terminal of your choice.
The final output lines should contain `BUILD SUCCESSFUL`.

In case this command fails, ensure that you are using the correct versions
of Java and Gradle by using the commands `java --version` and
`gradle --version`. 

Next, continue with [Chapter 2](01.automata/README.md)
