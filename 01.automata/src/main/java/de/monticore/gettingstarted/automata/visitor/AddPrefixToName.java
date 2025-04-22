/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.visitor;

import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._visitor.AutomataVisitor2;

public class AddPrefixToName implements AutomataVisitor2 {

  protected String prefix;

  public AddPrefixToName(String prefix) {
    this.prefix = prefix;
  }

  //TODO implement visitor, hint: change the name of the states
  //<#if solution>
  @Override
  public void visit(ASTState state){
    state.setName(prefix + state.getName());
  }
  //</#if>

}
