/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;

public class ParserTest extends AbstractTest {

  @Test
  public void testPingPong() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/de/monticore/gettingstarted/automata/PingPong.aut");
  }

  @Test
  //<#if !solution>@Ignore //</#if>
  public void testPingPongMealy() throws IOException {
    //TODO Exercise TODO: Delete the @Ignore annotation to test if the mealy automaton parses correctly
    ASTAutomaton aut = parse("src/test/resources/de/monticore/gettingstarted/automata/PingPongMealy.aut");
  }

  @Test
  //<#if !solution>@Ignore //</#if>
  public void testWithPreconditions() throws IOException {
    //TODO Exercise TODO: Delete the @Ignore annotation to test if the mealy automaton parses correctly
    ASTAutomaton aut = parse("src/test/resources/de/monticore/gettingstarted/automata/WithPreconditions.aut");
  }
}
