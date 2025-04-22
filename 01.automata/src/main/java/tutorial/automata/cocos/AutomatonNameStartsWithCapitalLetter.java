/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.cocos;

import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AutomatonNameStartsWithCapitalLetter implements AutomataASTAutomatonCoCo {

  public static final String errorCode = "0xA001";

  public static final String errorMsg = " The name of the automaton %s must start with a capital letter";

  @Override
  public void check(ASTAutomaton node) {
    //TODO implement CoCo
  }

}
