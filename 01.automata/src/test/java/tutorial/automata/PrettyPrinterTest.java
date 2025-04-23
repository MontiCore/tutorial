/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import de.se_rwth.commons.logging.LogStub;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._parser.AutomataParser;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PrettyPrinterTest extends AbstractTest {

  @Test
  @Ignore
  public void testPrettyPrinter() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/PingPong.aut");
  }

  @Test
  @Ignore
  public void testPrettyPrinterHierarchical() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/Hierarchical.aut");
  }

  @Test
  @Ignore
  public void testPrettyPrinterAttr() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/PingPongAttributes.aut");
  }


  public void testPrettyPrinter(String model) throws IOException {
    final ASTAutomaton ast = parse(model);
    assertNotNull(ast);

    // when
    String output = AutomataMill.prettyPrint(ast, false);

    // then
    AutomataParser parser = AutomataMill.parser();
    final Optional<ASTAutomaton> astPrint = parser.parse_String(output);
    assertTrue("The pretty printed output is not syntactically correct: " + output, astPrint.isPresent());
    assertTrue("The pretty printed output does not compare to the original input", ast.deepEquals(astPrint.get()));
  }
}
