/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._ast.ASTState;
import tutorial.automata._symboltable.StateSymbol;
import tutorial.automata._visitor.AutomataTraverser;
import tutorial.automata.visitor.AddPrefixToName;
import tutorial.automata.visitor.CountStates;
import tutorial.automata.visitor.CountTransitions;
import tutorial.automata.visitor.StateCollector;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class VisitorTest extends AbstractTest {

  @Test
  @org.junit.jupiter.api.Disabled
  void testPingPongTCount() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkCountTransitions(aut, 5);
  }

  @Test
  @org.junit.jupiter.api.Disabled // TODO Exercise 5: (once CountStates is implemented)  
  void testPingPong() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkCountTransitions(aut, 5);
    checkCountStates(aut, 3, 1, 1);
    // We do not check for the change name in this test
  }

  @Test
  @org.junit.jupiter.api.Disabled // TODO Exercise 5 (once both visitors are implemented)  
  void testDoor() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Door.aut");
    checkCountTransitions(aut, 4);
    checkCountStates(aut, 5, 2, 1);
    checkChangeName(aut, "Bar");
  }

  @Test
  @org.junit.jupiter.api.Disabled // TODO Exercise 5 (once both visitors are implemented)  
  void testHierarchical() throws IOException {
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
    Assertions.assertEquals(expectedNumber, ct.countTransitions(), "CountTransitions result incorrect ");
  }

  public void checkCountStates(ASTAutomaton automaton, int expectedNumber, int countInitial, int countFinal) {
    CountStates cs = new CountStates();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.add4Automata(cs);
    automaton.accept(traverser);
    Assertions.assertEquals(expectedNumber, cs.countStates(), "CountStates state result incorrect");
    Assertions.assertEquals(countInitial, cs.countInitialStates(), "CountStates initial state result incorrect");
    Assertions.assertEquals(countFinal, cs.countFinalStates(), "CountStates final state result incorrect");
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
    Assertions.assertFalse(stateList.isEmpty(), "All states were removed?");
    for(ASTState state: stateList){
      Assertions.assertTrue(state.getName().startsWith(prefix),
                            "AddPrefixToName did not add prefix, found " + state.getName());
    }
  }

}
