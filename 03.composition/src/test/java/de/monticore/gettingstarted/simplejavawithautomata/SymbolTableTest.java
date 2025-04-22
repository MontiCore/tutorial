/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import de.monticore.gettingstarted.simplejavawithautomata._symboltable.SimpleJavaWithAutomataSymbols2Json;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class SymbolTableTest extends AbstractTest {

  @Before
  public void setup(){
    SimpleJavaWithAutomataMill.globalScope().clear();
    SimpleJavaWithAutomataMill.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testPackage() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/de/monticore/gettingstarted/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    assertFalse(symbolTable.getPackageName().isEmpty());
    assertEquals(symbolTable.getPackageName(), ast.getMCPackageDeclaration().getMCQualifiedName().getQName());
  }

  @Test
  //<#if !solution>@Ignore//</#if>
  public void testSymbolCompletion() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/de/monticore/gettingstarted/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope symbolTable = createSymbolTable(ast);
    Optional<FunctionSymbol> function = symbolTable.resolveFunctionDown("Bar.getMax");
    assertTrue(function.isPresent());
    assertNotNull(function.get().getType());
    assertEquals("int", function.get().getType().print());
  }

  @Test
  public void testSymbolTableImport() {
    SimpleJavaWithAutomataSymbols2Json symbols2Json = new SimpleJavaWithAutomataSymbols2Json();
    ISimpleJavaWithAutomataArtifactScope as = symbols2Json.load("src/test/resources/de/monticore/gettingstarted/simplejava/symboltable/Check.javasym");
    Optional<FunctionSymbol> function = as.resolveFunctionDown("Check.getBar");
    assertTrue(function.isPresent());
  }


}
