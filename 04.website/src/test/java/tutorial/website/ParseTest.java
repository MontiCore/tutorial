/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;

public class ParseTest extends AbstractTest {

  @Test
  @Ignore //TODO: Exercise 1 
  public void testSERWTH() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SERWTH.web");
  }

  @Test
  @Ignore //TODO: Exercise 1 
  public void testDream() throws IOException {
    parse("src/test/resources/tutorial/website/valid/Dream.web");
  }

  @Test
  @Ignore //TODO: Exercise 1 
  public void testSinglePage() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SinglePage.web");
  }

}
