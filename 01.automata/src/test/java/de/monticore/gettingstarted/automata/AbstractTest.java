/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._symboltable.AutomataScopesGenitorDelegator;
import de.se_rwth.commons.logging.LogStub;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  @BeforeClass
  public static void init() {
    LogStub.init();
    LogStub.enableFailQuick(false);
  }

  public ASTAutomaton parse(String model) throws IOException {
    Optional<ASTAutomaton> aut = AutomataMill.parser().parse(model);
    assertTrue(aut.isPresent());
    ASTAutomaton automaton = aut.get();
    AutomataScopesGenitorDelegator automataScopesGenitorDelegator = AutomataMill.scopesGenitorDelegator();
    automataScopesGenitorDelegator.createFromAST(automaton);
    return automaton;
  }

}
