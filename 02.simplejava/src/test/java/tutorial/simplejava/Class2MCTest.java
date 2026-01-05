/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorial.simplejava.types3.SimpleJavaTypeCheck3;

class Class2MCTest extends AbstractTest {

  @BeforeEach
  public void setUpClass2MC() throws Exception {
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
    // TODO: Add Class2MCResolver.getJRTPath() to the symbol path
    // TODO: Add the Class2MCResolver as an adapted type symbol resolver
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void test() throws Exception {
    // Test, that we are successfully able to resolve java symbols, such as String, Optional, ...
    Assertions.assertTrue(SimpleJavaMill
            .globalScope()
            .resolveType("java.lang.String").isPresent());
    Assertions.assertTrue(SimpleJavaMill
            .globalScope()
            .resolveType("java.util.Optional").isPresent());

  }


}
