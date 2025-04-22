/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import tutorial.automata._ast.ASTAutomaton;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;

public class ParserTest extends AbstractTest {

  @Test
  public void testPingPong() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
  }
  
  @Test
  @Ignore 
  public void testYourModel() throws IOException {
    //TODO Exercise 1: Delete the @Ignore annotation and insert the path to your model similar to the other test methods!
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/<YOUR_MODEL>.aut");
  }
  
  @Test
  @Ignore 
  public void testPingPongMealy() throws IOException {
    //TODO Exercise 2: Delete the @Ignore annotation to test if the mealy automaton parses correctly
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPongMealy.aut");
  }

  @Test
  @Ignore 
  public void testWithPreconditions() throws IOException {
    //TODO Exercise TODO: Delete the @Ignore annotation to test if the mealy automaton parses correctly
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/WithPreconditions.aut");
  }
}
