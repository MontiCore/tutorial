/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava.cocos;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaVarDecl;
import de.monticore.gettingstarted.simplejava._cocos.SimpleJavaASTJavaVarDeclCoCo;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.TypeCalculator;
import de.se_rwth.commons.logging.Log;

public class VariableDeclarationIsCorrect implements SimpleJavaASTJavaVarDeclCoCo {

  protected TypeCalculator tc;

  protected static final String errorMsg = "The type and the expression of the variable declaration %s " +
     "are not compatible";
  protected static final String errorCode = "0xA0457";

  public VariableDeclarationIsCorrect(TypeCalculator tc){
    this.tc = tc;
  }


  @Override
  public void check(ASTJavaVarDecl node) {
    //TODO implement me!
    //If an expression to initialize the declared attribute is present
    //  then check that the types of the expression and of the declaration
    //  are compatible
    //Hint: Use the TypeCalculator class to check compatibility
  }
}
