<!-- (c) https://github.com/MontiCore/monticore -->
# Composition of Automata and SimpleJava
## Language, Models and Grammars 

In this Chapter, we will compose the two languages `Automata` created in Chapter 1 and
`SimpleJava` from Chapter 2. 
We will explore how to compose these two languages in the best and easiest way, and we will
 investigate which parts (AST, symbol table, context conditions, type check, visitors, ...) of the sublanguages can
 be used in this language as well. 
As you can imagine, our composed language cannot use every part of the language.


Composing multiple languages is fairly easy in MontiCore: 
The language developer simply creates a new grammar that extends those "super" grammars. 
Because both super grammars have a start production 
 (`Automaton` for Automata, `JavaCompilationUnit` for SimpleJava)
and only one of them can be the start production of this composed grammar, 
 we have to specify the new start production of the composed grammar using the keyword `start`. 
The following shows our new grammar `SimpleJavaWithAutomata`:

```mc4
grammar SimpleJavaWithAutomata extends SimpleJava, Automata {
 start JavaCompilationUnit;
 
 @Override
 JavaBlock = (JavaVarDecl | Expression | Automaton)*;

}
```

There exists only one new production in this grammar:
`JavaBlock`, which overrides the JavaBlock production of the grammar SimpleJava. 
The extension is a conservative extension of the production,
 as both original options `JavaVarDecl` and `Expression` are still possible.
Therefore, any `JavaBlock` of the grammar SimpleJava would also be a valid `JavaBlock` in this composed language. 
The only difference is that our new `JavaBlock` may consist of `Automata` as well. 
A `JavaBlock` is only used in the production `JavaMethod`, or more specifically: 
 The body of a method is a `JavaBlock`.
This means that in our composed language methods bodies can consist of variable declarations,
 expressions and, now additionally, automata. 
The next listing is a good example for a model of this language:

```java
class Check {

 int foo = 3

 tutorial.simplejava.valid.Bar getBar(){
   tutorial.simplejava.valid.Bar bar
   foo = bar.getAge()
 }

 void doSomething(){
   tutorial.simplejava.valid.Bar bar = getBar()
   // The ping pong game
   automaton PingPong {
     state NoGame <<initial>>;
     state Ping;
     state Pong <<final>>;
    
     NoGame - startGame > Ping;
    
     Ping - stopGame > NoGame;
     Pong - stopGame > NoGame;
    
     Ping - returnBall > Pong;
     Pong - returnBall > Ping;
   }
   print(tutorial.simplejava.valid.Bar.getMax())
 }

 void print(int max){
   tutorial.simplejava.valid.InheritanceBar inBar
   //test inheritance
   tutorial.simplejava.valid.Bar bar = inBar
   //test that InheritanceBar can use the methods of Bar
   int foo = inBar.getAge()
 }
}
```

This model combines the model `Check` of the language SimpleJava 
 and the `PingPong` automaton of the language Automata. 
In the method `doSomething`, the PingPong automaton is squeezed between the variable declaration
 and the expressions that were already there in the original SimpleJava model.



<!-- (c) https://github.com/MontiCore/monticore -->

## Composition
You saw how to compose the concrete syntax of two grammars.
Now, we will focus on different parts of the language such as the AST, symbol table, type check, context conditions and visitors and
investigate if the classes we implemented in the last two Chapters are reusable in this  language.

### AST
The composition of the AST is fairly easy. 
For productions that are not overwritten in the composed language, the AST classes of the defining languages are used. 
Therefore, MontiCore generates only one new AST class for the grammar: `ASTJavaBlock`. 
This new class has the ability to store not only variable declarations or expressions as in SimpleJava,
 but also automata. 
The new class `simplejavawithautomata._ast.ASTJavaBlock` extends the old class `simplejava._ast.ASTJavaBlock` of the language SimpleJava.

### Symbol Table


Composing a symbol table means creating scopes that can store the symbols of every part language. 
Therefore, MontiCore generates the three scope classes and interfaces mentioned in Section 1.4 for every grammar. 
The scope interface of the composed grammar extends all the scope interfaces of the sub languages. 
Thus, the scope class implementing this interface is able to resolve the different symbols of all the sub languages. 
Similar to the AST, the symbol classes of the sub languages are used instead of generating them anew
for every grammar that uses them. 
Only if the composed grammar would introduce a new symbol, a new symbol class would be generated for it. 
For the symbol table creation,
 i.e. the `ScopesGenitor` and `ScopesGenitorDelegator`, both classes are generated for every grammar. 
The `ScopesGenitor` focuses on the symbol table creation for the concrete grammar,
 while the `ScopesGenitorDelegator` connects it with the `ScopesGenitor`s of the sub languages,
 hence creating a symbol table with correct scopes and correct symbols for the whole composed language. 
Changes that were done to the `ScopesGenitor` of one of the sub languages like adding the package name of a model to the
artifact scope need to be implemented in the `ScopesGenitor` of the composed language as well. 
The symbol table creation for a language was split into two parts for more complex languages:
For this, the `ScopesGenitorDelegator` marked the first part that created the symbols
 but left some of their attributes unfilled while another handwritten class marked
the second part that completed those attributes. 
To add one class that acts as an interface for the symbol table creation, 
 we added another handwritten class combining the two phases of the symbol table creation. 
While `SimpleJavaSTCompleteTypes`,
 the handwritten class that we added for the second phase of the symbol table creation of the SimpleJava language can be reused,
 the `SimpleJavaPhasedSTC` class combining both parts of the symbol table creation cannot. 
Therefore, 
 we need to implement a new class that is structured just as the `SimpleJavaPhasedSTC` class that we already created for SimpleJava.
It first executes the `ScopesGenitorDelegator` on the AST and then executes all the classes
 that were handwritten for the second, completition phase of the symbol table creation on the AST. 
As a traverser of the composed language can use visitors of
the sub languages, the class for the completition phase of the SimpleJava language can simply
be given to this traverser.


#### Exercise 1
Similar to the exercise in the SimpleJava language,
 add the package name of a model to the symbol table by overwriting the `ScopesGenitor` with the TOP mechanism 
 and adjusting the `createFromAST` method. 
After that, fill in the logic that combines the two symbol table creation phases in the class `SimpleJavaWithAutomataPhasedSTC`. 
This will be similar to an exercise of the SimpleJava chapter as well. 
Check that your implementation is correct by executing the tests `testPackage` and `testSymbolCompletion` in the class `SymbolTableTest`.

In SimpleJava, we introduced the symbol table import and export mechanism.
The composed language can also export and import symbol tables into and from files. 
For this, the composed language has a Symbols2Json class as well. 
You saw how the serialized symbol table of a model looks in a file in the last chapter. 
The symbol table of the SimpleJava model `Check.sjava`  is stored in the `src/test/resources/tutorial/simplejava/symboltable/Check.javasym` file.
One of the main questions for the symbol table import is: 
 Can we import the symbol table of a sub language in our composed language? 
After all, our composed language knows all the scopes and symbols that
are described in this file. 
For this, we wrote the test named `testSymbolTableImport` in the class `SymbolTableTest`. 
We first create an instance of `SimpleJavaWithAutomataSymbols2Json` and
 then try to load the symbol table of the SimpleJava model `Check`. 
After that, we try to resolve the function `getBar` inside the class `Check`. 
We assert that the symbol for the function can be found. 
Executing this test will show you that loading a symbol table of a model of a part language works as intended 
 and that we can even resolve inside this symbol table. 
This is one major advantage of the JSON serialization of symbol tables. 
As long as another languages knows all of the symbols in a symbol table file or has adapters for them,
 it can import the symbol table into its GlobalScope. 
Thus, symbol tables from totally different languages can be used together if the same symbols are used in both languages.

### Visitors

We will test whether the visitors we implemented in Section 1.2 work for models of the
composed languages as well.
For this, we will write a test that executes the three visitors on a new model.
The test can be seen in the method `testVisitorsOnAutomaton` in the class `VisitorTest`.
First, the model Bar is parsed and its symbol table is created. 
After that, the method resolves for the automaton symbol `Door` in the model.
After it was found, its corresponding AST node is retrieved and the three visitors are
executed on it. 
There should exist four transitions, five states in total, two initial states
and one final state in this model. 
When executing the test, the visitors find exactly that amount of states and transitions. 
The second test `testVisitorsOnWholeModel` does mostly the same. 
However, it does not retrieve the automaton but executes the visitors
on the whole model. 
As the automaton is embedded into the whole model and it still has
the same number of transitions and states, the visitors should still return these numbers.
Executing this test will tell you that the visitors returned the correct values. This means
that the visitors of the sub languages work as intended in the composed language, even
if they are executed on AST classes that their sub language does not know. There was
one important change to the Visitor test in Automata: The traverser that was used had
the type `AutomataTraverser` but now it is `SimpleJavaWithAutomataTraverser`.
Without this change, the visitors would not have been successful in the second method
because the `AutomataTraverser` would not have known how to navigate AST classes
that are unknown to him, such as `ASTJavaCompilationUnit` on which we executed
the traverser.

Generally, the visitors of the sublanguages can be used in the composed language and
still return the correct results. However, these visitors need to be stored in the correct
traverser to work properly. 
If the traverser does not know how to navigate the AST, the
visitors will never be executed. The visitors of the sublanguages are only useful to retrieve
the information that the sublanguage has. 
If a composed language adds new productions or embeds a production of one language into the production of another language (like
Automaton in `JavaBlock`), 
 the language developer needs to write new visitors for the composed language to retrieve all information.

### Type Check

Creating a Type Check for a composed language can be very different from language to
language. This is because sometimes, nothing needs to be done and the Type Check of
one of the sub languages can be used, while sometimes, parts of the Type Check of a sub
language must be adjusted for the composed language and sometimes most of the Type
Check must be rewritten. In some composed languages, only one of the sub languages
already has a Type Check. Depending on the semantic of the new composed language,
this Type Check can either be reused or extended with more logic to integrate potential new
types that were added to the language by composing all those languages. If more than one
sub language already has a Type Check, a new Type Check combining the Type Checks of
all sub languages must be created. In our composed language, we have two sub languages.
SimpleJava already has a Type Check, Automata however has not. For this tutorial, we
chose that the Automata language does not introduce new types to the composed language
so that we can reuse the Type Check of SimpleJava. Another possibility would be that
we introduced automata as a new kind of method that always returns a boolean. This
method could then be called with the name of the automaton and instructions on which
transitions should be followed by the automaton. If these transitions end in a final state,
the method would return `true`, else it would return `false`.
But to keep it simple here, we did not add this to the Type Check, and thus, the Type Check of SimpleJava can be
reused for the composed language. 
You are however welcomed to try adding an automaton as a new method.

### Context Conditions
For our composed language, we would like to reuse the context conditions of our sub
languages. This includes the CoCos we wrote for the Automata language concerning the
case of their names or the number of final and initial states as well as the Type Check
CoCos we implemented for the SimpleJava language. Because these CoCos all implement
the `Visitor2` interface of their corresponding language and the `CoCoChecker` uses a traverser
to store all CoCos that need to be executed on a model, 
 they can simply be added to
the traverser of a checker. 
Therefore, the CoCos of sub languages can also be reused in our composed language. 
Adding the CoCos of both languages to the CoCoChecker of the composed language is very simple:

```java
public class SimpleJavaWithAutomataCoCos {

  public SimpleJavaWithAutomataCoCoChecker getCoCoChecker() {
    SimpleJavaWithAutomataCoCoChecker checker = new SimpleJavaWithAutomataCoCoChecker();
    checker.addChecker(new AutomataCoCos().getCoCoChecker());
    ...
    return checker;
  }
  
}
```

As you can see, the `CoCoChecker` of the automata language is simply added to the `CoCoChecker` of our
composed language. 
This includes all of its CoCos. If the composition of the two languages
made some things possible that should not be possible, the language developer would need
to write a new CoCo for the composed language that forbids these things. You could e.g.
constrict the number of automata in a JavaBlock to one. This CoCo would have to be
added to the Checker then as well. See the `CoCoTest` for tests concerning the CoCos.
There are multiple tests for the Type Check CoCos of SimpleJava and another test with
an invalid automaton where one of the Automata CoCos should log an error.

A fair *warning*: The CoCos, symbol table creators, traverser, etc. of the individual language components
 may use their languages specific Mill.
Always set up your own language test/tool/... by calling `SimpleJavaWithAutomataMill.init()`.
This lets the various mills of the component languages know, that a more concrete mill exists (static-delegator pattern).
Otherwise, a traverser might now work as expected. 


### Exercise 2
Switch to your IDE.
Complete the `getCoCoChecker` method of the `SimpleJavaWithAutomataCoCos` class by 
 adding the previously designed and implemented automata context conditions.
Do this in a clever way and not by specifying each CoCo individually.
Run all tests of the `CoCoTest` class.
Remember, that you might have to remove some `@Ignore` annotations.
<!-- (c) https://github.com/MontiCore/monticore -->

Next, continue with [Chapter 4](../04.website/README.md)
