/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.cocos;

import tutorial.automata._ast.ASTTransition;
import tutorial.automata._cocos.AutomataASTTransitionCoCo;
import de.se_rwth.commons.logging.Log;

public class TransitionNameUncapitalized implements AutomataASTTransitionCoCo {

  public static final String errorCode = "0xA003";

  public static final String errorMsg = " The input of the transition %s must be uncapitalized";

  @Override
  public void check(ASTTransition node) {
    //TODO implement me
  }
}
