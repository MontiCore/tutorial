/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata;

import de.monticore.ast.ASTNode;
import de.monticore.gettingstarted.automata.AutomataMill;
import de.monticore.gettingstarted.automata._ast.ASTAutomaton;
import de.monticore.gettingstarted.automata._ast.ASTState;
import de.monticore.gettingstarted.automata._symboltable.AutomatonSymbol;
import de.monticore.gettingstarted.automata.visitor.AddPrefixToName;
import de.monticore.gettingstarted.automata.visitor.CountStates;
import de.monticore.gettingstarted.automata.visitor.CountTransitions;
import de.monticore.gettingstarted.automata.visitor.StateCollector;
import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import de.monticore.gettingstarted.simplejavawithautomata._visitor.SimpleJavaWithAutomataTraverser;
import de.monticore.gettingstarted.simplejavawithautomata.types3.SimpleJavaWithAutomataTypeCheck3;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class VisitorTest extends AbstractTest {

  @Before
  public void setup(){
    SimpleJavaWithAutomataMill.globalScope().clear();
    SimpleJavaWithAutomataMill.init();
    SimpleJavaWithAutomataTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @Ignore
  public void testVisitorsOnAutomaton() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/de/monticore/gettingstarted/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope as = createSymbolTable(ast);
    Optional<AutomatonSymbol> aut = as.resolveAutomatonDown("Bar.getMax.Door");
    assertTrue(aut.isPresent());
    ASTAutomaton automaton = aut.get().getAstNode();
    checkCountTransitions(automaton, 4);
    checkCountStates(automaton, 5, 2, 1);
    checkChangeName(automaton, "Foo");
  }

  @Test
  @Ignore
  public void testVisitorsOnWholeModel() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/de/monticore/gettingstarted/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope as = createSymbolTable(ast);
    checkCountTransitions(ast, 4);
    checkCountStates(ast, 5, 2, 1);
    checkChangeName(ast, "Foo");
  }

  public void checkCountTransitions(ASTNode automaton, int expectedNumber) {
    CountTransitions ct = new CountTransitions();
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4Automata(ct);
    automaton.accept(traverser);
    assertEquals(expectedNumber, ct.countTransitions());
  }

  public void checkCountStates(ASTNode automaton, int expectedNumber, int countInitial, int countFinal) {
    CountStates cs = new CountStates();
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4Automata(cs);
    automaton.accept(traverser);
    assertEquals(expectedNumber, cs.countStates());
    assertEquals(countInitial, cs.countInitialStates());
    assertEquals(countFinal, cs.countFinalStates());
  }

  public void checkChangeName(ASTNode automaton, String prefix){
    AddPrefixToName cn = new AddPrefixToName(prefix);
    StateCollector sc = new StateCollector();
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4Automata(cn);
    traverser.add4Automata(sc);
    automaton.accept(traverser);
    List<ASTState> stateList = sc.collectStates();
    assertFalse(stateList.isEmpty());
    for(ASTState state: stateList){
      assertTrue(state.getName().startsWith(prefix));
    }
  }

}
