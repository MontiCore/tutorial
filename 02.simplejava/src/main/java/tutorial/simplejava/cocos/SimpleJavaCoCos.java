/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava.cocos;

import tutorial.simplejava._cocos.SimpleJavaCoCoChecker;

public class SimpleJavaCoCos {

  public SimpleJavaCoCoChecker getCoCoChecker(){
    SimpleJavaCoCoChecker checker = new SimpleJavaCoCoChecker();
    ExpressionIsValid coco = new ExpressionIsValid();
    checker.addCoCo(coco);
    checker.getTraverser().setCommonExpressionsHandler(coco);
    checker.addCoCo(new VariableDeclarationIsCorrect());
    return checker;
  }

}
