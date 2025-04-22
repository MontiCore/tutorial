/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejavawithautomata;

import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejavawithautomata._cocos.SimpleJavaWithAutomataCoCoChecker;
import tutorial.simplejavawithautomata._symboltable.SimpleJavaWithAutomataPhasedSTC;
import tutorial.simplejavawithautomata.cocos.SimpleJavaWithAutomataCoCos;
import tutorial.simplejavawithautomata.types3.SimpleJavaWithAutomataTypeCheck3;
import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoCoTest extends AbstractTest {

  private ASTJavaCompilationUnit check;
  private ASTJavaCompilationUnit bar;
  private ASTJavaCompilationUnit inheritanceBar;
  private ASTJavaCompilationUnit staticAbstractOOMethods;
  private ASTJavaCompilationUnit staticAbstractOOFields;
  private ASTJavaCompilationUnit wrongAssignment;
  private ASTJavaCompilationUnit complicatedWrongAssignment;
  private ASTJavaCompilationUnit complicatedCorrectAssignment;
  private ASTJavaCompilationUnit inheritedCannotUseStaticFromSuper;

  private ASTJavaCompilationUnit invalidAutomaton;

  @BeforeClass
  public static void init(){
    LogStub.init();
    LogStub.enableFailQuick(false);
    SimpleJavaWithAutomataMill.init();
    SimpleJavaWithAutomataTypeCheck3.init();
    Log.clearFindings();
  }

  @Before
  public void setup() throws IOException {
    SimpleJavaWithAutomataMill.globalScope().clear();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleJavaWithAutomataMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleJavaWithAutomataMill.globalScope())
      .build();
    string.setSpannedScope(SimpleJavaWithAutomataMill.scope());
    SimpleJavaWithAutomataMill.globalScope().add(string);
    SimpleJavaWithAutomataMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
    SimpleJavaWithAutomataPhasedSTC stc = new SimpleJavaWithAutomataPhasedSTC();

    this.bar = parse("src/test/resources/tutorial/simplejava/valid/Bar.java");
    stc.createFromAST(this.bar);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.inheritanceBar = parse("src/test/resources/tutorial/simplejava/valid/InheritanceBar.java");
    stc.createFromAST(this.inheritanceBar);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.staticAbstractOOMethods = parse("src/test/resources/tutorial/simplejava/invalid/StaticAbstractOOMethods.java");
    stc.createFromAST(this.staticAbstractOOMethods);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.staticAbstractOOFields = parse("src/test/resources/tutorial/simplejava/invalid/StaticAbstractOOFields.java");
    stc.createFromAST(this.staticAbstractOOFields);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.check = parse("src/test/resources/tutorial/simplejavawithautomata/Check.jla");
    stc.createFromAST(this.check);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.invalidAutomaton = parse("src/test/resources/tutorial/simplejavawithautomata/invalid/InvalidAutomaton.jla");
    stc.createFromAST(this.invalidAutomaton);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.wrongAssignment = parse("src/test/resources/tutorial/simplejava/invalid/WrongAssignment.java");
    stc.createFromAST(this.wrongAssignment);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.complicatedWrongAssignment = parse("src/test/resources/tutorial/simplejava/invalid/ComplicatedWrongAssignment.java");
    stc.createFromAST(this.complicatedWrongAssignment);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.complicatedCorrectAssignment = parse("src/test/resources/tutorial/simplejava/valid/ComplicatedCorrectAssignment.java");
    stc.createFromAST(this.complicatedCorrectAssignment);

    stc = new SimpleJavaWithAutomataPhasedSTC();
    this.inheritedCannotUseStaticFromSuper = parse("src/test/resources/tutorial/simplejava/invalid/InheritedCannotUseStaticFromSuper.java");
    stc.createFromAST(this.inheritedCannotUseStaticFromSuper);
  }

  @Test
  public void testValidCheckOOAndAbstract(){
    testValidOO(check);
  }

  @Test
  public void testValidBarOOAndAbstract(){
    testValidOO(bar);
  }

  @Test
  public void testValidInheritanceBarOOAndAbstract(){
    testValidOO(inheritanceBar);
  }

  @Test
  @Ignore //TODO Exercise 2 
  public void testStaticAbstractOOFields(){
    testInvalidOO("0xFD118", staticAbstractOOFields);
  }

//  Due to changes to the TypeCheck not tested
//  @Test
//  public void testStaticAbstractOOMethods(){
//    testInvalidOO("0xA2239", staticAbstractOOMethods);
//  }

  //  Due to changes to the TypeCheck not tested
//  @Test
//  public void testInheritedCannotUseStaticFromSuper(){
//    testInvalidOO("0xA2239",inheritedCannotUseStaticFromSuper);
//  }

  @Test
  public void testComplicatedCorrectAssignment(){
    testValidOO(complicatedCorrectAssignment);
  }

  @Test
  @Ignore //TODO Exercise 2 
  public void testComplicatedWrongAssignment(){
    testInvalidOO("0xB0163", complicatedWrongAssignment);
  }

  @Test
  @Ignore //TODO Exercise 2 
  public void testWrongAssignment(){
    testInvalidOO("0xA0457", wrongAssignment);
  }

  @Test
  @Ignore //TODO Exercise 2 
  public void testInvalidAutomaton(){
    testInvalidOO("0xA005", invalidAutomaton);
  }

  protected void testInvalidOO(String errorCode, ASTJavaCompilationUnit comp){
    Log.clearFindings();
    SimpleJavaWithAutomataCoCoChecker checker = getOOChecker();
    try{
      checker.checkAll(comp);
    }catch(Exception e){
      //do nothing here, just catch the exception for further testing
    }
    assertTrue("Expected finding, but found none!", Log.getFindingsCount()>=1);
    assertTrue(Log.getFindings().stream().map(Finding::toString).collect(Collectors.joining(System.lineSeparator())),
               Log.getFindings().get(0).getMsg().startsWith(errorCode));
  }

  protected void testValidOO(ASTJavaCompilationUnit comp){
    Log.clearFindings();
    SimpleJavaWithAutomataCoCoChecker checker = getOOChecker();
    checker.checkAll(comp);
    assertEquals(0, Log.getFindingsCount());
  }

  protected SimpleJavaWithAutomataCoCoChecker getOOChecker(){
    return new SimpleJavaWithAutomataCoCos().getCoCoChecker();
  }

}

