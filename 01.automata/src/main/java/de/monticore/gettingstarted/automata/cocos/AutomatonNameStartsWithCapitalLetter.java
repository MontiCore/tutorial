/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.cocos;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AutomatonNameStartsWithCapitalLetter implements AutomataASTAutomatonCoCo {

  public static final String errorCode = "0xA001";

  public static final String errorMsg = " The name of the automaton %s must start with a capital letter";

  @Override
  public void check(ASTAutomaton node) {
    //<#if solution>
    if(!Character.isUpperCase(node.getName().charAt(0))){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
    //</#if>
    //TODO implement CoCo
  }

}
