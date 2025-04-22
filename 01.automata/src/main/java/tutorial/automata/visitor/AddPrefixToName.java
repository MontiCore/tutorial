/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.visitor;

import tutorial.automata._ast.ASTState;
import tutorial.automata._visitor.AutomataVisitor2;

public class AddPrefixToName implements AutomataVisitor2 {

  protected String prefix;

  public AddPrefixToName(String prefix) {
    this.prefix = prefix;
  }

  //TODO implement visitor, hint: change the name of the states

}
