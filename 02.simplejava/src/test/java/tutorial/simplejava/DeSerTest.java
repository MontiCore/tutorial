/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava;

import org.junit.jupiter.api.*;
import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._symboltable.ISimpleJavaArtifactScope;
import tutorial.simplejava._symboltable.SimpleJavaPhasedSTC;
import tutorial.simplejava._symboltable.SimpleJavaSymbols2Json;
import tutorial.simplejava.types3.SimpleJavaTypeCheck3;
import de.monticore.io.paths.MCPath;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Paths;

class DeSerTest extends AbstractTest {

  @BeforeEach
  public void setup() {
    SimpleJavaMill.globalScope().clear();
    SimpleJavaMill.init();
    SimpleJavaTypeCheck3.init();
    BasicSymbolsMill.initializePrimitives();
    TypeSymbol string = SimpleJavaMill
      .typeSymbolBuilder()
      .setName("String")
      .setEnclosingScope(SimpleJavaMill.globalScope())
      .build();
    string.setSpannedScope(SimpleJavaMill.scope());
    SimpleJavaMill.globalScope().add(string);
    SimpleJavaMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources")));
  }

  @Test
  void testSerialization() throws IOException {
    //TODO implement me!
  }

  @Test
  void testDeserialization(){
    //TODO implement me!
    String symbolTableFile = "src/test/resources/tutorial/simplejava/symboltable/Check.javasym";
  }

}
