/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejavawithautomata.cocos;

import tutorial.automata.cocos.AutomataCoCos;
import tutorial.simplejava.cocos.ExpressionIsValid;
import tutorial.simplejava.cocos.VariableDeclarationIsCorrect;
import tutorial.simplejavawithautomata._cocos.SimpleJavaWithAutomataCoCoChecker;

public class SimpleJavaWithAutomataCoCos {

  public SimpleJavaWithAutomataCoCoChecker getCoCoChecker(){
    SimpleJavaWithAutomataCoCoChecker checker = new SimpleJavaWithAutomataCoCoChecker();
    // TODO: Add the Automata CoCos here
    ExpressionIsValid coco = new ExpressionIsValid();
    checker.addCoCo(new VariableDeclarationIsCorrect());
    checker.addCoCo(coco);
    checker.getTraverser().setCommonExpressionsHandler(coco);

    return checker;
  }

}
