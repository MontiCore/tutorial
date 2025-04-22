/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod;

import de.monticore.gettingstarted.simpleod._ast.ASTODCompilationUnit;
import de.monticore.gettingstarted.simpleod._cocos.SimpleODCoCoChecker;
import de.monticore.gettingstarted.simpleod._symboltable.SimpleODPhasedSTC;
import de.monticore.gettingstarted.simpleod.cocos.SimpleODCoCos;
import de.monticore.gettingstarted.simpleod.cocos.VariableDeclarationIsCorrect;
import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoCoTest extends AbstractTest {

  private ASTODCompilationUnit bar;
  private ASTODCompilationUnit wrongAssignment;
  private ASTODCompilationUnit complicatedWrongAssignment;
  private ASTODCompilationUnit complicatedCorrectAssignment;

  @BeforeClass
  public static void init(){
//    LogStub.init();
    Log.init();
    LogStub.enableFailQuick(false);
    SimpleODMill.init();
  }

  @Before
  public void setup() throws IOException {
    Log.clearFindings();
    SimpleODMill.globalScope().clear();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleODMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleODMill.globalScope())
      .build();
    string.setSpannedScope(SimpleODMill.scope());
    SimpleODMill.globalScope().add(string);
    SimpleODMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
    SimpleODPhasedSTC stc = new SimpleODPhasedSTC();

    this.bar = parse("src/test/resources/de/monticore/gettingstarted/simpleod/valid/Bar.sod");
    stc.createFromAST(this.bar);

    stc = new SimpleODPhasedSTC();
    this.wrongAssignment = parse("src/test/resources/de/monticore/gettingstarted/simpleod/invalid/WrongAssignment.sod");
    stc.createFromAST(this.wrongAssignment);

    stc = new SimpleODPhasedSTC();
    this.complicatedWrongAssignment = parse("src/test/resources/de/monticore/gettingstarted/simpleod/invalid/ComplicatedWrongAssignment.sod");
    stc.createFromAST(this.complicatedWrongAssignment);

    stc = new SimpleODPhasedSTC();
    this.complicatedCorrectAssignment = parse("src/test/resources/de/monticore/gettingstarted/simpleod/valid/ComplicatedCorrectAssignment.sod");
    stc.createFromAST(this.complicatedCorrectAssignment);
  }

  @Test
  public void testValidBarOOAndAbstract(){
    testValidOO(bar);
  }

  @Test
  public void testComplicatedCorrectAssignment(){
    testValidOO(complicatedCorrectAssignment);
  }

  @Test
  @Ignore
  public void testComplicatedWrongAssignment(){
    testInvalidOO("0xA0168", complicatedWrongAssignment);
  }

  @Test
  @Ignore
  public void testWrongAssignment(){
    testInvalidOO(VariableDeclarationIsCorrect.errorCode, wrongAssignment);
  }

  protected void testInvalidOO(String errorCode, ASTODCompilationUnit comp){
    Log.clearFindings();
    SimpleODCoCoChecker checker = getOOChecker();
    try{
      checker.checkAll(comp);
    }catch(Exception e){
      //do nothing here, just catch the exception for further testing
    }
    assertTrue(Log.getFindingsCount()>=1);
    assertTrue(Log.getFindings().get(0).getMsg().startsWith(errorCode));
  }

  protected void testValidOO(ASTODCompilationUnit comp){
    Log.clearFindings();
    SimpleODCoCoChecker checker = getOOChecker();
    checker.checkAll(comp);
    assertEquals(0, Log.getFindingsCount());
  }

  protected SimpleODCoCoChecker getOOChecker(){
    return new SimpleODCoCos().getCoCoChecker();
  }

}
