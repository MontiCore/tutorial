/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._cocos.SimpleJavaCoCoChecker;
import tutorial.simplejava._symboltable.SimpleJavaPhasedSTC;
import tutorial.simplejava.cocos.SimpleJavaCoCos;
import tutorial.simplejava.types3.SimpleJavaTypeCheck3;
import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

  @BeforeClass
  public static void init(){
    LogStub.init();
    LogStub.enableFailQuick(false);
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
  }

  @Before
  public void setup() throws IOException {
    Log.clearFindings();
    SimpleJavaMill.globalScope().clear();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleJavaMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleJavaMill.globalScope())
      .build();
    string.setSpannedScope(SimpleJavaMill.scope());
    SimpleJavaMill.globalScope().add(string);
    SimpleJavaMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
    SimpleJavaPhasedSTC stc = new SimpleJavaPhasedSTC();

    this.bar = parse("src/test/resources/tutorial/simplejava/valid/Bar.sjava");
    stc.createFromAST(this.bar);

    stc = new SimpleJavaPhasedSTC();
    this.inheritanceBar = parse("src/test/resources/tutorial/simplejava/valid/InheritanceBar.sjava");
    stc.createFromAST(this.inheritanceBar);

    stc = new SimpleJavaPhasedSTC();
    this.staticAbstractOOMethods = parse("src/test/resources/tutorial/simplejava/invalid/StaticAbstractOOMethods.sjava");
    stc.createFromAST(this.staticAbstractOOMethods);

    stc = new SimpleJavaPhasedSTC();
    this.staticAbstractOOFields = parse("src/test/resources/tutorial/simplejava/invalid/StaticAbstractOOFields.sjava");
    stc.createFromAST(this.staticAbstractOOFields);

    stc = new SimpleJavaPhasedSTC();
    this.check = parse("src/test/resources/tutorial/simplejava/valid/Check.sjava");
    stc.createFromAST(this.check);

    stc = new SimpleJavaPhasedSTC();
    this.wrongAssignment = parse("src/test/resources/tutorial/simplejava/invalid/WrongAssignment.sjava");
    stc.createFromAST(this.wrongAssignment);

    stc = new SimpleJavaPhasedSTC();
    this.complicatedWrongAssignment = parse("src/test/resources/tutorial/simplejava/invalid/ComplicatedWrongAssignment.sjava");
    stc.createFromAST(this.complicatedWrongAssignment);

    stc = new SimpleJavaPhasedSTC();
    this.complicatedCorrectAssignment = parse("src/test/resources/tutorial/simplejava/valid/ComplicatedCorrectAssignment.sjava");
    stc.createFromAST(this.complicatedCorrectAssignment);

    stc = new SimpleJavaPhasedSTC();
    this.inheritedCannotUseStaticFromSuper = parse("src/test/resources/tutorial/simplejava/invalid/InheritedCannotUseStaticFromSuper.sjava");
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
  @Ignore // Broken?
  public void testStaticAbstractOOMethods(){
    testInvalidOO("0xA2239", staticAbstractOOMethods);
  }

  @Test
  @Ignore
  public void testStaticAbstractOOFields(){
    testInvalidOO("0xFD118", staticAbstractOOFields);
  }

  @Test
  @Ignore // Broken?
  public void testInheritedCannotUseStaticFromSuper(){
    testInvalidOO("0xA2239", inheritedCannotUseStaticFromSuper);
  }

  @Test
  public void testComplicatedCorrectAssignment(){
    testValidOO(complicatedCorrectAssignment);
  }

  @Test
  @Ignore
  public void testComplicatedWrongAssignment(){
    testInvalidOO("0xB0163", complicatedWrongAssignment);
  }

  @Test
  @Ignore
  public void testWrongAssignment(){
    testInvalidOO("0xA0457", wrongAssignment);
  }

  protected void testInvalidOO(String errorCode, ASTJavaCompilationUnit comp){
    Log.clearFindings();
    SimpleJavaCoCoChecker checker = getOOChecker();
    try{
      checker.checkAll(comp);
    }catch(Exception e){
      //do nothing here, just catch the exception for further testing
    }
    assertTrue("Expected a finding, but found none!", Log.getFindingsCount()>=1);
    assertTrue(Log.getFindings().stream().map(Finding::toString).collect(Collectors.joining(System.lineSeparator())),
               Log.getFindings().get(0).getMsg().startsWith(errorCode));
  }

  protected void testValidOO(ASTJavaCompilationUnit comp){
    Log.clearFindings();
    SimpleJavaCoCoChecker checker = getOOChecker();
    checker.checkAll(comp);
    assertEquals(0, Log.getFindingsCount());
  }

  protected SimpleJavaCoCoChecker getOOChecker(){
    return new SimpleJavaCoCos().getCoCoChecker();
  }

}
