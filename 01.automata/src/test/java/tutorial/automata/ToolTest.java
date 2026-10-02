/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorial.automata._ast.ASTAutomaton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ToolTest extends AbstractTest {
  protected AutomataTool tool;

  @BeforeEach
  void setUp() {
    tool = new AutomataTool();
  }

  @Test
  void testTool(){
    tool.run(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut"});
  }

  @Test
  @org.junit.jupiter.api.Disabled // Task 6 
  void testReports() throws IOException {
    tool.run(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-r", "target/automata/reports/"});
    File reportFile = new File("target/automata/reports/PingPong.txt");
    assertTrue(reportFile.exists());
    String content = Files.readString(reportFile.toPath());
    assertTrue(content.contains("Number of States: 3"));
    assertTrue(content.contains("Number of Transitions: 5"));
  }

  @Test
  @org.junit.jupiter.api.Disabled // Task 8 
  void testPrettyPrinter() throws IOException {
    ASTAutomaton originalAut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    tool.run(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-pp", "target/automata/pp/"});
    assertTrue(new File("target/automata/pp/PingPong.aut").exists());
    ASTAutomaton newAut = parse("target/automata/pp/PingPong.aut");
    assertTrue(originalAut.deepEquals(newAut));
  }

}
