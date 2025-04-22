/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.cocos;

import de.monticore.gettingstarted.automata._ast.ASTTransition;
import de.monticore.gettingstarted.automata._cocos.AutomataASTTransitionCoCo;
import de.se_rwth.commons.logging.Log;

public class TransitionNameUncapitalized implements AutomataASTTransitionCoCo {

  public static final String errorCode = "0xA003";

  public static final String errorMsg = " The input of the transition %s must be uncapitalized";

  @Override
  public void check(ASTTransition node) {
    //<#if solution>
    if(Character.isUpperCase(node.getInput().charAt(0))){
      Log.error(errorCode + String.format(errorMsg, node.getInput()));
    }
    //</#if>
    //TODO implement me
  }
}
