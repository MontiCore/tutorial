/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import de.se_rwth.commons.logging.Finding;
import org.junit.jupiter.api.Assertions;
import tutorial.website._ast.ASTWebsite;
import tutorial.website._symboltable.WebsiteScopesGenitorDelegator;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;


class AbstractTest {

  @BeforeEach
  public void setup(){
    Log.clearFindings();
  }

  public ASTWebsite parse(String model) throws IOException {
    Optional<ASTWebsite> website = WebsiteMill.parser().parse(model);
    Assertions.assertTrue(website.isPresent(),
                          Log.getFindings().stream().map(Finding::toString).collect(
                                  Collectors.joining(System.lineSeparator())));

    WebsiteScopesGenitorDelegator scopesGenitorDelegator = WebsiteMill.scopesGenitorDelegator();
    scopesGenitorDelegator.createFromAST(website.get());
    return website.get();
  }

}
