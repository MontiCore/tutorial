/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._ast.ASTState;
import tutorial.automata._symboltable.StateSymbol;
import tutorial.automata._visitor.AutomataTraverser;
import tutorial.automata.visitor.AddPrefixToName;
import tutorial.automata.visitor.CountStates;
import tutorial.automata.visitor.CountTransitions;
import tutorial.automata.visitor.StateCollector;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class VisitorTest extends AbstractTest {

  @Test
  @Ignore
  public void testPingPongTCount() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkCountTransitions(aut, 5);
  }

  @Test
  @Ignore // TODO Exercise 5: (once CountStates is implemented)  
  public void testPingPong() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkCountTransitions(aut, 5);
    checkCountStates(aut, 3, 1, 1);
    // We do not check for the change name in this test
  }

  @Test
  @Ignore // TODO Exercise 5 (once both visitors are implemented)  
  public void testDoor() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Door.aut");
    checkCountTransitions(aut, 4);
    checkCountStates(aut, 5, 2, 1);
    checkChangeName(aut, "Bar");
  }

  @Test
  @Ignore // TODO Exercise 5 (once both visitors are implemented)  
  public void testHierarchical() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Hierarchical.aut");
    checkCountTransitions(aut, 7);
    checkCountStates(aut, 6, 3, 1);
    checkChangeName(aut, "Bar");
  }


  public void checkCountTransitions(ASTAutomaton automaton, int expectedNumber) {
    CountTransitions ct = new CountTransitions();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.add4Automata(ct);
    automaton.accept(traverser);
    assertEquals("CountTransitions result incorrect ", expectedNumber, ct.countTransitions());
  }

  public void checkCountStates(ASTAutomaton automaton, int expectedNumber, int countInitial, int countFinal) {
    CountStates cs = new CountStates();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.add4Automata(cs);
    automaton.accept(traverser);
    assertEquals("CountStates state result incorrect", expectedNumber, cs.countStates());
    assertEquals("CountStates initial state result incorrect", countInitial, cs.countInitialStates());
    assertEquals("CountStates final state result incorrect", countFinal, cs.countFinalStates());
  }

  public void checkChangeName(ASTAutomaton automaton, String prefix){
    AddPrefixToName cn = new AddPrefixToName(prefix);
    StateCollector sc = new StateCollector();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.add4Automata(cn);
    traverser.add4Automata(sc);
    automaton.accept(traverser);
    List<ASTState> stateList = automaton.getSpannedScope()
            .getStateSymbols().values().stream().map(StateSymbol::getAstNode)
            .collect(Collectors.toList());
    assertFalse("All states were removed?", stateList.isEmpty());
    for(ASTState state: stateList){
      assertTrue("AddPrefixToName did not add prefix, found " + state.getName() , state.getName().startsWith(prefix));
    }
  }

}
