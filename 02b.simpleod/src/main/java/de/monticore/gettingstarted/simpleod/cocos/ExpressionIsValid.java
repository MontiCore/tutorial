/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod.cocos;

import de.monticore.expressions.commonexpressions._ast.ASTCallExpression;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsHandler;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsTraverser;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._cocos.ExpressionsBasisASTExpressionCoCo;
import de.monticore.types.check.TypeCalculator;

public class ExpressionIsValid implements ExpressionsBasisASTExpressionCoCo, CommonExpressionsHandler {

  protected TypeCalculator tc;

  protected static final String errorMsg = "The type of the expression could not be calculated";
  protected static final String errorCode = "0xA0456";

  public ExpressionIsValid(TypeCalculator tc){
    this.tc = tc;
  }


  @Override
  public void check(ASTExpression node) {
    //TODO implement me!
  }



  //leave the lower part as it is

  protected CommonExpressionsTraverser traverser;

  @Override
  public void handle(ASTCallExpression node) {
    visit(node);
    //do not traverse because subexpressions may not yield a correct result
    endVisit(node);
  }

  @Override
  public void setTraverser(CommonExpressionsTraverser traverser) {
    this.traverser = traverser;
  }

  @Override
  public CommonExpressionsTraverser getTraverser() {
    return this.traverser;
  }
}
