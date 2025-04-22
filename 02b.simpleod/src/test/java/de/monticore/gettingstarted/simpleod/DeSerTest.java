/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod;

import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class DeSerTest extends AbstractTest {

  @Before
  public void setup() {
    SimpleODMill.globalScope().clear();
    SimpleODMill.init();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleODMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleODMill.globalScope())
      .build();
    string.setSpannedScope(SimpleODMill.scope());
    SimpleODMill.globalScope().add(string);
    SimpleODMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
  }

  @Test
  public void testSerialization() throws IOException {
    //TODO implement me!
    // Test if the ST of an OD, such as Bar, can be written to a sym file
  }

  @Test
  public void testDeserialization(){
    //TODO implement me!
    // Test if a given odsym file can be loaded
    // You have to create the odsym file yourself
  }

}
