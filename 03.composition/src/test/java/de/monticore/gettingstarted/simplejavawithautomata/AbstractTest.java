/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejavawithautomata._symboltable.ISimpleJavaWithAutomataArtifactScope;
import de.monticore.gettingstarted.simplejavawithautomata._symboltable.SimpleJavaWithAutomataPhasedSTC;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  public ASTJavaCompilationUnit parse(String model) throws IOException {
    Optional<ASTJavaCompilationUnit> clazz = SimpleJavaWithAutomataMill.parser().parse(model);
    assertTrue(clazz.isPresent());
    return clazz.get();
  }

  public ISimpleJavaWithAutomataArtifactScope createSymbolTable(ASTJavaCompilationUnit clazz){
    SimpleJavaWithAutomataPhasedSTC stc = new SimpleJavaWithAutomataPhasedSTC();
    return stc.createFromAST(clazz);
  }

}
