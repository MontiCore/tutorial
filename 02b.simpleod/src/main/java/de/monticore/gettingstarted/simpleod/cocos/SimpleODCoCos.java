/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod.cocos;

import de.monticore.gettingstarted.simpleod._cocos.SimpleODCoCoChecker;
import de.monticore.gettingstarted.simpleod.types.FullDeriveFromSimpleOD;
import de.monticore.gettingstarted.simpleod.types.FullSynthesizeFromSimpleOD;
import de.monticore.types.check.IDerive;
import de.monticore.types.check.ISynthesize;
import de.monticore.types.check.TypeCalculator;

public class SimpleODCoCos {

  public SimpleODCoCoChecker getCoCoChecker(){
    SimpleODCoCoChecker checker = new SimpleODCoCoChecker();
    IDerive deriver = new FullDeriveFromSimpleOD();
    ISynthesize synthesizer = new FullSynthesizeFromSimpleOD();
    TypeCalculator tc = new TypeCalculator(synthesizer, deriver);
    ExpressionIsValid coco = new ExpressionIsValid(tc);
    checker.addCoCo(coco);
    checker.getTraverser().setCommonExpressionsHandler(coco);
    checker.addCoCo(new VariableDeclarationIsCorrect(tc));
    return checker;
  }

}
