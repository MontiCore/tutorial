/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automata;

import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._symboltable.AutomataScopesGenitorDelegator;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.LogStub;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  @BeforeClass
  public static void init() {
    LogStub.init();
    LogStub.enableFailQuick(false);
    AutomataMill.init();
  }

  @AfterClass
  public static void reset() {
    AutomataMill.reset();
  }

  @Before
  public void before() {
    LogStub.clearFindings();
  }

  public ASTAutomaton parse(String model) throws IOException {
    Optional<ASTAutomaton> aut = AutomataMill.parser().parse(model);
    assertTrue(LogStub.getFindings().stream().map(Finding::buildMsg).collect(Collectors.joining(System.lineSeparator())), aut.isPresent());
    ASTAutomaton automaton = aut.get();
    AutomataScopesGenitorDelegator automataScopesGenitorDelegator = AutomataMill.scopesGenitorDelegator();
    automataScopesGenitorDelegator.createFromAST(automaton);
    return automaton;
  }

}
