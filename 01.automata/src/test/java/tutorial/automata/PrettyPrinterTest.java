/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.Assertions;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._parser.AutomataParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;


class PrettyPrinterTest extends AbstractTest {

  @Test
  @org.junit.jupiter.api.Disabled
  void testPrettyPrinter() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/PingPong.aut");
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testPrettyPrinterHierarchical() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/Hierarchical.aut");
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testPrettyPrinterAttr() throws IOException {
    testPrettyPrinter("src/test/resources/tutorial/automata/PingPongAttributes.aut");
  }


  public void testPrettyPrinter(String model) throws IOException {
    final ASTAutomaton ast = parse(model);
    Assertions.assertNotNull(ast);

    // when
    String output = AutomataMill.prettyPrint(ast, false);

    // then
    AutomataParser parser = AutomataMill.parser();
    final Optional<ASTAutomaton> astPrint = parser.parse_String(output);
    Assertions.assertTrue(astPrint.isPresent(), "The pretty printed output is not syntactically correct: " + output);
    Assertions.assertTrue(ast.deepEquals(astPrint.get()),
                          "The pretty printed output does not compare to the original input");
  }
}
