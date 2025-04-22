/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.cocos;

import tutorial.automata._ast.ASTTransition;
import tutorial.automata._cocos.AutomataASTTransitionCoCo;
import de.se_rwth.commons.logging.Log;

public class TransitionSourceIsState implements AutomataASTTransitionCoCo {

  public static final String errorCode = "0xA004";

  public static final String errorMsg = " The state %s referenced in the transition %s is not resolvable";

  @Override
  public void check(ASTTransition node) {
    //TODO implement me!
    // check that the "from" name of the transition can be resolved to a state

    // hint: use the symbol table by accessing the enclosing scope of the transition
  }
}
