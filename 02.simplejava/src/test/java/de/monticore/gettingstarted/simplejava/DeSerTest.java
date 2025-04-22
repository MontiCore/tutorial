/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._symboltable.*;
import de.monticore.gettingstarted.simplejava.types3.SimpleJavaTypeCheck3;
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
    SimpleJavaTypeCheck3.init();
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
  }

  @Test
  public void testDeserialization(){
    //TODO implement me!
  }

}
