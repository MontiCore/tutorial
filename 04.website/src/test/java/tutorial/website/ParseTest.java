/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import org.junit.Test;

import java.io.IOException;

public class ParseTest extends AbstractTest {

  @Test
  public void testSERWTH() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SERWTH.web");
  }

  @Test
  public void testDream() throws IOException {
    parse("src/test/resources/tutorial/website/valid/Dream.web");
  }

  @Test
  public void testSinglePage() throws IOException {
    parse("src/test/resources/tutorial/website/valid/SinglePage.web");
  }

}
