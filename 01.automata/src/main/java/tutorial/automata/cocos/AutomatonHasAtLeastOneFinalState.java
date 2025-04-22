/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.cocos;

import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AutomatonHasAtLeastOneFinalState implements AutomataASTAutomatonCoCo {

  public static final String errorCode = "0xA006";

  public static final String errorMsg = " The automaton %s must have at least one final state";

  @Override
  public void check(ASTAutomaton node) {
    //TODO implement me!
  }

}
