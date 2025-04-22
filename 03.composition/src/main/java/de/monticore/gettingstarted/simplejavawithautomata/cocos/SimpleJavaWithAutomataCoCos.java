/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata.cocos;

import de.monticore.gettingstarted.automata.cocos.AutomataCoCos;
import de.monticore.gettingstarted.simplejava.cocos.ExpressionIsValid;
import de.monticore.gettingstarted.simplejava.cocos.VariableDeclarationIsCorrect;
import de.monticore.gettingstarted.simplejavawithautomata._cocos.SimpleJavaWithAutomataCoCoChecker;
import de.monticore.types.check.TypeCalculator;

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
