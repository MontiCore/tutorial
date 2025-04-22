/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import tutorial.automata._ast.ASTAutomaton;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

public class ToolTest extends AbstractTest {

  @Test
  public void testTool(){
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut"});
  }

  @Test
  @Ignore // Task 6 
  public void testReports() throws IOException {
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-r", "target/automata/reports/"});
    File reportFile = new File("target/automata/reports/PingPong.txt");
    assertTrue(reportFile.exists());
    String content = Files.readString(reportFile.toPath());
    assertTrue(Files.readString(reportFile.toPath()).contains("Number of States: 3"));
    assertTrue(Files.readString(reportFile.toPath()).contains("Number of Transitions: 5"));
  }

  @Test
  @Ignore // Task 8 
  public void testPrettyPrinter() throws IOException {
    ASTAutomaton originalAut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    AutomataTool.main(new String[]{"-i", "src/test/resources/tutorial/automata/PingPong.aut",
            "-pp", "target/automata/pp/"});
    assertTrue(new File("target/automata/pp/PingPong.aut").exists());
    ASTAutomaton newAut = parse("target/automata/pp/PingPong.aut");
    assertTrue(originalAut.deepEquals(newAut));
  }

}
