/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import de.monticore.symbols.basicsymbols._symboltable.VariableSymbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._symboltable.ISimpleJavaArtifactScope;
import tutorial.simplejava._symboltable.SimpleJavaScopesGenitorDelegator;
import tutorial.simplejava.types3.SimpleJavaTypeCheck3;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SymTabTest extends AbstractTest {

  @BeforeEach
  public void setup(){
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testPackageSet() throws IOException {
    ASTJavaCompilationUnit bar = parse("src/test/resources/tutorial/simplejava/valid/Bar.sjava");
    SimpleJavaScopesGenitorDelegator st = SimpleJavaMill.scopesGenitorDelegator();
    ISimpleJavaArtifactScope as = st.createFromAST(bar);
    assertEquals(as.getPackageName(), bar.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testTypesSet() throws IOException {
    ASTJavaCompilationUnit bar = parse("src/test/resources/tutorial/simplejava/valid/Bar.sjava");
    ISimpleJavaArtifactScope as = createSymbolTable(bar);
    Optional<FunctionSymbol> getMax = as.resolveFunctionDown("Bar.getMax");
    assertTrue(getMax.isPresent());
    assertNotNull(getMax.get().getType());
    assertEquals("int", getMax.get().getType().print());

    Optional<VariableSymbol> d = as.resolveVariableDown("Bar.getMax.d");
    assertFalse(d.isPresent());
    // the variable d is now within a non-exporting scope
  }


}
