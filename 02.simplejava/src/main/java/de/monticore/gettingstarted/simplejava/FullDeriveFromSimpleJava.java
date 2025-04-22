/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.types.check.*;

@Deprecated(forRemoval = true)
public class FullDeriveFromSimpleJava extends AbstractDerive {

  public FullDeriveFromSimpleJava(){
    this(SimpleJavaMill.traverser());
  }

  public FullDeriveFromSimpleJava(SimpleJavaTraverser traverser){
    super(traverser);
    init(traverser);
  }

  public void init(SimpleJavaTraverser traverser) {
    //TODO implement me!
    //<#if solution>
    DeriveSymTypeOfAssignmentExpressions assignmentExpressions = new DeriveSymTypeOfAssignmentExpressions();
    DeriveSymTypeOfExpression expressionsBasis = new DeriveSymTypeOfExpression();
    DeriveSymTypeOfCommonExpressions commonExpressions = new DeriveSymTypeOfCommonExpressions();
    DeriveSymTypeOfBitExpressions bitExpressions = new DeriveSymTypeOfBitExpressions();
    DeriveSymTypeOfLiterals literalsBasis = new DeriveSymTypeOfLiterals();
    DeriveSymTypeOfMCCommonLiterals commonLiterals = new DeriveSymTypeOfMCCommonLiterals();

    assignmentExpressions.setTypeCheckResult(typeCheckResult);
    expressionsBasis.setTypeCheckResult(typeCheckResult);
    commonExpressions.setTypeCheckResult(typeCheckResult);
    literalsBasis.setTypeCheckResult(typeCheckResult);
    bitExpressions.setTypeCheckResult(typeCheckResult);
    commonLiterals.setTypeCheckResult(typeCheckResult);

    traverser.add4AssignmentExpressions(assignmentExpressions);
    traverser.setAssignmentExpressionsHandler(assignmentExpressions);

    traverser.add4ExpressionsBasis(expressionsBasis);
    traverser.setExpressionsBasisHandler(expressionsBasis);

    traverser.add4CommonExpressions(commonExpressions);
    traverser.setCommonExpressionsHandler(commonExpressions);

    traverser.add4BitExpressions(bitExpressions);
    traverser.setBitExpressionsHandler(bitExpressions);

    traverser.add4MCLiteralsBasis(literalsBasis);

    traverser.add4MCCommonLiterals(commonLiterals);
    //</#if>
  }

}
