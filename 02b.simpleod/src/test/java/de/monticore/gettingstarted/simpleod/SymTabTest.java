/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod;

import de.monticore.gettingstarted.simpleod._ast.ASTODCompilationUnit;
import de.monticore.gettingstarted.simpleod._symboltable.ISimpleODArtifactScope;
import de.monticore.gettingstarted.simpleod._symboltable.SimpleODScopesGenitorDelegator;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import de.monticore.symbols.basicsymbols._symboltable.VariableSymbol;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class SymTabTest extends AbstractTest {

  @Before
  public void setup(){
    SimpleODMill.globalScope().clear();
    SimpleODMill.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @Ignore
  public void testPackageSet() throws IOException {
    ASTODCompilationUnit bar = parse("src/test/resources/de/monticore/gettingstarted/simpleod/valid/Bar.sod");
    SimpleODScopesGenitorDelegator st = SimpleODMill.scopesGenitorDelegator();
    ISimpleODArtifactScope as = st.createFromAST(bar);
    assertEquals(as.getPackageName(), bar.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  @Ignore
  public void testTypesSet() throws IOException {
    ASTODCompilationUnit bar = parse("src/test/resources/de/monticore/gettingstarted/simpleod/valid/Bar.sod");
    ISimpleODArtifactScope as = createSymbolTable(bar);
    // TODO: function
    Optional<FunctionSymbol> getMax = as.resolveFunctionDown("Bar.getMax");
    assertTrue(getMax.isPresent());
    assertNotNull(getMax.get().getType());
    assertEquals("int", getMax.get().getType().print());

    Optional<VariableSymbol> d = as.resolveVariableDown("Bar.getMax.d");
    assertTrue(d.isPresent());
    assertNotNull(d.get().getType());
    assertEquals("double", d.get().getType().print());
  }


}
