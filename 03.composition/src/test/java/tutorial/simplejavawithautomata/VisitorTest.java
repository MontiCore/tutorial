/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejavawithautomata;

import de.monticore.ast.ASTNode;
import de.monticore.symbols.oosymbols._symboltable.MethodSymbol;
import org.junit.jupiter.api.Assertions;
import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._ast.ASTState;
import tutorial.automata._symboltable.AutomatonSymbol;
import tutorial.automata.visitor.AddPrefixToName;
import tutorial.automata.visitor.CountStates;
import tutorial.automata.visitor.CountTransitions;
import tutorial.automata.visitor.StateCollector;
import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._ast.ASTJavaMethod;
import tutorial.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import tutorial.simplejavawithautomata._symboltable.SimpleJavaWithAutomataScope;
import tutorial.simplejavawithautomata._visitor.SimpleJavaWithAutomataTraverser;
import tutorial.simplejavawithautomata.types3.SimpleJavaWithAutomataTypeCheck3;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class VisitorTest extends AbstractTest {

  @BeforeEach
  public void setup(){
    SimpleJavaWithAutomataMill.globalScope().clear();
    SimpleJavaWithAutomataMill.init();
    SimpleJavaWithAutomataTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testVisitorsOnAutomaton() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
    ISimpleJavaWithAutomataArtifactScope as = createSymbolTable(ast);
    Optional<MethodSymbol> getMaxSymbol = as.resolveMethodDown("Bar.getMax");
    Assertions.assertTrue(getMaxSymbol.isPresent(), "getMax not found");
    SimpleJavaWithAutomataScope s = (SimpleJavaWithAutomataScope) ((ASTJavaMethod)getMaxSymbol.get()
            .getAstNode()).getJavaBlock().getSpannedScope();
    Optional<AutomatonSymbol> aut = s.resolveAutomatonDown("Door");
    Assertions.assertTrue(aut.isPresent());
    ASTAutomaton automaton = aut.get().getAstNode();
    checkCountTransitions(automaton, 4);
    checkCountStates(automaton, 5, 2, 1);
    checkChangeName(automaton, "Foo");
  }

  @Test
  @org.junit.jupiter.api.Disabled
  void testVisitorsOnWholeModel() throws IOException {
    ASTJavaCompilationUnit ast = parse("src/test/resources/tutorial/simplejavawithautomata/Bar.jla");
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
    Assertions.assertEquals(expectedNumber, ct.countTransitions());
  }

  public void checkCountStates(ASTNode automaton, int expectedNumber, int countInitial, int countFinal) {
    CountStates cs = new CountStates();
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4Automata(cs);
    automaton.accept(traverser);
    Assertions.assertEquals(expectedNumber, cs.countStates());
    Assertions.assertEquals(countInitial, cs.countInitialStates());
    Assertions.assertEquals(countFinal, cs.countFinalStates());
  }

  public void checkChangeName(ASTNode automaton, String prefix){
    AddPrefixToName cn = new AddPrefixToName(prefix);
    StateCollector sc = new StateCollector();
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4Automata(cn);
    traverser.add4Automata(sc);
    automaton.accept(traverser);
    List<ASTState> stateList = sc.collectStates();
    Assertions.assertFalse(stateList.isEmpty());
    for(ASTState state: stateList){
      Assertions.assertTrue(state.getName().startsWith(prefix));
    }
  }

}
