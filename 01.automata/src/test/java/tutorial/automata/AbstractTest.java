/* (c) https://github.com/MontiCore/monticore */
package tutorial.automata;

import de.monticore.runtime.junit.MCAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._symboltable.AutomataScopesGenitorDelegator;
import de.se_rwth.commons.logging.LogStub;

import java.io.IOException;
import java.util.Optional;


public class AbstractTest {

  @BeforeAll
  public static void init() {
    LogStub.init();
    LogStub.enableFailQuick(false);
    AutomataMill.init();
  }

  @AfterAll
  public static void reset() {
    AutomataMill.reset();
  }

  @BeforeEach
  public void before() {
    LogStub.clearFindings();
  }

  public ASTAutomaton parse(String model) throws IOException {
    Optional<ASTAutomaton> aut = AutomataMill.parser().parse(model);
    MCAssertions.assertNoFindings();
    ASTAutomaton automaton = aut.get();
    AutomataScopesGenitorDelegator automataScopesGenitorDelegator = AutomataMill.scopesGenitorDelegator();
    automataScopesGenitorDelegator.createFromAST(automaton);
    return automaton;
  }

}
