/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import de.monticore.expressions.assignmentexpressions._visitor.AssignmentExpressionsTraverser;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsTraverser;
import de.monticore.expressions.expressionsbasis._symboltable.IExpressionsBasisScope;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisTraverser;
import de.monticore.literals.mccommonliterals._visitor.MCCommonLiteralsTraverser;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.types.check.SymTypeOfGenerics;
import de.monticore.types.mcbasictypes._visitor.MCBasicTypesTraverser;
import de.monticore.types.mccollectiontypes._visitor.MCCollectionTypesTraverser;
import de.monticore.types.mcsimplegenerictypes._visitor.MCSimpleGenericTypesTraverser;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tutorial.simplejava._parser.SimpleJavaParser;
import tutorial.simplejava._visitor.SimpleJavaTraverser;
import tutorial.simplejava.types3.SimpleJavaTypeCheck3;

import java.io.IOException;
import java.util.Optional;

public class Class2MCTest extends AbstractTest {

  @Before
  public void setUpClass2MC() throws Exception {
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
    // TODO: Add Class2MCResolver.getJRTPath() to the symbol path
    // TODO: Add the Class2MCResolver as an adapted type symbol resolver
  }

  @Test
  @Ignore
  public void test() throws Exception {
    // Test, that we are successfully able to resolve java symbols, such as String, Optional, ...
    Assert.assertTrue(SimpleJavaMill
            .globalScope()
            .resolveType("java.lang.String").isPresent());
    Assert.assertTrue(SimpleJavaMill
            .globalScope()
            .resolveType("java.util.Optional").isPresent());

  }


}
