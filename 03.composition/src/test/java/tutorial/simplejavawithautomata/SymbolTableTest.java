/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejavawithautomata;

import org.junit.jupiter.api.Assertions;
import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import tutorial.simplejavawithautomata._symboltable.SimpleJavaWithAutomataSymbols2Json;
import tutorial.simplejavawithautomata.types3.SimpleJavaWithAutomataTypeCheck3;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

class SymbolTableTest extends AbstractTest {

  @BeforeEach
  public void setup(){
    SimpleJavaWithAutomataMill.globalScope().clear();
    SimpleJavaWithAutomataMill.init();
    SimpleJavaWithAutomataTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @Ignore //TODO Exercise 1 
  void testPackage() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    Assertions.assertFalse(symbolTable.getPackageName().isEmpty());
    Assertions.assertEquals(symbolTable.getPackageName(),
                            ast.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  @Ignore //TODO Exercise 1 
  void testSymbolCompletion() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    Optional<FunctionSymbol> function = symbolTable.resolveFunctionDown("Bar.getMax");
    Assertions.assertTrue(function.isPresent());
    Assertions.assertNotNull(function.get().getType());
    Assertions.assertEquals("int", function.get().getType().print());
  }

  @Test
  void testSymbolTableImport() {
    SimpleJavaWithAutomataSymbols2Json symbols2Json = new SimpleJavaWithAutomataSymbols2Json();
    ISimpleJavaWithAutomataArtifactScope as = symbols2Json.load("src/test/resources/tutorial/simplejava/symboltable/Check.javasym");
    Optional<FunctionSymbol> function = as.resolveFunctionDown("Check.getBar");
    Assertions.assertTrue(function.isPresent(), "Failed to find Check.getBar");
  }


}
