/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.cocos;

import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._cocos.AutomataASTStateCoCo;
import de.se_rwth.commons.logging.Log;

public class StateNameStartsWithCapitalLetter implements AutomataASTStateCoCo {

  public static final String errorCode = "0xA002";

  public static final String errorMsg = " The name of the state %s must start with a capital letter";


  @Override
  public void check(ASTState node) {
    //TODO implement me!
  }

}
