/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.cocos;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AutomatonHasExactlyOneInitialState implements AutomataASTAutomatonCoCo {

  public static final String errorCode = "0xA005";

  public static final String errorMsg = " The automaton %s must have exactly one initial state";

  @Override
  public void check(ASTAutomaton node) {
    if(node.streamStates().filter(ASTState::isInitial).count() != 1) {
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }

}
