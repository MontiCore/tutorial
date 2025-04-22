/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata.visitor;

import com.google.common.collect.Sets;
import tutorial.automata._ast.ASTState;
import tutorial.automata._visitor.AutomataVisitor2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StateCollector implements AutomataVisitor2 {

  protected Set<ASTState> states = Sets.newHashSet();

  @Override
  public void visit(ASTState state) {
    states.add(state);
  }

  public List<ASTState> collectStates(){
    return new ArrayList<>(states);
  }

}
