<!-- (c) https://github.com/MontiCore/monticore -->
# SimpleJava
## Language, Models and Grammars 
The name SimpleJava describes the language itself very well. 
The language is very close to Java, it allows to create classes with methods and attributes, 
 and it allows using other classes and methods of other classes. 
Furthermore, classes can extend one another like in Java. 
But it is much simpler than Java because it does not allow other types such as interfaces, abstract classes or enums. 
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

It extends several grammars that are provided by MontiCore as base grammars. 
The grammar `MCSimpleGenericTypes` adds types like primitive types such as `int`, 
 qualified types such as `java.lang.String` and even generic types such as `java.util.List<String>` to the language. 
The three expression grammars `CommonExpressions`, `AssignmentExpressions` and `BitExpressions`
add basic expressions to the language such as `3+4`, `methodCall(arg)`, `variable++`
or `variable -= 3`. 
Next is the grammar `MCCommonLiterals` which introduces basic literals such as string literals in quotation marks, 
 `int` or `double` literals, i.e. numbers and  decimal numbers, to the language, that can be used in expressions. 
Last, the grammar `OOSymbols` adds symbols like the `TypeSymbol`, `MethodSymbol` and `VariableSymbol` to the grammar 
 that can be used in the symbol table. 
A SimpleJava model contains one `JavaCompilationUnit`. 
In this `JavaCompilationUnit`, the package of the model can be declared and other models can be imported with 
 standard import statements. 
Furthermore, it refers the nonterminal `JavaArtifact` which stands for the class that is declared in a model. 
A `JavaArtifact` implements the interface Type so that the symbol class `JavaArtifactSymbol` extends
 the MontiCore class `TypeSymbol`, meaning that `JavaArtifacts` can be resolved in the symbol table as types.
It is composed of the same things that a real Java class is composed of, as it starts with the keyword `"class"` 
and the name of the class, it can extend another type with the help of the keyword `"extends"` and
the name of a class that it extends, and it begins and ends with a left and right parenthesis
which contains other `JavaElements`. 
It is a symbol so that the class can be referenced in the model itself or in other models/classes. 
Since a class contains methods and attributes, it spans a scope to store their symbols.

`JavaElement` is an interface production that is implemented by the two nonterminals `JavaMethod` and `JavaVarDecl`
 which define methods and variables/attributes. 
In the SimpleJava language, attributes and variables are the same.
They have a type and a name and can be initialized by an expression like a method call or an operation like `3+4`.
Its corresponding production `JavaVarDecl` defines a symbol so that the attribute or the variable
can be referred to in other places. 
It implements the interface `Variable` so that the generated class `JavaVarDeclSymbol` extends the MontiCore class `VariableSymbol`,
guaranteeing that `JavaVarDeclSymbols` can be resolved as `VariableSymbols` in the symbol table. 
The production `JavaMethod` spans a scope to store the variables in its body and
 defines a symbol so that methods can be called in other methods. 
Additional to `JavaElement`, it implements the interface `Function` so that the generated symbol class
`JavaMethodSymbol` is a subtype of the MontiCore symbol `FunctionSymbol` and can be
resolved as a `FunctionSymbol`.
A method has a return type, a name and parameters which consist of a type and a name. 
Like a class, a method has a body that starts after the left parenthesis and ends before the right parenthesis. 
This body is a `JavaBlock` which contains an arbitrary number of variable declarations and other expressions like method
calls and assignments to already existing variables.

A simple model of this language is shown below:

```java
package de.monticore.gettingstarted.simplejava.valid;

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

It shows the class Bar that has two methods `getMax` and `getAge` which are getters for the attributes `max` and `age`.
The methods do not contain `return` statements as these are not defined in the grammar.
In this example, the class lies in the package `de.monticore.gettingstared.simplejava.valid`.

<!-- (c) https://github.com/MontiCore/monticore -->
## TypeCheck
### What is a TypeCheck?
When creating a modern programming language, language developers will in most cases
not be able to avoid creating a Type Check for the language. 
This is because most modern programming languages have types and expressions that can be evaluated to types. 
In Java (and SimpleJava as well), each class like `java.lang.String` or primitive types like `int`
are types. 
Expressions like `3+4` or `foo(3)` can be evaluated to these types.
`3+4` should result in the type `int` because both `3` and `4` are whole numbers and are thus derived to
the type `int` and the addition of two integer numbers should result in another `int`.
The expression `foo(3)` can be evaluated to the return type of the function `foo` if the correct parameters are given to the function.

A Type Check is necessary to derive a type for every expression and preventing type errors
like assigning a number to a String. Consider the declaration `int a = 12` of the variable
`a`. The variable has a declared type `int`.
If the expression `12` would not be evaluated to
the type `int` as well or one of its subtypes, the assignment results in an error. 
Luckily, the expression `12` results in the type `int` and the assignment is correct. 
If the expression had been `false` or `"bar"` instead, the Type Check would have to log an error because
of a wrong assignment. 
Ensuring that type errors like these do not happen is the primary  task of the Type Check. 
It ensures the type safety of a language.

### TypeCheck in MontiCore
For a language like `SimpleJava` to be complete and correct, it must be type safe and thus
requires a Type Check to prevent type errors. 
MontiCore offers a basic Type Check infrastructure that can be adapted and extended by every language that extends the grammar `BasicSymbols`.
This is necessary because the TypeCheck heavily uses the important symbols `TypeSymbol`, `VariableSymbol` and `FunctionSymbol` of the `BasicSymbols` grammar.
The productions `JavaArtifact`, `JavaVarDecl` and `JavaMethod` of the  `SimpleJava` grammar add symbols for classes, variables and methods to the language.
Because they each implement one of these important symbol productions of the grammar
`BasicSymbols`, their corresponding generated symbol classes extend the symbol classes
`TypeSymbol`, `VariableSymbol` and `FunctionSymbol` and can thus also be used by
MontiCore's basic Type Check infrastructure.

MontiCore's Type Check infrastructure consists of multiple parts:
*  BasicSymbols grammar
*  SymTypeExpression classes
*  TypeCheckResult class
*  Synthesize classes and FullSynthesizer
*  Derive classes and FullDeriver
*  TypeCheck and TypeCalculator interfaces

As explained above, the basis for working with the Type Check is the `BasicSymbols` grammar.
The Type Check works with the symbols introduced in this grammar, hence why
a language must extend the `BasicSymbols` grammar to use the Type Check. 
One of the three symbols is the `TypeSymbol` which (in Java) stands for classes and primitive types.
While the `TypeSymbol` symbolizes a type definition, a `SymTypeExpression` stands for
a type usage.
In Java terms: The declaration of a class is represented by a type symbol,
the usage of a class is represented by a `SymTypeExpression`.
`java.util.List<E>` is the standard `TypeSymbol`/declaration of Java's often used `List` class.
To use this class, a `SymTypeExpression` like `java.util.List<String>` is necessary which replaces the
type variable `E` with an actual type `String`.
The type of a variable, the super types of a class and the return type of a method are `SymTypeExpressions` or type usages as they
are not declarations of new classes and thus do not add a new type symbol to the symbol table.

Because a type usage should always refer the definition of the type to have an idea which
methods and attributes/variables can be used, a `SymTypeExpression` always knows its
corresponding `TypeSymbol`.
When asking the class `List<String>` for the return type
of its `get` method, it will ask its type definition `List<E>` for all its methods named
`get`.
The type symbol returns a method with the return type `E`, the type variable of the
class. 
Next, the `SymTypeExpression` replaces the type variable `E` with its actual type
argument `String` and returns the method. 
If the parameter of the `get` method was of the correct type,
 the whole expression `get(12)` will return the type `String` as it is the return
type of the method.
MontiCore differentiates between multiple `SymTypeExpressions`
that all extend the abstract class `SymTypeExpression` and all stand for different kinds
of type usages:

* `SymTypePrimitive` for primitive types such as `int` or `char`
* `SymTypeVoid` for the type `void`
*  `SymTypeOfNull` for the type `null`
* `SymTypeOfObject` for normal types like `java.lang.String` or `de.monticore.Foo`
* `SymTypeOfGenerics` for generic types like `List<String>` or `Map<Foo,Bar>`
* `SymTypeVariable` for referencing type variables like `E`. This is mainly used for the
  methods of a type like `List<E>`. The return type of the `get` method must be `E`
  and a `SymTypeExpression`
* `SymTypeArray` for array types like `int[]` or `String[][][]`
* `SymTypeOfWildcard` for types like `? extends String`, `? super Foo>` and
  `?`
* `SymTypeOfFunction` for functional programming and function types like `int -> String`
* `SymTypeObscure` if the Type Check does not find the correct type or finds a type error


Each of them introduce different specialized methods for the kind of types they represent.
A `SymTypeOfGenerics` `Map<String,Foo>` for example knows its type definition, 
 i.e. a type symbol `Map<K,V>`. 
Additionally, the `SymTypeOfGenerics` has an attribute argumentList of the type `List<SymTypeExpression>` to store its actual type arguments.
Therefore, the argument list of `Map<String,Foo>` would contain the two `SymTypeExpressions` `String` and `Foo`. 
A `SymTypeArray` additionally knows its dimensionality  as an integer number. 
One-dimensional arrays have the dimension 1, three-dimensional
ones have the dimension 3. 
For more about `SymTypeExpressions`, see Chapter 18.6 of the MontiCore Reference Manual.

To compare types and the types that are derived for expressions, e.g. in an attribute
declaration with immediate assignment `int a = 3`, the Type Check calculates a `SymTypeExpression` for both the type and the expression. 
After that, the `SymTypeExpressions` can be compared for compatibility in terms of an assignment. 
This means that the Type Check has to provide different functions:

* synthesizing a `SymTypeExpression` from an `MCType`
* deriving a `SymTypeExpression` from an `Expression`
* comparing `SymTypeExpressions` in terms of assignability

The first two are solved with visitors, handlers and traversers. They have the same concept.
For each grammar `A` that adds new types to a language, a class `SynthesizeSymType` `FromA` is created that implements the `AHandler` and/or the `AVisitor2` interface. 
For each production that extends the basis type `MCType`, 
 the traversal is edited by implementing either the `endVisit` or the `traverse` method. 
In this method, a `SymTypeExpression` for the type is created and stored as a result.
Generic types first synthesize the inner type (i.e. the type argument) to a `SymTypeExpression` and use it as an argument for a
`SymTypeOfGenerics`. 
The synthesizing of an `MCMapType` `Map<String,Foo>` would consist of first trying to synthesize the type arguments `String` and `Foo` to `SymTypeExpressions`.
If this is possible, the whole type `Map<String,Foo>` is synthesized next by
creating a `SymTypeOfGeneric` with the `TypeSymbol` `Map`.
The synthesized `SymTypeExpressions` for `String` and `Foo` are used as arguments for this `SymTypeOfGeneric`.
The actual implementation for `ASTMCMapType` is displayed below and can be found
in MontiCore's class `SynthesizeSymTypeFromMCCollectionTypes`. 
MontiCore provides a basic implementation of a `Synthesize` class for every standard MontiCore grammar that introduces types. 
This class can be used and/or extended by language developers for their own language.

```java
public void traverse(ASTMCMapType node){
  List<TypeSymbol> mapSyms=getScope(node.getEnclosingScope())
  .resolveTypeMany("Map");
  
  // Argument 1:
  if(null!=node.getKey()){
    node.getKey().accept(getTraverser());
  }
  TypeCheckResult keyTypeResult=getTypeCheckResult().copy();
  
  // Argument 2:
  if(null!=node.getValue()){
    node.getValue().accept(getTraverser());
  }
  TypeCheckResult valueTypeResult=getTypeCheckResult().copy();
  
  if(!keyTypeResult.isPresentResult()){
      Log.error("0xE9FDD Missing SymType argument 1 for Map type.");
      getTypeCheckResult()
        .setResult(SymTypeExpressionFactory.createObscureType());
  }else if(!valueTypeResult.isPresentResult()){
      Log.error("0xE9FDE Missing SymType argument 2 for Map type.");
      getTypeCheckResult()
        .setResult(SymTypeExpressionFactory.createObscureType());
  }else{
      if(!keyTypeResult.getResult().isObscureType()
        &&!valueTypeResult.getResult().isObscureType()){
          if(mapSyms.size()==1){
              SymTypeExpression keyTypeExpr=keyTypeResult.getResult();
              SymTypeExpression valueTypeExpr=valueTypeResult.getResult();
              SymTypeExpression typeExpression=
              SymTypeExpressionFactory.createGenerics(mapSyms.get(0),
                keyTypeExpr,valueTypeExpr);
              getTypeCheckResult().setResult(typeExpression);
              node.setDefiningSymbol(typeExpression.getTypeInfo());
          }else{
              Log.error("0xE9FDC multiple or no matching types were found for ’Map’");
              getTypeCheckResult()
                .setResult(SymTypeExpressionFactory.createObscureType());
          }
      }else{
          getTypeCheckResult()
            .setResult(SymTypeExpressionFactory.createObscureType());
      }
  }
}
```

\newpage
Combining the `Synthesize` classes for every grammar that add types and are (transitively) extended by the grammar `A` and therefore combining their calculations is done by
creating a `FullSynthesizeFromA` class.
This class combines them by adding all of them to an `ATraverser` and giving them the same instance of the class `TypeCheckResult` to store all their (intermediate) results in. 
They extend the abstract class `AbstractSynthesize` that provides the function `synthesizeType` to synthesize a type to a `SymTypeExpression`. 
The `FullSynthesize` class for the grammar `MCFullGenericTypes` can be seen below:


```java
public class FullSynthesizeFromMCFullGenericTypes
                extends AbstractSynthesize {

     public FullSynthesizeFromMCFullGenericTypes(){
      this(MCFullGenericTypesMill.traverser());
     }
    
     public FullSynthesizeFromMCFullGenericTypes(
         MCFullGenericTypesTraverser traverser){
         super(traverser);
         init(traverser);
     }
    
     public void init(MCFullGenericTypesTraverser traverser) {
         SynthesizeSymTypeFromMCFullGenericTypes synFromFull =
          new SynthesizeSymTypeFromMCFullGenericTypes();
         synFromFull.setTypeCheckResult(getTypeCheckResult());
         SynthesizeSymTypeFromMCSimpleGenericTypes synFromSimple =
          new SynthesizeSymTypeFromMCSimpleGenericTypes();
         synFromSimple.setTypeCheckResult(getTypeCheckResult());
         SynthesizeSymTypeFromMCCollectionTypes synFromCollection =
          new SynthesizeSymTypeFromMCCollectionTypes();
         synFromCollection.setTypeCheckResult(getTypeCheckResult());
         SynthesizeSymTypeFromMCBasicTypes synFromBasic =
          new SynthesizeSymTypeFromMCBasicTypes();
         synFromBasic.setTypeCheckResult(getTypeCheckResult());
        
         traverser.add4MCFullGenericTypes(synFromFull);
         traverser.setMCFullGenericTypesHandler(synFromFull);
         traverser.add4MCSimpleGenericTypes(synFromSimple);
         traverser.setMCSimpleGenericTypesHandler(synFromSimple);
         traverser.add4MCCollectionTypes(synFromCollection);
         traverser.setMCCollectionTypesHandler(synFromCollection);
         traverser.add4MCBasicTypes(synFromBasic);
         traverser.setMCBasicTypesHandler(synFromBasic);
     }

 }
```

#### Exercise 1
Switch to your IDE. 
The skeleton of a `FullSynthesize` class for the grammar `SimpleJava` is already provided in the class `FullSynthesizeFromSimpleJava`.
Implement the `init` method by adding the `Synthesize` classes of all grammars that `SimpleJava` transitively extends to a traverser and passing them the same instance of a `TypeCheckResult` like shown for `FullSynthesizeFromMCFullGenericTypes` above.
Execute the test `testSynthesizer` in the class `TypeCheckTest` to check the correctness of your implementation.

*Hint:* You can find the existing grammar definitions on [github](https://github.com/MontiCore/monticore/tree/dev/monticore-grammar/src/main/grammars/de/monticore).

Deriving a `SymTypeExpression` from an expression works the same way as synthesizing it from a type. 
For each grammar `A` that introduces expressions, a class `DeriveSymTypeOfA` is created that implements the `AHandler` and/or `AVisitor2` interface. 
For every production extending or implementing the production `Expression` transitively, a
`traverse` or `endVisit` method is programmed that is able to calculate a `SymTypeExpression` for the expression. 
See below for an example of a traverse method:

```java
public void traverse(ASTPlusExpression expr) {
 List<SymTypeExpression> innerTypes = calculateInnerTypes(expr.getLeft(),
        expr.getRight());
 if(checkNotObscure(innerTypes)){
     //calculate
     SymTypeExpression wholeResult = calculatePlusExpression(expr, 
        innerTypes.get(0), innerTypes.get(1));
     storeResultOrLogError(wholeResult, expr, "0xA0210");
 }else{
     getTypeCheckResult().reset();
     getTypeCheckResult().
     setResult(SymTypeExpressionFactory.createObscureType());
 }
}
```

This is done for a `PlusExpression` like `3+4` or `a + map.get("Foo")`. 
At first, `SymTypeExpressions` are derived for both subexpressions left and right from the plus. 
If there was no type error in those subexpressions, the logic behind the calculation is executed in
the method `calculatePlusExpression` that compares the `SymTypeExpressions`
for both subexpressions in the terms of the operator `"+"`. 
If they are both `int`, the result will be `int`. 
If however one of them is a `String`, the result (in Java) should be `String` as well. 
If one of them has the type `Person`, an error will be returned as the operator `"+"` cannot be applied to this type.
After the resulting `SymTypeExpression` was derived, it is stored in an instance of the class `TypeCheckResult`.

These Derive classes are provided by MontiCore for every grammar that introduces expressions and literals. 
Similar to the `FullSynthesize` class, a `FullDerive` class can combine
`Derive` classes of different grammars and thus combine their derivations of expressions.
The class `FullDeriveFromSimpleJava` has a similar structure as the class `FullSynthesizeFromSimpleJava`. 
It extends the abstract class `AbstractDerive` instead of
`AbstractSynthesize`.
This abstract class provides a method `deriveType` to derive a
`SymTypeExpression` for a given `ASTExpression`. 
It has the same two constructors as
the `FullSynthesize` class and its `init` method consists of creating the `Derive` classes of the
grammars introducing expressions or literals that `SimpleJava` extends, passing them the
same instance of `TypeCheckResult` and adding them to a `SimpleJavaTraverser`.


The last two parts of MontiCore's Type Check that were not introduced yet are the
classes `TypeCheck` and `TypeCalculator`. 
The `TypeCalculator` class needs one `FullDerive` and one `FullSynthesize` class for the same
 grammar and can be used as a common interface to synthesize `SymTypeExpressions` from types 
 or derive them from expressions and literals. 
It offers methods like `symTypeFromAST` and `typeOf` for those purposes. 
Means to checking whether a type is a subtype of another
 or whether an assignment is correct are provided by the `TypeCheck` class. 
The function of its methods `isSubtypeOf` and `compatible` should be self-explanatory.
This class is often used in Context Conditions to check if the declared type and the type of an initializing expression  of a variable declaration are compatible.


#### Exercise 2
Switch to your IDE. The skeleton of a `FullDerive` class for the grammar `SimpleJava` is already provided in the class `FullDeriveFromSimpleJava`. 
Implement the `init` method by adding the `Derive` classes of all grammars that `SimpleJava` transitively extends 
 to a traverser and passing them the same instance of a `TypeCheckResult` similar  to the last exercise. 
Execute the method `testDeriver` to check the correctness of your implementation.

*Hint:* Not all derivers implement the `Handler` interface.
Especially ones for Literals may only be visitors and thus only be added as visitors to the traverser.
<!-- (c) https://github.com/MontiCore/monticore -->
## Symbol Table
### Symbol Table Creation

As explained in Section 2.4, a basic infrastructure for a symbol table is already generated
for every MontiCore grammar. 
These are the symbol classes, the scope classes and interfaces, the `ScopesGenitor` and the `ScopesGenitorDelegator` 
 for the creation of a symbol table corresponding to an AST. 
Because it is only a *basic* infrastructure, some languages
need to supplement the infrastructure by overwriting the basic implementation. This can
be done with the help of the TOP mechanism presented in Section 2.5. 
SimpleJava is such a language. 
One information that is not added to the symbol table automatically is
the package in which the model is located. This is because not every language needs a
package statement as packages are irrelevant in some languages. In SimpleJava however,
an artifact scope needs to know the package of the model so that symbols (like variables
or classes) in the model can be resolvable from other models. 
Adding this information to the ArtifactScope can be done fairly easily in the SimpleJava language by overwriting the
`createFromAST` method of the class `SimpleJavaScopesGenitor`. The parameter of
the type `ASTJavaCompilationUnit` can be used to get the package of the model. The
ArtifactScope can be fetched by executing the `createFromAST` method of the super class.
After that, the package name can be added to the ArtifactScope before the ArtifactScope
is returned.

#### Exercise 3
Switch to your IDE. Add the package of the model to the ArtifactScope by overwriting the
`createFromAST` method of the `SimpleJavaScopesGenitor` with the help of the TOP mechanism. 
Check your implementation by executing the method `testPackageSet` in the class `SymTabTest`.

The symbols in the grammar SimpleJava are subtypes of the `TypeSymbol`, `FunctionSymbol` 
 and `VariableSymbol` of the grammar `BasicSymbols`.
The TypeSymbol has an additional attribute that stores its super types and a function and a variable have an
additional attribute storing their type. 
All these attributes have the same type: `SymTypeExpression` (or a list of `SymTypeExpression`s for the super types).
This class `SymTypeExpression` is a non-generated class, so the generated ScopesGenitor classes
do not know it and thus cannot use it. 
In the symbol table creation for the SimpleJava language, the ScopesGenitor would be responsible
 to create all symbols correctly and store them in the correct scopes. 
But it cannot create the symbols extending the `TypeSymbol`, `FunctionSymbol` and `VariableSymbol` correctly
 as it cannot fill all their attributes. 
Thus, the language developer must add the filling of the attributes of the type `SymTypeExpression` by themself. 
In the case of the symbols of SimpleJava, this is done with the help of the TypeCheck in another symbol table phase.


The primitive Java types, such as `int`, `float`, `boolean`, also have to be defined.
The `BasicSymbolsMill` provides a `initializePrimitives()` method, 
 which adds symbols for these primitive types.
Similarly, the `String` type can be defined via the `initializeString()` method.
(Make sure to call `JavaDSLMill.init()` first, 
 as the Mills use a static-delegator pattern to find the most-concrete mill, 
 and `Mill#init()` sets these.)

For more complex languages as SimpleJava, the symbol table creation is split into two
parts. The first part is done after the execution of the ScopesGenitor to create a basic
symbol table for a model. But as explained above, this leaves the symbol classes extending
the `TypeSymbol`, `FunctionSymbol` and `VariableSymbol` with some empty attributes. 
For these attribtues, values are then assigned in the second phase of the symbol table creation.
The second phase consists of one or more visitor or handler classes that traverse the AST
and fill the attributes correctly. 
The AST classes that have a link to the symbol table 
(such as `ASTJavaMethod` or `ASTJavaVarDecl` in the grammar SimpleJava)
must then  be visited and their corresponding symbol fetched. 
Then, the attributes of the symbols can be filled however the language developer wants.


#### Exercise 4
The skeleton of a visitor for the second phase of the symbol table creation is displayed
in the class `SimpleJavaSTCompleteTypes`. Implement the `visit` methods and use the
`TypeCalculator` class to fill the empty attributes of the symbols. 
After filling the attributes of the symbols, use the methods `replaceSurrogate` or `replaceSurrogates` 
 to remove symbol surrogates if present. 
The test for correctness will be done after Exercise 5.

It is useful to combine both parts of the symbol table creation in a common class. 
This class needs to have two attributes: 
The ScopesGenitorDelegator of the language for the first phase and a list of traversers for the second phase.
In its constructor, it must add the visitors and handlers of phase two to a traverser and add it to the traverser list attribute.
To keep things consistent, the class needs to offer a `createFromAST` method
with the same parameter and return type like the methods in the ScopesGenitorDelegator
and the ScopesGenitor. 
In this method, the `createFromAST` method of the ScopesGenitorDelegator (and therefore phase one of the symbol table creation) must be called first.
Then, all traversers in the traverser list are executed on the AST, i.e. the parameter.
After that, the ArtifactScope that was returned by the `createFromAST` method of the
ScopesGenitorDelegator can be returned.

#### Exercise 5
The skeleton of a common class for executing both parts of the symbol table creation is
displayed in the class `SimpleJavaPhasedSTC`. 
Implement the constructor and the `createFromAST` method correctly. 
Execute the test `testTypesSet` in the class `SymTabTest` to test your implementation from both Exercise 4 and 5.




### Symbol Table Import and Export

This is called *serialization* of a symbol table. 
It is useful to store the symbol table of a model into a file for later use because loading
the symbol table of a model from this file is faster than parsing the model and creating
the symbol table by far. Loading a symbol table from a file is called *deserialization* of a
symbol table. 
Another use case of storing and loading symbols is loading symbol tables from models of other DSLs.
This means that symbols from the models of other languages can be deserialized 
 and therefore made accessible in the symbol table. 
For this to be possible, the symbol table of each language had to be serialized into the same, language independent format.
The serialized symbol table did not necessarily need to be human-readable, 
 but it had to be compact so that storing it in files is possible. 
There are many languages like JSON, YAML, XML or other markup languages who could have been chosen for this. 
In MontiCore, symbol tables are serialized into a JSON format.

The following shows an excerpt from the serialized symbol table of the `Bar` class:
```json
{
 "generated-using": "www.MontiCore.de technology",
 "name": "Bar",
 "package": "de.monticore.gettingstarted.simplejava.valid",
 "symbols": [
  {
   "kind": "de.monticore.gettingstarted.simplejava._symboltable.JavaArtifactSymbol",
   "name": "Bar",
   "spannedScope": {
    "symbols": [
     {
      "kind": "de.monticore.gettingstarted.simplejava._symboltable.JavaMethodSymbol",
      "name": "getMax",
      "type": {
       "kind": "de.monticore.types.check.SymTypePrimitive",
       "primitiveName": "int"
      },
      "spannedScope": {
       "symbols": [
        {
         "kind": "de.monticore.gettingstarted.simplejava._symboltable.JavaVarDeclSymbol",
         "name": "d",
         "type": {
          "kind": "de.monticore.types.check.SymTypePrimitive",
          "primitiveName": "double"
         }
        },
        {
         "kind": "de.monticore.gettingstarted.simplejava._symboltable.JavaVarDeclSymbol",
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
At first, it defines the name of the model and the name of the package it lies in. After that, the
serialized symbols are stored in a list. For each symbol, its kind and name is saved. If a
symbol spans a scope, the symbols in this scope are displayed as well. Additional attributes
of the symbol like type in `JavaVarDeclSymbol` are serialized as well.

For the serialization and deserialization of a symbol table, MontiCore generates the class
`ASymbols2Json` for a grammar `A`. 
Its method `store` needs an artifact scope and a `String`.
The artifact scope contains the symbol table of the model that will be serialized while the
string contains the location of the file into which the symbol table is serialized. 
Thus, it serializes the symbol table of exactly one model into exactly one file. 
To load a symbol table from a file, the `ASymbols2Json` class offers the method `load`.
It has one parameter of the type `String` that should contain the location of the file in which the to-be-loaded
symbol table is stored.
The method returns the artifact scope which is the top of the symbol table of the model and
 thus contains its whole symbol table.
This artifact scope can then be added as a subscope to the global scope of the language so that other models
can resolve symbols of this loaded symbol table as well.

MontiCore is able to generate serilization aand deser infrastructure for most symbols.
But symbols with custom information require hand-written effort.

The `Type` symbol interface is extended by a `superTypes` attribute, containing a list of `SymTypeExpressions`:
```mc4
  symbolrule Type =
    superTypes: de.monticore.types.check.SymTypeExpression* ;
```

Thus, we have to extend the SymbolDeSer for java artifact symbols using the TOP-mechanism
and handle the superTypes (de)serialization, like the following:

```java
public class JavaArtifactSymbolDeSer extends JavaArtifactSymbolDeSerTOP {
  @Override
  protected void serializeSuperTypes(List<SymTypeExpression> superTypes,
                                     SimpleJavaSymbols2Json s2j) {
    SymTypeExpressionDeSer.serializeMember(s2j.getJsonPrinter(), "superTypes",
            superTypes);
  }

  @Override
  protected List<SymTypeExpression> deserializeSuperTypes(JsonObject symbolJson) {
    return SymTypeExpressionDeSer.deserializeListMember("superTypes", symbolJson);
  }
}
```

Similar, the type of a `JavaMethod` symbol is serialized and deserialized:
```java
public class JavaMethodSymbolDeSer extends JavaMethodSymbolDeSerTOP {
  @Override
  protected void serializeType(SymTypeExpression type, 
                               SimpleJavaSymbols2Json s2j) {
    SymTypeExpressionDeSer.serializeMember(s2j.getJsonPrinter(), "type",
            type);
  }

  @Override
  protected SymTypeExpression deserializeType(JsonObject symbolJson) {
    return SymTypeExpressionDeSer.deserializeMember("type", symbolJson);
  }
}
```

#### Exercise 6
Add the handwritten symbol serialization and deserialization to the symbol DeSers of java artifact symbols, 
 java method symbols, and java var declaration symbols.
Next, go to the class `DeSerTest`. 
Implement the method `testSerialization` by storing the symbol table of the model Bar into a file. 
For this, you have to parse the model, create its symbol table and serialize it. 
Store the serialized symbol table in the file `target/symboltable/Bar.javasym`. 
After that, implement the method `testDeserialization`. 
Use the `SimpleJavaSymbols2Json` to deserialize the symbol table of the model.
Check that is stored under `src/test/resources/de/monticore/gettingstarted/simplejava/symboltable`. 
Add it as a subscope to the global scope and make sure that resolving the TypeSymbol `Check` is possible.
Next, ensure that the amount of super types the TypeSymbol `Check` has equals `0`. (Hint: `.getSuperTypesList().size()`)



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

To make the language usable and free from errors, CoCos must be created that further restrict it. 
Some of these CoCos should be the standard CoCos such as

* the name of classes should be capitalized
* the name of attributes and variables should be uncapitalized
* the name of methods should be uncapitalized


This type of Context Conditions was handled in Chapter 2 already and thus will not be handled here. 
We will focus on other types of Context Conditions that contain using the TypeCheck. 
There are two Context Conditions that should be implemented to guarantee type safety for a language. 
They should log errors if the type rules are broken. These CoCos are

* If a variable is initialized immediately after its declaration, the type of the variable
  should be compatible to the expression initializing it
* An expression should be valid. Calling a method with wrong parameters or assigning
  the value 3 to an already existing boolean variable should result in an error

For both of these cases, one Context Condition is enough. In the first case, the CoCo
should revolve around the type `ASTJavaVarDecl`. 
If a variable declaration has an expression that immediately initializes it, 
 the `SymTypeExpression` for both the declared type and the initializing expression should be calculated and compared. 
If the resulting `SymTypeExpression`s are not compatible (e.g. `int` and `boolean`),
 the Context Condition should log an error.
The `TypeCalculator` has a method to check the compatibility of two `SymTypeExpression`s.
If a variable declaration does not have an expression, the CoCo should ignore this variable declaration.
The second case is a little different and should use the class `ASTExpression` of the grammar `ExpressionsBasis`. 
This class is an interface that all expressions implement.
Therefore, the validity of all expressions can be checked with a CoCo specified to this interface. 
The TypeCheck should be used to derive a `SymTypeExpression` from the `ASTExpression`. 
If the resulting `SymTypeExpression` is obscure,
 this means that the TypeCheck could not derive a type for the `ASTExpression`
which means that the expression is not valid as the types of its subexpressions do not match. 
In this case, an error should be logged.

#### Exercise 7
The skeletons for the Context Conditions `VariableDeclarationIsCorrect` and `ExpressionIsValid` 
 are already given in the GettingStarted project. 
Implement these Context Conditions like described above and check their correctness by executing all tests
 in the class `CoCoTest`.

(*Hint*: Be aware, that the expression of variable declarations is optional.)
<!-- (c) https://github.com/MontiCore/monticore -->

Next, continue with [Chapter 3](../03.composition/README.md)
