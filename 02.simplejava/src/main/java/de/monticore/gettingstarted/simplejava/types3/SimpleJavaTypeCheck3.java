package de.monticore.gettingstarted.simplejava.types3;

import de.monticore.expressions.assignmentexpressions.types3.AssignmentExpressionsCTTIVisitor;
import de.monticore.expressions.commonexpressions.types3.CommonExpressionsCTTIVisitor;
import de.monticore.expressions.commonexpressions.types3.util.CommonExpressionsLValueRelations;
import de.monticore.expressions.expressionsbasis.types3.ExpressionBasisCTTIVisitor;
import de.monticore.gettingstarted.simplejava.SimpleJavaMill;
import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.literals.mccommonliterals.types3.MCCommonLiteralsTypeVisitor;
import de.monticore.types.mcbasictypes.types3.MCBasicTypesTypeVisitor;
import de.monticore.types.mccollectiontypes.types3.MCCollectionTypesTypeVisitor;
import de.monticore.types.mcsimplegenerictypes.types3.MCSimpleGenericTypesTypeVisitor;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.types3.util.*;
import de.monticore.visitor.ITraverser;
import de.se_rwth.commons.logging.Log;

/**
 * TypeCheck3 implementation for the SimpleJava language.
 * After calling {@link #init()},
 * this implementation will be available through the TypeCheck3 interface.
 */
public class SimpleJavaTypeCheck3 extends MapBasedTypeCheck3 {

  public static void init() {
    Log.trace("init SimpleJavaTypeCheck3", "TypeCheck setup");

    // initialize static delegates
    // use OO rules to access types, fields, etc.
    OOWithinScopeBasicSymbolsResolver.init();
    OOWithinTypeBasicSymbolsResolver.init();
    TypeContextCalculator.init();
    TypeVisitorOperatorCalculator.init();
    TypeVisitorLifting.init();
    FunctionRelations.init();
    CommonExpressionsLValueRelations.init();

    // traverser of your language
    // no inheritance traverser is used, as it is recommended
    // to create a new traverser / TC3 for each language.
    SimpleJavaTraverser traverser = SimpleJavaMill.traverser();
    // map to store the results
    Type4Ast type4Ast = new Type4Ast();
    InferenceContext4Ast ctx4Ast = new InferenceContext4Ast();

    // Literals

    // one of many type visitors
    // check their documentation, whether further configuration is required

    {
      MCCommonLiteralsTypeVisitor visMCCommonLiterals = new MCCommonLiteralsTypeVisitor();
      visMCCommonLiterals.setType4Ast(type4Ast);
      traverser.add4MCCommonLiterals(visMCCommonLiterals);
    }

    // Expressions

    {
      AssignmentExpressionsCTTIVisitor visAssignmentExpressions = new AssignmentExpressionsCTTIVisitor();
      visAssignmentExpressions.setType4Ast(type4Ast);
      visAssignmentExpressions.setContext4Ast(ctx4Ast);
      traverser.add4AssignmentExpressions(visAssignmentExpressions);
      traverser.setAssignmentExpressionsHandler(visAssignmentExpressions);
    }

    // TODO: Add visitors of the concrete super expressions here
    // Hint: CommonExpressionsCTTIVisitor and  ExpressionBasis...

    //<#if solution>
    {
      CommonExpressionsCTTIVisitor visCommonExpressions = new CommonExpressionsCTTIVisitor();
      visCommonExpressions.setType4Ast(type4Ast);
      visCommonExpressions.setContext4Ast(ctx4Ast);
      traverser.add4CommonExpressions(visCommonExpressions);
      traverser.setCommonExpressionsHandler(visCommonExpressions);
    }

    {
      ExpressionBasisCTTIVisitor visExpressionBasis = new ExpressionBasisCTTIVisitor();
      visExpressionBasis.setType4Ast(type4Ast);
      visExpressionBasis.setContext4Ast(ctx4Ast);
      traverser.add4ExpressionsBasis(visExpressionBasis);
      traverser.setExpressionsBasisHandler(visExpressionBasis);
    }
    //</#if>

    // MCTypes

    {
      MCBasicTypesTypeVisitor visMCBasicTypes = new MCBasicTypesTypeVisitor();
      visMCBasicTypes.setType4Ast(type4Ast);
      traverser.add4MCBasicTypes(visMCBasicTypes);
    }

    {
      MCCollectionTypesTypeVisitor visMCCollectionTypes = new MCCollectionTypesTypeVisitor();
      visMCCollectionTypes.setType4Ast(type4Ast);
      traverser.add4MCCollectionTypes(visMCCollectionTypes);
    }

    {
      MCSimpleGenericTypesTypeVisitor visMCSimpleGenericTypes = new MCSimpleGenericTypesTypeVisitor();
      visMCSimpleGenericTypes.setType4Ast(type4Ast);
      traverser.add4MCSimpleGenericTypes(visMCSimpleGenericTypes);
    }

    // create the TypeCheck3 delegate
    SimpleJavaTypeCheck3 oclTC3 = new SimpleJavaTypeCheck3(traverser, type4Ast, ctx4Ast);
    oclTC3.setThisAsDelegate();
  }

  public static void reset() {
    Log.trace("reset SimpleJavaTypeCheck3", "TypeCheck setup");
    SimpleJavaTypeCheck3.resetDelegate();
    OOWithinScopeBasicSymbolsResolver.reset();
    OOWithinTypeBasicSymbolsResolver.reset();
    TypeContextCalculator.reset();
    TypeVisitorOperatorCalculator.reset();
    TypeVisitorLifting.reset();
    FunctionRelations.reset();
  }

  protected SimpleJavaTypeCheck3(
          ITraverser typeTraverser, Type4Ast type4Ast, InferenceContext4Ast ctx4Ast) {
    super(typeTraverser, type4Ast, ctx4Ast);
  }
}
