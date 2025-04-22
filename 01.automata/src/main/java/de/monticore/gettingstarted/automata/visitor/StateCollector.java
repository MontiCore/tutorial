/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata.visitor;

import com.google.common.collect.Sets;
import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._visitor.AutomataVisitor2;

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
