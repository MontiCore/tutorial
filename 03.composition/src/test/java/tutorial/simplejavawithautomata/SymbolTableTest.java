/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejavawithautomata;

import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import tutorial.simplejavawithautomata._symboltable.SimpleJavaWithAutomataSymbols2Json;
import tutorial.simplejavawithautomata.types3.SimpleJavaWithAutomataTypeCheck3;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class SymbolTableTest extends AbstractTest {

  @Before
  public void setup(){
    SimpleJavaWithAutomataMill.globalScope().clear();
    SimpleJavaWithAutomataMill.init();
    SimpleJavaWithAutomataTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @Ignore //TODO Exercise 1 
  public void testPackage() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    assertFalse(symbolTable.getPackageName().isEmpty());
    assertEquals(symbolTable.getPackageName(), ast.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  @Ignore //TODO Exercise 1 
  public void testSymbolCompletion() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    Optional<FunctionSymbol> function = symbolTable.resolveFunctionDown("Bar.getMax");
    assertTrue(function.isPresent());
    assertNotNull(function.get().getType());
    assertEquals("int", function.get().getType().print());
  }

  @Test
  public void testSymbolTableImport() {
    SimpleJavaWithAutomataSymbols2Json symbols2Json = new SimpleJavaWithAutomataSymbols2Json();
    ISimpleJavaWithAutomataArtifactScope as = symbols2Json.load("src/test/resources/tutorial/simplejava/symboltable/Check.javasym");
    Optional<FunctionSymbol> function = as.resolveFunctionDown("Check.getBar");
    assertTrue(function.isPresent());
  }


}
