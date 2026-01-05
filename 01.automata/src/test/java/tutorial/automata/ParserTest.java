/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.Assertions;
import tutorial.automata._ast.ASTAutomaton;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ParserTest extends AbstractTest {

  @Test
  void testPingPong() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    Assertions.assertNotNull(aut);
    Assertions.assertEquals(5, aut.getTransitionList().size());
    Assertions.assertEquals(3, aut.getStateList().size());
    Assertions.assertEquals("startGame", aut.getTransition(0).getInput());
    Assertions.assertEquals("NoGame", aut.getState(0).getName());
  }
  
  @Test
  @org.junit.jupiter.api.Disabled 
  void testYourModel() throws IOException {
    //TODO Exercise 1: Delete the @org.junit.jupiter.api.Disabled annotation and insert the path to your model similar to the other test methods!
    //ASTAutomaton aut = parse("src/test/resources/tutorial/automata/<YOUR_MODEL>.aut");
  }
  
  @Test
  @org.junit.jupiter.api.Disabled 
  void testPingPongMealy() throws IOException {
    //TODO Exercise 2: Delete the @org.junit.jupiter.api.Disabled annotation to test if the mealy automaton parses correctly
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPongMealy.aut");
  }
}
