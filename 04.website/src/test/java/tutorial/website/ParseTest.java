/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ParseTest extends AbstractTest {

  @Test
  @org.junit.jupiter.api.Disabled //TODO: Exercise 1 
  void testSERWTH() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SERWTH.web");
  }

  @Test
  @org.junit.jupiter.api.Disabled //TODO: Exercise 1 
  void testDream() throws IOException {
    parse("src/test/resources/tutorial/website/valid/Dream.web");
  }

  @Test
  @org.junit.jupiter.api.Disabled //TODO: Exercise 1 
  void testSinglePage() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SinglePage.web");
  }

}
