/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._symboltable.*;
import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.se_rwth.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeSerTest extends AbstractTest {

  @Before
  public void setup() {
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleJavaMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleJavaMill.globalScope())
      .build();
    string.setSpannedScope(SimpleJavaMill.scope());
    SimpleJavaMill.globalScope().add(string);
    SimpleJavaMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
  }

  @Test
  public void testSerialization() throws IOException {
    //TODO implement me!
    //<#if solution>
    SimpleJavaPhasedSTC stc = new SimpleJavaPhasedSTC();

    ASTJavaCompilationUnit bar = parse("src/test/resources/de/monticore/gettingstarted/simplejava/valid/Bar.sjava");
    ISimpleJavaArtifactScope as = stc.createFromAST(bar);

    SimpleJavaSymbols2Json symbols2Json = new SimpleJavaSymbols2Json();
    symbols2Json.store(as, "target/symboltable/Bar.javasym");
    //</#if>
  }

  @Test
  public void testDeserialization(){
    //TODO implement me!
    //<#if solution>
    SimpleJavaSymbols2Json symbols2Json = new SimpleJavaSymbols2Json();
    ISimpleJavaArtifactScope as = symbols2Json.load("src/test/resources/de/monticore/gettingstarted/simplejava/symboltable/Check.javasym");
    as.setEnclosingScope(SimpleJavaMill.globalScope());
    assertTrue(as.resolveType("Check").isPresent());
    assertTrue(as.resolveFunction("de.monticore.gettingstarted.simplejava.valid.Check.print").isPresent());
    assertEquals(0, as.resolveType("Check").get().getSuperTypesList().size());
    //</#if>
  }

}
