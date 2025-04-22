/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.visitor;

import de.monticore.gettingstarted.automata._ast.ASTTransition;
import de.monticore.gettingstarted.automata._visitor.AutomataVisitor2;

import java.util.ArrayList;
import java.util.List;

public class CountTransitions implements AutomataVisitor2 {

  protected int count;

  protected List<String> transitionInputs = new ArrayList<>();


  @Override
  public void visit(ASTTransition node) {
    this.count++;
    this.transitionInputs.add(node.getInput());
  }

  public int countTransitions(){
    return this.count;
  }

  public List<String> getTransitionInputs() {
    return this.transitionInputs;
  }

}
