<!-- (c) https://github.com/MontiCore/monticore -->
# SimpleJava

In this tutorial chapter, we have a deeper look at a more sophisticated 
language engineering endeavour, `SimpleJava`.

## Language, Models, and Grammars 
The name SimpleJava describes the language itself very well. 
The language is very close to Java, it allows to create classes with methods 
and attributes, and it allows using other classes and methods of other classes.
Furthermore, classes can extend one another like in Java. 
However, it is much simpler than Java because it does not allow other 
meta-types such as interfaces, abstract classes, or enums. 
Moreover, modifiers such as `private` or `static` cannot be used in this language.

The grammar is shown below:

```mc4
grammar SimpleJava extends de.monticore.types.MCSimpleGenericTypes,
                            de.monticore.expressions.CommonExpressions,
                            de.monticore.expressions.AssignmentExpressions,
                            de.monticore.expressions.BitExpressions,
                            de.monticore.literals.MCCommonLiterals,
                            de.monticore.symbols.OOSymbols {

  JavaCompilationUnit = MCPackageDeclaration?
                        MCImportStatement*
                        JavaArtifact;

  scope symbol JavaArtifact implements Type = "class" Name 
      ("extends" superType:MCType)?
      "{" JavaElement* "}";

  interface JavaElement;



  scope symbol JavaMethod implements JavaElement,Function = MCReturnType
                                                           Name
                                                           FormalParameters
                                                           "{" JavaBlock "}";


  FormalParameters = "(" FormalParameter* ")";

  FormalParameter = MCType Name;

  JavaBlock = (JavaVarDecl | Expression)*;

  symbol JavaVarDecl implements JavaElement,Variable = MCType Name ("=" Expression)?;

}
```

As you can see, the grammar itself, although claiming to support a certain 
portion of Java syntax, is in itself still quite easy to easy. 
The reason is, that it is not extended from scratch but reuses multiple 
language components.
It extends several grammars that are provided by MontiCore as base grammars. 
The grammar `MCSimpleGenericTypes` adds types like primitives such as `int`, 
qualified types such as `java.lang.String` and even generic types such as 
`java.util.List<String>` to the language. 
The three expression grammars `CommonExpressions`, `AssignmentExpressions` 
and `BitExpressions` add basic expressions to the language such as `3+4`, 
`methodCall(arg)`, `variable++`, or `variable -= 3`. 
Next is the grammar `MCCommonLiterals`, which introduces basic literals 
such as string literals in quotation marks, `int` or `double` literals, 
i.e., numbers and  decimal numbers, to the language, that can be used in 
expressions. 
Finally, the grammar `OOSymbols` adds symbols like the `TypeSymbol`, 
`MethodSymbol` and `VariableSymbol` to the grammar that can be used in the 
symbol table. 
A SimpleJava model contains one `JavaCompilationUnit`. 
In this `JavaCompilationUnit`, the package of the model can be declared and 
other models can be imported with standard import statements. 
Furthermore, it refers the nonterminal `JavaArtifact` which stands for the 
class that is declared in a model. 
A `JavaArtifact` implements the interface Type so that the symbol class 
`JavaArtifactSymbol` extends the MontiCore class `TypeSymbol`, meaning that 
`JavaArtifacts` can be resolved in the symbol table as types.
It is composed of the same things that a real Java class is composed of, as 
it starts with the keyword `"class"` and the name of the class, it can extend 
another type with the help of the keyword `"extends"` and the name of a class 
that it extends, and it begins and ends with a left and right parenthesis
which contains other `JavaElements`. 
It is a symbol so that the class can be referenced in the model itself or in 
other models/classes. 
Since a class contains methods and attributes, it spans a scope to store their 
symbols.

`JavaElement` is an interface production that is implemented by the two 
nonterminals `JavaMethod` and `JavaVarDecl` which define methods and 
variables/attributes. 
In the SimpleJava language, attributes and variables are the same.
They have a type and a name and can be initialized by an expression like a 
method call or an operation like `3+4`.
Its corresponding production `JavaVarDecl` defines a symbol so that the 
attribute or the variable can be referred to in other places. 
It implements the interface `Variable` so that the generated class 
`JavaVarDeclSymbol` extends the MontiCore class `VariableSymbol`,
guaranteeing that `JavaVarDeclSymbols` can be resolved as `VariableSymbols` 
in the symbol table. 
The production `JavaMethod` spans a scope to store the variables in its body 
and defines a symbol so that methods can be called in other methods. 
Additional to `JavaElement`, it implements the interface `Function` so that 
the generated symbol class `JavaMethodSymbol` is a subtype of the MontiCore 
symbol `FunctionSymbol` and can be resolved as a `FunctionSymbol`.
A method has a return type, a name and parameters which consist of a type and 
a name. 
Like a class, a method has a body that starts after the left parenthesis and 
ends before the right parenthesis. 
This body is a `JavaBlock` which contains an arbitrary number of variable 
declarations and other expressions like method calls and assignments to 
already existing variables.

A simple model of this language is shown below:

```java
package tutorial.simplejava.valid;

class Bar {
  int max = 3
  int age = 1

  int getMax() {
    double d = 3.4
    double f = d + 1
    print(max * 4)
  }

  int getAge() {
    boolean b = true
    b = false
  }

}
```

It shows the class Bar that has two methods `getMax` and `getAge` which are 
getters for the attributes `max` and `age`.
The methods do not contain `return` statements as these are not defined in 
the grammar.
In this example, the class lies in the package `tutorial.simplejava.valid`.

<!-- (c) https://github.com/MontiCore/monticore -->
## TypeCheck
### What is a TypeCheck?
When creating a modern programming language, language developers will, in most 
cases, not be able to avoid creating a Type Check for the language. 
This is because most modern programming languages have types and expressions 
that can be evaluated to types. 
In Java (and SimpleJava as well), each class like `java.lang.String` or 
primitive types like `int` are types. 
Expressions like `3+4` or `foo(3)` can be evaluated to these types.
`3+4` should result in the type `int` because both `3` and `4` are whole 
numbers and are thus derived to the type `int` and the addition of two integer 
numbers should result in another `int`.
The expression `foo(3)` can be evaluated to the return type of the function 
`foo` if the correct parameters are given to the function.

A Type Check is necessary to derive a type for an expression and 
preventing type errors like assigning a number to a String. 
Consider the declaration `int a = 12` of the variable `a`. 
The variable has a declared type `int`.
If the expression `12` would not be evaluated to
the type `int` as well or one of its subtypes, the assignment results in an 
error. 
In this case, the expression `12` results in the type `int` and the assignment 
is correct. 
If the expression was something such as `false` or `"bar"` instead, the Type 
Check would have to log an error because of a wrong assignment. 
Ensuring that type errors like these do not happen is the primary task of the 
Type Check. 
It ensures the type safety of a language.

### TypeCheck in MontiCore
For a language like `SimpleJava` to be complete and correct, it must be type 
safe and thus requires a Type Check to prevent type errors. 
MontiCore offers a basic Type Check infrastructure that can be adapted and 
extended by every language that extends the grammar `BasicSymbols`.
This is necessary because the TypeCheck heavily uses the important symbols 
`TypeSymbol`, `VariableSymbol` and `FunctionSymbol` of the `BasicSymbols` 
grammar.
The productions `JavaArtifact`, `JavaVarDecl` and `JavaMethod` of the 
`SimpleJava` grammar add symbols for classes, variables and methods to the 
language.
Because they each implement one of these important symbol productions of 
the grammar `BasicSymbols`, their corresponding generated symbol classes 
extend the symbol classes `TypeSymbol`, `VariableSymbol` and 
`FunctionSymbol` and can thus also be used by MontiCore's basic Type Check 
infrastructure.

MontiCore's Type Check infrastructure consists of multiple parts:
*  BasicSymbols grammar
*  SymTypeExpression classes
*  Type Visitors
*  TypeCheck3 and SymTypeRelations interfaces

As explained above, the basis for working with the Type Check is the 
`BasicSymbols` grammar.
The Type Check works with the symbols introduced in this grammar, hence why
a language must extend the `BasicSymbols` grammar to use the Type Check. 
One of the three symbols is the `TypeSymbol` which (in Java) stands for 
classes and primitive types.
While the `TypeSymbol` symbolizes a type definition, a `SymTypeExpression` 
stands for a type usage.
In Java terms: The declaration of a class is represented by a type symbol,
the usage of a class is represented by a `SymTypeExpression`.
`java.util.List<E>` is the standard `TypeSymbol`/declaration of Java's often 
used `List` class.
To use this class, a `SymTypeExpression` like `java.util.List<String>` is 
necessary which replaces the type variable `E` with an actual type `String`.
The type of a variable, the super types of a class, and the return type of 
a method are `SymTypeExpressions` or type usages as they are not declarations 
of new classes and thus do not add a new type symbol to the symbol table.

Because a type usage should always refer the definition of the type to have 
an idea which methods and attributes/variables can be used, 
 most `SymTypeExpressions` know their corresponding `TypeSymbol`.
For example: The `get(int)` method is called on a variable `myList` of type `List<String>`.
The type of `myList` is represented as a `SymTypeOfGenerics`,
 with a reference to the `TypeSymbol` of the `List` class
 and a further `SymTypeOfObject` for the `String` type parameter.
Note: Not all `SymTypeExpressions` are guaranteed to have a type definition.

When asking the `List<String>` type for the return type
of its `get` method, its type definition `List<E>` is asked for all its 
methods named `get` with the matching signature.
The type symbol returns a method with the return type `E`, the type variable 
of the `List` class. 
Next, the type-check replaces the type variable `E` with the actual 
type argument `String` and returns this type information. 
If the parameter of the `get` method was of the correct type,
the whole expression `get(12)` will return the type `String` as it is the 
return type of the method.
MontiCore differentiates between multiple `SymTypeExpressions`
that all extend the abstract class `SymTypeExpression` and all stand for 
different kinds of type usages:

* `SymTypePrimitive` for primitive types such as `int` or `char`
* `SymTypeVoid` for the type `void`
*  `SymTypeOfNull` for the type `null`
* `SymTypeOfObject` for normal types like `java.lang.String` or `de.monticore.Foo`
* `SymTypeOfGenerics` for generic types like `List<String>` or `Map<Foo,Bar>`
* `SymTypeVariable` for referencing type variables like `E`. 
This is mainly used for the methods of a type like `List<E>`. 
The return type of the `get` method must be `E` and a `SymTypeExpression`
* `SymTypeArray` for array types like `int[]` or `String[][][]`
* `SymTypeOfWildcard` for types like `? extends String`, `? super Foo>` and `?`
* `SymTypeOfFunction` for functional programming and function types like `int -> String`
* `SymTypeObscure` if the Type Check does not find the correct type or finds 
a type error


Each of them introduces different specialized methods for the kind of types 
they represent.
A `SymTypeOfGenerics` `Map<String,Foo>` for example knows its type definition, 
i.e., a type symbol `Map<K,V>`. 
Additionally, the `SymTypeOfGenerics` has an attribute argumentList of the 
type `List<SymTypeExpression>` to store its actual type arguments.
Therefore, the argument list of `Map<String,Foo>` would contain the two 
`SymTypeExpressions` `String` and `Foo`. 
A `SymTypeArray` additionally knows its dimensionality  as an integer number. 
One-dimensional arrays have the dimension 1, three-dimensional ones have the 
dimension 3. 
For more about `SymTypeExpressions`, see Chapter 18.6 of the MontiCore 
Handbook.

To compare types and the types that are derived for expressions, e.g., 
in an attribute declaration with immediate assignment `int a = 3`, the Type 
Check calculates a `SymTypeExpression` for both the type and the expression. 
After that, the `SymTypeExpressions` can be compared for compatibility in 
terms of an assignment. 
This means that the Type Check has to provide different functions:

* synthesizing a `SymTypeExpression` from an `MCType`
* deriving a `SymTypeExpression` from an `Expression`
* comparing `SymTypeExpressions` in terms of assignability

The first two are solved with visitors, handlers and traversers following the 
same concept.
For each grammar `A` that adds new types to a language, a class extending 
`AbstractTypeVisitor` has to be created that implements the `AHandler` 
and/or the `AVisitor2` interface. 
For each production that extends the basis type `MCType`, the traversal is 
edited by implementing either the `endVisit` or the `traverse` method. 
In this method, a `SymTypeExpression` for the type is created and stored as 
a result.
Generic types first synthesize the inner type (i.e., the type argument) to a 
`SymTypeExpression` and use it as an argument for a `SymTypeOfGenerics`. 
The synthesizing of an `MCMapType` `Map<String,Foo>` would consist of first 
trying to synthesize the type arguments `String` and `Foo` to 
`SymTypeExpressions`.
If this is possible, the whole type `Map<String,Foo>` is synthesized next by
creating a `SymTypeOfGeneric` with the `TypeSymbol` `Map`.
The synthesized `SymTypeExpressions` for `String` and `Foo` are then used as 
arguments for this `SymTypeOfGeneric`.


#### Exercise 1
Switch to your IDE. 
The skeleton of a type check for the grammar `SimpleJava` is already provided 
in the class `SimpleJavaTypeCheck3`.
The TypeCheck has to be initialized with the various TypeVisitors of its 
component grammars.

For example, all nonterminals of the AssignmentExpressions grammar are added 
to the TypeCheck with the following lines: 
```java
    AssignmentExpressionsCTTIVisitor visAssignmentExpressions = new AssignmentExpressionsCTTIVisitor();
    visAssignmentExpressions.setType4Ast(type4Ast);
    visAssignmentExpressions.setContext4Ast(ctx4Ast);
    traverser.add4AssignmentExpressions(visAssignmentExpressions);
    traverser.setAssignmentExpressionsHandler(visAssignmentExpressions);
```
First, the `type4Ast` which maps AST-nodes to their SymTypeExpressions and 
`ctx4Ast` which stores context during the inference are passed to the 
`AssignmentExpressionsCTTIVisitor`.
Next, this type visitor is added as both a visitor and a handler to the 
type-checking traverser.

Complete the `init` method by adding the correct visitors of the missing 
grammars that `SimpleJava` transitively extends to the traverser, like 
shown for `AssignmentExpressionsCTTIVisitor` above.
Execute the `TypeCheckTest` tests to check the correctness of your 
implementation.

*Hint:* You can find the existing grammar definitions on [github](https://github.com/MontiCore/monticore/tree/dev/monticore-grammar/src/main/grammars/de/monticore).

The various type checking classes are provided by MontiCore for every grammar 
that introduces expressions and literals. 
See [the documentation](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/java/de/monticore/types3/TypeSystem3.md) for more information about the type system.


The last two parts of MontiCore's Type Check that were not introduced yet are 
the classes `TypeCheck3` and `SymTypeRelations`. 
The `TypeCheck3` class exposes methods which can be used to synthesize 
`SymTypeExpressions` from types  or derive them from expressions and literals. 
It offers methods like `SymTypeExpression symTypeFromAST(ASTMCType mcType)` 
and `SymTypeExpression typeOf(ASTExpression expr)`  for those purposes. 
Means to checking whether a type is a subtype of another or whether an 
assignment is correct are provided by the `SymTypeRelations` class. 
The function of its methods `isSubTypeOf` and `isCompatible` should be 
self-explanatory.
This class is often used in Context Conditions to check if the declared 
type and the type of an initializing expression  of a variable declaration 
are compatible.

<!-- (c) https://github.com/MontiCore/monticore -->
## Symbol Table
### Symbol Table Creation

As explained in Section 1.6, a basic infrastructure for a symbol table is 
already generated for every MontiCore grammar. 
These are the symbol classes, the scope classes and interfaces, the 
`ScopesGenitor` and the `ScopesGenitorDelegator`
for the creation of a symbol table corresponding to an AST. 
Because it is only a *basic* infrastructure, some languages
need to supplement the infrastructure by overwriting the basic implementation. 
This can be done with the help of the TOP mechanism presented in Section 1.8. 
SimpleJava is such a language. 
One information that is not added to the symbol table automatically is
the package in which the model is located. 
This is because not every language needs a package statement as packages 
are irrelevant in some languages. 
In SimpleJava however, an artifact scope needs to know the package of the 
model so that symbols (like variables or classes) in the model can be 
resolvable from other models. 
Adding this information to the ArtifactScope can be done fairly easily in 
the SimpleJava language by overwriting the
`createFromAST` method of the class `SimpleJavaScopesGenitor`. 
The parameter of the type `ASTJavaCompilationUnit` can be used to get the 
package of the model. 
The ArtifactScope can be fetched by executing the `createFromAST` method 
of the super class.
After that, the package name can be added to the ArtifactScope before 
the ArtifactScope is returned.

#### Exercise 2
Switch to your IDE. 
Add the package of the model to the ArtifactScope by overwriting the
`createFromAST` method of the `SimpleJavaScopesGenitor` with the help 
of the TOP mechanism. 
Check your implementation by executing the method `testPackageSet` in 
the class `SymTabTest`.

The symbols in the grammar SimpleJava are subtypes of the `TypeSymbol`, 
`FunctionSymbol` and `VariableSymbol` of the grammar `BasicSymbols`.
The TypeSymbol has an additional attribute that stores its super types 
and a function and a variable have an additional attribute storing their type. 
All these attributes have the same type: `SymTypeExpression` (or a list of 
`SymTypeExpression`s for the super types).
This class `SymTypeExpression` is a non-generated class, so the generated 
ScopesGenitor classes do not know it and thus cannot use it. 
In the symbol table creation for the SimpleJava language, the ScopesGenitor 
would be responsible to create all symbols correctly and store them in the 
correct scopes. 
But it cannot create the symbols extending the `TypeSymbol`, `FunctionSymbol` 
and `VariableSymbol` correctly as it cannot fill all their attributes. 
Thus, the language developer must add the filling of the attributes of the 
type `SymTypeExpression` by themself. 
In the case of the symbols of SimpleJava, this is done with the help of the 
TypeCheck in another symbol table phase.

The primitive Java types, such as `int`, `float`, `boolean`, also have to 
be defined.
The `BasicSymbolsMill` provides a `initializePrimitives()` method, which adds 
symbols for these primitive types.
Similarly, the `String` type can be defined via the `initializeString()` 
method.
(Make sure to call `JavaDSLMill.init()` first, as the Mills use a 
static-delegator pattern to find the most-concrete mill, 
and `Mill#init()` sets these.)

For more complex languages as SimpleJava, the symbol table creation is split 
into two parts. 
The first part is done after the execution of the ScopesGenitor to create a 
basic symbol table for a model. 
But as explained above, this leaves the symbol classes extending the 
`TypeSymbol`, `FunctionSymbol` and `VariableSymbol` with some empty attributes.
For these attribtues, values are then assigned in the second phase of the 
symbol table creation.
The second phase consists of one or more visitor or handler classes that 
traverse the AST and fill the attributes correctly. 
The AST classes that have a link to the symbol table (such as `ASTJavaMethod` 
or `ASTJavaVarDecl` in the grammar SimpleJava) must then  be visited and their 
corresponding symbol fetched. 
Then, the attributes of the symbols can be filled however the language 
developer wants.

#### Exercise 3
The skeleton of a visitor for the second phase of the symbol table creation is 
displayed in the class `SimpleJavaSTCompleteTypes`. 
Implement the `visit` methods and use the `TypeCheck3` class to fill the 
missing type information of the symbols.
In this regards, the `JavaVarDeclSymbol` requires a type,
`JavaMethodSymbol` a return type,
and `JavaArtifactSymbol` a potential super type.
The test for correctness will be done after Exercise 4.

It is useful to combine both parts of the symbol table creation in a common 
class. 
This class needs to have two attributes: 
The ScopesGenitorDelegator of the language for the first phase and a list of 
traversers for the second phase.
In its constructor, it must add the visitors and handlers of phase two to a 
traverser and add it to the traverser list attribute.
To keep things consistent, the class needs to offer a `createFromAST` method
with the same parameter and return type like the methods in the 
ScopesGenitorDelegator and the ScopesGenitor. 
In this method, the `createFromAST` method of the ScopesGenitorDelegator (and 
therefore phase one of the symbol table creation) must be called first.
Then, all traversers in the traverser list are executed on the AST, i.e., 
the parameter.
After that, the ArtifactScope that was returned by the `createFromAST` method 
of the ScopesGenitorDelegator can be returned.

#### Exercise 4
The skeleton of a common class for executing both parts of the symbol table 
creation is displayed in the class `SimpleJavaPhasedSTC`. 
Implement the constructor and the `createFromAST` method correctly. 
Execute the test `testTypesSet` in the class `SymTabTest` to test your 
implementation from both Exercise 3 and 4.


### Symbol Table Import and Export

MontiCore provides language tooling for importing and exporting symbol tables.
This is called *serialization* of a symbol table. 
It is useful to store the symbol table of a model into a file for later use 
because loading the symbol table of a model from this file is faster than 
parsing the model and creating the symbol table by far. 
Loading a symbol table from a file is called *deserialization* of a
symbol table. 
Another use case of storing and loading symbols is loading symbol tables from 
models of other DSLs.
This means that symbols from the models of other languages can be deserialized 
and, therefore, made accessible in the symbol table. 
For this to be possible, the symbol table of each language had to be 
serialized into the same, language independent format.
The serialized symbol table did not necessarily need to be human-readable, 
 but it had to be compact so that storing it in files is possible. 
There are many languages like JSON, YAML, XML or other markup languages who 
could have been chosen for this. 
In MontiCore, symbol tables are serialized into a JSON format.

The following shows an excerpt from the serialized symbol table of the `Bar` class:
```json
{
 "generated-using": "www.MontiCore.de technology",
 "name": "Bar",
 "package": "tutorial.simplejava.valid",
 "symbols": [
  {
   "kind": "tutorial.simplejava._symboltable.JavaArtifactSymbol",
   "name": "Bar",
   "spannedScope": {
    "symbols": [
     {
      "kind": "tutorial.simplejava._symboltable.JavaMethodSymbol",
      "name": "getMax",
      "type": {
       "kind": "de.monticore.types.check.SymTypePrimitive",
       "primitiveName": "int"
      },
      "spannedScope": {
       "symbols": [
        {
         "kind": "tutorial.simplejava._symboltable.JavaVarDeclSymbol",
         "name": "d",
         "type": {
          "kind": "de.monticore.types.check.SymTypePrimitive",
          "primitiveName": "double"
         }
        },
        {
         "kind": "tutorial.simplejava._symboltable.JavaVarDeclSymbol",
         "name": "f",
         "type": {
          "kind": "de.monticore.types.check.SymTypePrimitive",
          "primitiveName": "double"
         }
        }
       ]
      }
     },
     ...
    }
  }
}
```
At first, it defines the name of the model and the name of the package it 
lies in. 
After that, the serialized symbols are stored in a list. 
For each symbol, its kind and name is saved. 
If a symbol spans a scope, the symbols in this scope are displayed as well. 
Additional attributes of the symbol like type in `JavaVarDeclSymbol` are 
serialized as well.

For the serialization and deserialization of a symbol table, MontiCore 
generates the class `ASymbols2Json` for a grammar `A`. 
Its method `store` needs an artifact scope and a `String`.
The artifact scope contains the symbol table of the model that will be 
serialized while the string contains the location of the file into which 
the symbol table is serialized. 
Thus, it serializes the symbol table of exactly one model into exactly 
one file. 
To load a symbol table from a file, the `ASymbols2Json` class offers the 
method `load`.
It has one parameter of the type `String` that should contain the location 
of the file in which the to-be-loaded symbol table is stored.
The method returns the artifact scope which is the top of the symbol table 
of the model and thus contains its whole symbol table.
This artifact scope can then be added as a subscope to the global scope 
of the language so that other models can resolve symbols of this loaded 
symbol table as well.

MontiCore is able to generate serialization and deser infrastructure for 
most symbols.
But symbols with custom information, i.e., _symbol rules_, require 
hand-written effort.

#### Exercise 5
For the SimpleJava grammar, all serialization and deserialization classes 
are already generated.
Go to the class `DeSerTest`. 
Implement the method `testSerialization` by storing the symbol table of 
the model Bar into a file. 
For this, you have to parse the model, create its symbol table and serialize 
it. 
Store the serialized symbol table in the file `target/symboltable/Bar.javasym`. 
After that, implement the method `testDeserialization`. 
Use the `SimpleJavaSymbols2Json` to deserialize the symbol table of the model.
Check that is stored under `src/test/resources/tutorial/simplejava/symboltable`. 
Add it as a subscope to the global scope and make sure that resolving the 
TypeSymbol `Check` is possible.
Next, ensure that the amount of super types the TypeSymbol `Check` equals 
`0`. (Hint: `.getSuperTypesList().size()`)



## Using existing Java types: Class2MC
To integrate your symbol table with an existing system, such as the Java runtime,
you can add all loaded classes, as well as their methods and fields to the symbol table.

Start by adding Class2MC as a dependency to your project:

```build.gradle
dependencies {
  implementation group: 'de.monticore', name: 'class2mc', version: mc_version
}
```

Next, add an adapted OOType symbol resolver to your languages global scope:

```java
SimpleJavaMill.globalScope().addAdaptedOOTypeSymbolResolver(new Class2MCResolver());
```

Now the symbol table will resolve for (object oriented) types in loaded class files 
(Note: Class2MC works on the compiled files, not the `.java` source files).

<!-- (c) https://github.com/MontiCore/monticore -->
## CoCos

To make the language usable and free from errors, CoCos must be created 
that further restrict it. 
Some of these CoCos should be the standard CoCos such as

* the name of classes should be capitalized
* the name of attributes and variables should be uncapitalized
* the name of methods should be uncapitalized

This type of Context Conditions was handled in Chapter 1 already and thus will 
not be handled here. 
We will focus on other types of Context Conditions that contain using the 
TypeCheck. 
There are two Context Conditions that should be implemented to guarantee 
type safety for a language. 
They should log errors if the type rules are broken. These CoCos are:

* If a variable is initialized immediately after its declaration, the type of the variable
  should be compatible to the expression initializing it
* An expression should be valid. Calling a method with wrong parameters or assigning
  the value 3 to an already existing boolean variable should result in an error

For both of these cases, one Context Condition is enough. In the first case, 
the CoCo should revolve around the type `ASTJavaVarDecl`. 
If a variable declaration has an expression that immediately initializes it, 
 the `SymTypeExpression` for both the declared type and the initializing 
expression should be calculated and compared. 
If the resulting `SymTypeExpression`s are not compatible (e.g., `int` and 
`boolean`), the Context Condition should log an error.
The `SymTypeRelations` has a method to check the compatibility of two 
`SymTypeExpression`s.
If a variable declaration does not have an expression, the CoCo should 
ignore this variable declaration.
The second case is a little different and should use the class `ASTExpression` 
of the grammar `ExpressionsBasis`. 
This class is an interface that all expressions implement.
Therefore, the validity of all expressions can be checked with a CoCo 
specified to this interface. 
The TypeCheck should be used to derive a `SymTypeExpression` from the 
`ASTExpression`. 
If the resulting `SymTypeExpression` is obscure, this means that the TypeCheck 
could not derive a type for the `ASTExpression` which means that the 
expression is not valid as the types of its subexpressions do not match. 
In this case, an error should be logged.

#### Exercise 6
The skeletons for the Context Conditions `ExpressionIsValid`  and 
`VariableDeclarationIsCorrect` are already given in the tutorial project. 
Implement these Context Conditions like described above and check their 
correctness by executing all tests in the class `CoCoTest`.

(*Hint*: Be aware, that the expression of variable declarations is optional.)
<!-- (c) https://github.com/MontiCore/monticore -->

Next, continue with [Chapter 3](../03.composition/README.md)
