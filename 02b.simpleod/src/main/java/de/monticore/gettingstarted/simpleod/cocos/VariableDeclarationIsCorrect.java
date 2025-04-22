/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod.cocos;

import de.monticore.gettingstarted.simpleod._ast.ASTODAttribute;
import de.monticore.gettingstarted.simpleod._cocos.SimpleODASTODAttributeCoCo;
import de.monticore.types.check.TypeCalculator;

public class VariableDeclarationIsCorrect implements SimpleODASTODAttributeCoCo {

  protected TypeCalculator tc;

  protected static final String errorMsg = "The type and the expression of the attribute %s " +
     "are not compatible";
  public static final String errorCode = "0xA0487";

  public VariableDeclarationIsCorrect(TypeCalculator tc){
    this.tc = tc;
  }


  @Override
  public void check(ASTODAttribute node) {
    //TODO implement me!
    //If an expression (ODSimpleValue) to initialize the declared attribute is used,
    //  check that the types of the expression and of the declaration
    //  are compatible
    //Hint: Use the TypeCalculator class to check compatibility
  }
}
