/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod;

import de.monticore.gettingstarted.simpleod.SimpleODMill;
import de.monticore.gettingstarted.simpleod._ast.ASTODCompilationUnit;
import de.monticore.gettingstarted.simpleod._symboltable.ISimpleODArtifactScope;
import de.monticore.gettingstarted.simpleod._symboltable.SimpleODPhasedSTC;
import de.se_rwth.commons.logging.Log;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  public ASTODCompilationUnit parse(String model) throws IOException {
    Optional<ASTODCompilationUnit> clazz = SimpleODMill.parser().parse(model);
    Log.getFindings().forEach(System.err::println);
    assertTrue(clazz.isPresent());
    return clazz.get();
  }

  public ISimpleODArtifactScope createSymbolTable(ASTODCompilationUnit clazz){
    SimpleODPhasedSTC stc = new SimpleODPhasedSTC();
    return stc.createFromAST(clazz);
  }

}
