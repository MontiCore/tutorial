/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.cocos;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AutomatonHasAtLeastOneFinalState implements AutomataASTAutomatonCoCo {

  public static final String errorCode = "0xA006";

  public static final String errorMsg = " The automaton %s must have at least one final state";

  @Override
  public void check(ASTAutomaton node) {
    //TODO implement me!
  }

}
