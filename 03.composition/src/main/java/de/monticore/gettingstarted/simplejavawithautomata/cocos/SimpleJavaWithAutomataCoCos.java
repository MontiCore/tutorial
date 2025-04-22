/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata.cocos;

import de.monticore.gettingstarted.automata.cocos.AutomataCoCos;
import de.monticore.gettingstarted.simplejava.FullDeriveFromSimpleJava;
import de.monticore.gettingstarted.simplejava.FullSynthesizeFromSimpleJava;
import de.monticore.gettingstarted.simplejava.cocos.ExpressionIsValid;
import de.monticore.gettingstarted.simplejava.cocos.VariableDeclarationIsCorrect;
import de.monticore.gettingstarted.simplejavawithautomata._cocos.SimpleJavaWithAutomataCoCoChecker;
import de.monticore.types.check.TypeCalculator;

public class SimpleJavaWithAutomataCoCos {

  public SimpleJavaWithAutomataCoCoChecker getCoCoChecker(){
    SimpleJavaWithAutomataCoCoChecker checker = new SimpleJavaWithAutomataCoCoChecker();
    // TODO: Add the Automata CoCos here
    TypeCalculator tc = new TypeCalculator(new FullSynthesizeFromSimpleJava(), new FullDeriveFromSimpleJava());
    ExpressionIsValid coco = new ExpressionIsValid(tc);
    checker.addCoCo(new VariableDeclarationIsCorrect(tc));
    checker.addCoCo(coco);
    checker.getTraverser().setCommonExpressionsHandler(coco);

    return checker;
  }

}
