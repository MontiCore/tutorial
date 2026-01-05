/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import org.junit.jupiter.api.*;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._cocos.AutomataCoCoChecker;
import tutorial.automata.cocos.*;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

class CoCoTest extends AbstractTest {

  @BeforeAll
  public static void init(){
    Log.enableFailQuick(false);
  }

  @BeforeEach
  public void setup(){
    Log.clearFindings();
  }

  public void checkValid(ASTAutomaton node){
    AutomataCoCoChecker checker = new AutomataCoCos().getCoCoChecker();
    checker.checkAll(node);
    Assertions.assertTrue(Log.getFindings().isEmpty());
  }

  public void checkInvalid(ASTAutomaton node, String errorCode){
    AutomataCoCoChecker checker = new AutomataCoCos().getCoCoChecker();
    checker.checkAll(node);
    List<Finding> findings = Log.getFindings();
    Assertions.assertFalse(findings.isEmpty());
    Assertions.assertEquals(1, findings.size());
    Assertions.assertTrue(findings.get(0).getMsg().startsWith(errorCode));
  }

  @Test
  void testPingPongValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkValid(aut);
  }

  @Test
  void testDoorValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Door.aut");
    checkValid(aut);
  }

  @Test
  void testHierarchicalValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Hierarchical.aut");
    checkValid(aut);
  }

  @Test
  @Ignore // TODO: Exercise 3
  void testInvalidAutomatonName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/AutName.aut");
    checkInvalid(aut, AutomatonNameStartsWithCapitalLetter.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  void testInvalidStateName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/StateName.aut");
    checkInvalid(aut, StateNameStartsWithCapitalLetter.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  void testInvalidTransitionName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/TransName.aut");
    checkInvalid(aut, TransitionNameUncapitalized.errorCode);
  }

  @Test
  void testNoInitialState() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/NoInitial.aut");
    checkInvalid(aut, AutomatonHasExactlyOneInitialState.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  void testNoFinalState() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/NoFinal.aut");
    checkInvalid(aut, AutomatonHasAtLeastOneFinalState.errorCode);
  }
  
  @Test
  @Ignore // TODO: Exercise 6
  void testTransitionSourceDoesNotExist() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/TransitionSource.aut");
    checkInvalid(aut, TransitionSourceIsState.errorCode);
  }
}
