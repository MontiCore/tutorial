/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod;

import de.monticore.expressions.assignmentexpressions._ast.ASTAssignmentExpression;
import de.monticore.expressions.assignmentexpressions._ast.ASTConstantsAssignmentExpressions;
import de.monticore.expressions.assignmentexpressions._visitor.AssignmentExpressionsTraverser;
import de.monticore.expressions.commonexpressions._ast.ASTPlusExpression;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsTraverser;
import de.monticore.expressions.expressionsbasis._ast.ASTLiteralExpression;
import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.expressions.expressionsbasis._symboltable.IExpressionsBasisScope;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisTraverser;
import de.monticore.gettingstarted.simpleod._parser.SimpleODParser;
import de.monticore.gettingstarted.simpleod._visitor.SimpleODTraverser;
import de.monticore.gettingstarted.simpleod.types.FullDeriveFromSimpleOD;
import de.monticore.gettingstarted.simpleod.types.FullSynthesizeFromSimpleOD;
import de.monticore.literals.mccommonliterals._visitor.MCCommonLiteralsTraverser;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.monticore.types.check.TypeCheckResult;
import de.monticore.types.mcbasictypes._ast.ASTMCPrimitiveType;
import de.monticore.types.mcbasictypes._visitor.MCBasicTypesTraverser;
import de.monticore.types.mccollectiontypes._ast.ASTMCListType;
import de.monticore.types.mccollectiontypes._visitor.MCCollectionTypesTraverser;
import de.monticore.types.mcsimplegenerictypes._visitor.MCSimpleGenericTypesTraverser;
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
    SimpleODMill.globalScope().clear();
    SimpleODMill.init();
    BasicSymbolsMill.initializePrimitives();
    SimpleODMill.globalScope().add(SimpleODMill.typeSymbolBuilder().setName("List").build());
    SimpleODMill.globalScope().add(SimpleODMill.fieldSymbolBuilder().setName("variable").setType(SymTypeExpressionFactory.createPrimitive("float")).build());
  }

  @Test
  @Ignore
  public void testSynthesizer() throws IOException {
    SimpleODParser p = SimpleODMill.parser();
    FullSynthesizeFromSimpleOD syn = new FullSynthesizeFromSimpleOD();

    //BasicTypes
    Optional<ASTMCPrimitiveType> prim = p.parse_StringMCPrimitiveType("int");
    assertTrue(prim.isPresent());
    SimpleODTraverser traverser = SimpleODMill.traverser();
    addToTraverser(traverser, SimpleODMill.globalScope());
    prim.get().accept(traverser);
    TypeCheckResult res = syn.synthesizeType(prim.get());
    assertTrue(res.isPresentResult());
    assertEquals("int", res.getResult().print());

    //CollectionTypes
    Optional<ASTMCListType> list = p.parse_StringMCListType("List<int>");
    assertTrue(list.isPresent());
    traverser = SimpleODMill.traverser();
    addToTraverser(traverser, SimpleODMill.globalScope());
    list.get().accept(traverser);
    res = syn.synthesizeType(list.get());
    assertTrue(res.isPresentResult());
    assertEquals("List<int>", res.getResult().print());
  }

  @Test
  @Ignore
  public void testDeriver() throws IOException {
    SimpleODParser p = SimpleODMill.parser();
    FullDeriveFromSimpleOD der = new FullDeriveFromSimpleOD();

    //ExpressionsBasis
    Optional<ASTLiteralExpression> lit = p.parse_StringLiteralExpression("3");
    assertTrue(lit.isPresent());
    SimpleODTraverser traverser = SimpleODMill.traverser();
    addToTraverser(traverser, SimpleODMill.globalScope());
    lit.get().accept(traverser);
    TypeCheckResult res = der.deriveType(lit.get());
    assertTrue(res.isPresentResult());
    assertEquals("int", res.getResult().print());

    //CommonExpressions
    Optional<ASTLiteralExpression> lit2 = p.parse_StringLiteralExpression("3.4");
    assertTrue(lit2.isPresent());
    ASTPlusExpression plus = SimpleODMill.plusExpressionBuilder().setLeft(lit.get()).setRight(lit2.get()).setOperator("+").build();
    traverser = SimpleODMill.traverser();
    addToTraverser(traverser, SimpleODMill.globalScope());
    plus.accept(traverser);
    res = der.deriveType(plus);
    assertTrue(res.isPresentResult());
    assertEquals("double", res.getResult().print());

    //AssignmentExpressions
    Optional<ASTNameExpression> name = p.parse_StringNameExpression("variable");
    assertTrue(name.isPresent());
    ASTAssignmentExpression ass = SimpleODMill.assignmentExpressionBuilder().setLeft(name.get()).setRight(lit.get()).setOperator(ASTConstantsAssignmentExpressions.MINUSEQUALS).build();
    traverser = SimpleODMill.traverser();
    addToTraverser(traverser, SimpleODMill.globalScope());
    ass.accept(traverser);
    res = der.deriveType(ass);
    assertTrue(res.isPresentResult());
    assertEquals("float", res.getResult().print());
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
