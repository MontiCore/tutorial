/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.cocos;

import tutorial.automata._cocos.AutomataCoCoChecker;

public class AutomataCoCos {

  public AutomataCoCoChecker getCoCoChecker(){
    AutomataCoCoChecker checker = new AutomataCoCoChecker();
    checker.addCoCo(new AutomatonHasExactlyOneInitialState());
    checker.addCoCo(new AutomatonHasAtLeastOneFinalState());
    checker.addCoCo(new AutomatonNameStartsWithCapitalLetter());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    checker.addCoCo(new TransitionNameUncapitalized());
    checker.addCoCo(new TransitionSourceIsState());
    return checker;
  }

}
