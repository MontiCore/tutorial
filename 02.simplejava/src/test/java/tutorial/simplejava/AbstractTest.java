/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._symboltable.ISimpleJavaArtifactScope;
import tutorial.simplejava._symboltable.SimpleJavaPhasedSTC;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  public ASTJavaCompilationUnit parse(String model) throws IOException {
    Optional<ASTJavaCompilationUnit> clazz = SimpleJavaMill.parser().parse(model);
    assertTrue(clazz.isPresent());
    return clazz.get();
  }

  public ISimpleJavaArtifactScope createSymbolTable(ASTJavaCompilationUnit clazz){
    SimpleJavaPhasedSTC stc = new SimpleJavaPhasedSTC();
    return stc.createFromAST(clazz);
  }

}
