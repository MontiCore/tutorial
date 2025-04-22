/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava.cocos;

import de.monticore.gettingstarted.simplejava.FullDeriveFromSimpleJava;
import de.monticore.gettingstarted.simplejava.FullSynthesizeFromSimpleJava;
import de.monticore.gettingstarted.simplejava._cocos.SimpleJavaCoCoChecker;
import de.monticore.types.check.IDerive;
import de.monticore.types.check.ISynthesize;
import de.monticore.types.check.TypeCalculator;

public class SimpleJavaCoCos {

  public SimpleJavaCoCoChecker getCoCoChecker(){
    SimpleJavaCoCoChecker checker = new SimpleJavaCoCoChecker();
    IDerive deriver = new FullDeriveFromSimpleJava();
    ISynthesize synthesizer = new FullSynthesizeFromSimpleJava();
    TypeCalculator tc = new TypeCalculator(synthesizer, deriver);
    ExpressionIsValid coco = new ExpressionIsValid(tc);
    checker.addCoCo(coco);
    checker.getTraverser().setCommonExpressionsHandler(coco);
    checker.addCoCo(new VariableDeclarationIsCorrect(tc));
    return checker;
  }

}
