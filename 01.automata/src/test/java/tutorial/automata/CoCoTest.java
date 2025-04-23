/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._cocos.AutomataCoCoChecker;
import tutorial.automata.cocos.*;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CoCoTest extends AbstractTest {

  @BeforeClass
  public static void init(){
    Log.enableFailQuick(false);
  }

  @Before
  public void setup(){
    Log.clearFindings();
  }

  public void checkValid(ASTAutomaton node){
    AutomataCoCoChecker checker = new AutomataCoCos().getCoCoChecker();
    checker.checkAll(node);
    assertTrue(Log.getFindings().isEmpty());
  }

  public void checkInvalid(ASTAutomaton node, String errorCode){
    AutomataCoCoChecker checker = new AutomataCoCos().getCoCoChecker();
    checker.checkAll(node);
    List<Finding> findings = Log.getFindings();
    assertFalse(findings.isEmpty());
    assertEquals(1, findings.size());
    assertTrue(findings.get(0).getMsg().startsWith(errorCode));
  }

  @Test
  public void testPingPongValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/PingPong.aut");
    checkValid(aut);
  }

  @Test
  public void testDoorValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Door.aut");
    checkValid(aut);
  }

  @Test
  public void testHierarchicalValid() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/Hierarchical.aut");
    checkValid(aut);
  }

  @Test
  @Ignore // TODO: Exercise 3
  public void testInvalidAutomatonName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/AutName.aut");
    checkInvalid(aut, AutomatonNameStartsWithCapitalLetter.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  public void testInvalidStateName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/StateName.aut");
    checkInvalid(aut, StateNameStartsWithCapitalLetter.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  public void testInvalidTransitionName() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/TransName.aut");
    checkInvalid(aut, TransitionNameUncapitalized.errorCode);
  }

  @Test
  public void testNoInitialState() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/NoInitial.aut");
    checkInvalid(aut, AutomatonHasExactlyOneInitialState.errorCode);
  }

  @Test
  @Ignore // TODO: Exercise 3
  public void testNoFinalState() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/NoFinal.aut");
    checkInvalid(aut, AutomatonHasAtLeastOneFinalState.errorCode);
  }
  
  @Test
  @Ignore // TODO: Exercise 6
  public void testTransitionSourceDoesNotExist() throws IOException {
    ASTAutomaton aut = parse("src/test/resources/tutorial/automata/invalid/TransitionSource.aut");
    checkInvalid(aut, TransitionSourceIsState.errorCode);
  }
}
