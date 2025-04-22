/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._symboltable.ISimpleJavaArtifactScope;
import de.monticore.gettingstarted.simplejava._symboltable.SimpleJavaScopesGenitorDelegator;
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
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testPackageSet() throws IOException {
    ASTJavaCompilationUnit bar = parse("src/test/resources/de/monticore/gettingstarted/simplejava/valid/Bar.sjava");
    SimpleJavaScopesGenitorDelegator st = SimpleJavaMill.scopesGenitorDelegator();
    ISimpleJavaArtifactScope as = st.createFromAST(bar);
    assertEquals(as.getPackageName(), bar.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testTypesSet() throws IOException {
    ASTJavaCompilationUnit bar = parse("src/test/resources/de/monticore/gettingstarted/simplejava/valid/Bar.sjava");
    ISimpleJavaArtifactScope as = createSymbolTable(bar);
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
