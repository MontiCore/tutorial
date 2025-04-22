/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava.cocos;

import de.monticore.gettingstarted.simplejava._cocos.SimpleJavaCoCoChecker;

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
