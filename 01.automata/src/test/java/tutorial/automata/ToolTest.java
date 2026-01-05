/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.Assertions;
import tutorial.automata._ast.ASTAutomaton;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class ToolTest extends AbstractTest {

  @Test
  void testTool(){
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut"});
  }

  @Test
  @org.junit.jupiter.api.Disabled // Task 6 
  void testReports() throws IOException {
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-r", "target/automata/reports/"});
    File reportFile = new File("target/automata/reports/PingPong.txt");
    Assertions.assertTrue(reportFile.exists());
    String content = Files.readString(reportFile.toPath());
    Assertions.assertTrue(Files.readString(reportFile.toPath()).contains("Number of States: 3"));
    Assertions.assertTrue(Files.readString(reportFile.toPath()).contains("Number of Transitions: 5"));
  }

  @Test
  @org.junit.jupiter.api.Disabled // Task 8 
  void testPrettyPrinter() throws IOException {
    ASTAutomaton originalAut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-pp", "target/automata/pp/"});
    Assertions.assertTrue(new File("target/automata/pp/PingPong.aut").exists());
    ASTAutomaton newAut = parse("target/automata/pp/PingPong.aut");
    Assertions.assertTrue(originalAut.deepEquals(newAut));
  }

}
