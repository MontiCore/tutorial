/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.visitor;

import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._visitor.AutomataVisitor2;

import java.util.ArrayList;
import java.util.List;

public class CountStates implements AutomataVisitor2 {

  protected int count = 0;

  protected int countInitial = 0;

  protected int countFinal = 0;

  protected List<String> stateNames = new ArrayList<>();

  protected List<String> finalStateNames = new ArrayList<>();

  protected List<String> initialStateNames = new ArrayList<>();

  public int countStates(){
    return count;
  }

  public int countInitialStates(){
    return countInitial;
  }

  public int countFinalStates(){
    return countFinal;
  }

  public List<String> getStateNames() {
    return stateNames;
  }

  public List<String> getInitialStateNames() {
    return initialStateNames;
  }

  public List<String> getFinalStateNames() {
    return finalStateNames;
  }

  //TODO implement visitor

}
