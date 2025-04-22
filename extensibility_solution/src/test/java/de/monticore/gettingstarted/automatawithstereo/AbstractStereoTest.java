/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automatawithstereo;

import de.monticore.gettingstarted.automatawithstereo.AutomataWithStereoMill;
import de.monticore.gettingstarted.automatawithstereo._ast.ASTAutomaton;
import de.monticore.gettingstarted.automatawithstereo._symboltable.AutomataWithStereoScopesGenitorDelegator;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AbstractStereoTest {

  public ASTAutomaton parse(String model) throws IOException {
    Optional<ASTAutomaton> aut = AutomataWithStereoMill.parser().parse(model);
    assertTrue(aut.isPresent());
    ASTAutomaton automaton = aut.get();
    AutomataWithStereoScopesGenitorDelegator automataScopesGenitorDelegator = AutomataWithStereoMill.scopesGenitorDelegator();
    automataScopesGenitorDelegator.createFromAST(automaton);
    return automaton;
  }

}
