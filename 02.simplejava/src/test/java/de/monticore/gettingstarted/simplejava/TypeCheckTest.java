/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.expressions.assignmentexpressions._ast.ASTAssignmentExpression;
import de.monticore.expressions.assignmentexpressions._ast.ASTConstantsAssignmentExpressions;
import de.monticore.expressions.assignmentexpressions._visitor.AssignmentExpressionsTraverser;
import de.monticore.expressions.commonexpressions._ast.ASTPlusExpression;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsTraverser;
import de.monticore.expressions.expressionsbasis._ast.ASTLiteralExpression;
import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.expressions.expressionsbasis._symboltable.IExpressionsBasisScope;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisTraverser;
import de.monticore.gettingstarted.simplejava._parser.SimpleJavaParser;
import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.gettingstarted.simplejava.types3.SimpleJavaTypeCheck3;
import de.monticore.literals.mccommonliterals._visitor.MCCommonLiteralsTraverser;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.monticore.types.check.TypeCheckResult;
import de.monticore.types.mcbasictypes._ast.ASTMCPrimitiveType;
import de.monticore.types.mcbasictypes._visitor.MCBasicTypesTraverser;
import de.monticore.types.mccollectiontypes._ast.ASTMCListType;
import de.monticore.types.mccollectiontypes._visitor.MCCollectionTypesTraverser;
import de.monticore.types.mcsimplegenerictypes._visitor.MCSimpleGenericTypesTraverser;
import de.monticore.types3.SymTypeRelations;
import de.monticore.types3.TypeCheck3;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TypeCheckTest extends AbstractTest {

  @Before
  public void setup(){
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
    SimpleJavaMill.globalScope().add(SimpleJavaMill.typeSymbolBuilder().setName("List").build());
    SimpleJavaMill.globalScope().add(SimpleJavaMill.fieldSymbolBuilder().setName("variable").setType(SymTypeExpressionFactory.createPrimitive("float")).build());
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testSynthesizer() throws IOException {
    SimpleJavaParser p = SimpleJavaMill.parser();

    //BasicTypes
    Optional<ASTMCPrimitiveType> prim = p.parse_StringMCPrimitiveType("int");
    assertTrue(prim.isPresent());
    SimpleJavaTraverser traverser = SimpleJavaMill.traverser();
    addToTraverser(traverser, SimpleJavaMill.globalScope());
    prim.get().accept(traverser);
    SymTypeExpression res = TypeCheck3.symTypeFromAST(prim.get());
    assertEquals("int", res.print());

    //CollectionTypes
    Optional<ASTMCListType> list = p.parse_StringMCListType("List<int>");
    assertTrue(list.isPresent());
    traverser = SimpleJavaMill.traverser();
    addToTraverser(traverser, SimpleJavaMill.globalScope());
    list.get().accept(traverser);
    res = TypeCheck3.symTypeFromAST(list.get());
    assertEquals("List<int>", res.print());
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testDeriver() throws IOException {
    SimpleJavaParser p = SimpleJavaMill.parser();

    //ExpressionsBasis
    Optional<ASTLiteralExpression> lit = p.parse_StringLiteralExpression("3");
    assertTrue(lit.isPresent());
    SimpleJavaTraverser traverser = SimpleJavaMill.traverser();
    addToTraverser(traverser, SimpleJavaMill.globalScope());
    lit.get().accept(traverser);
    SymTypeExpression res = TypeCheck3.typeOf(lit.get());
    assertEquals("int", res.print());

    //CommonExpressions
    Optional<ASTLiteralExpression> lit2 = p.parse_StringLiteralExpression("3.4");
    assertTrue(lit2.isPresent());
    ASTPlusExpression plus = SimpleJavaMill.plusExpressionBuilder().setLeft(lit.get()).setRight(lit2.get()).setOperator("+").build();
    traverser = SimpleJavaMill.traverser();
    addToTraverser(traverser, SimpleJavaMill.globalScope());
    plus.accept(traverser);
    res = TypeCheck3.typeOf(plus);
    assertEquals("double", res.print());

    //AssignmentExpressions
    Optional<ASTNameExpression> name = p.parse_StringNameExpression("variable");
    assertTrue(name.isPresent());
    ASTAssignmentExpression ass = SimpleJavaMill.assignmentExpressionBuilder().setLeft(name.get()).setRight(lit.get()).setOperator(ASTConstantsAssignmentExpressions.MINUSEQUALS).build();
    traverser = SimpleJavaMill.traverser();
    addToTraverser(traverser, SimpleJavaMill.globalScope());
    ass.accept(traverser);
    res = TypeCheck3.typeOf(ass);
    assertEquals("float", res.print());
  }

  private void addToTraverser(ExpressionsBasisTraverser traverser, IExpressionsBasisScope enclosingScope) {
    FlatExpressionScopeSetter flatExpressionScopeSetter = new FlatExpressionScopeSetter(enclosingScope);
    traverser.add4ExpressionsBasis(flatExpressionScopeSetter);
    if (traverser instanceof AssignmentExpressionsTraverser) {
      ((AssignmentExpressionsTraverser) traverser).add4AssignmentExpressions(flatExpressionScopeSetter);
    }
    if (traverser instanceof CommonExpressionsTraverser) {
      ((CommonExpressionsTraverser) traverser).add4CommonExpressions(flatExpressionScopeSetter);
    }
    if (traverser instanceof MCBasicTypesTraverser) {
      ((MCBasicTypesTraverser) traverser).add4MCBasicTypes(flatExpressionScopeSetter);
    }
    if(traverser instanceof MCCollectionTypesTraverser) {
      ((MCCollectionTypesTraverser) traverser).add4MCCollectionTypes(flatExpressionScopeSetter);
    }
    if(traverser instanceof MCSimpleGenericTypesTraverser) {
      ((MCSimpleGenericTypesTraverser) traverser).add4MCSimpleGenericTypes(flatExpressionScopeSetter);
    }
    if (traverser instanceof MCCommonLiteralsTraverser) {
      ((MCCommonLiteralsTraverser) traverser).add4MCCommonLiterals(flatExpressionScopeSetter);
    }
  }



}
