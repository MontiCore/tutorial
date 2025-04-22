/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import de.se_rwth.commons.logging.Finding;
import tutorial.website._ast.ASTWebsite;
import tutorial.website._symboltable.WebsiteScopesGenitorDelegator;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class AbstractTest {

  @Before
  public void setup(){
    Log.clearFindings();
  }

  public ASTWebsite parse(String model) throws IOException {
    Optional<ASTWebsite> website = WebsiteMill.parser().parse(model);
    assertTrue(Log.getFindings().stream().map(Finding::toString).collect(Collectors.joining(System.lineSeparator())),
               website.isPresent());

    WebsiteScopesGenitorDelegator scopesGenitorDelegator = WebsiteMill.scopesGenitorDelegator();
    scopesGenitorDelegator.createFromAST(website.get());
    return website.get();
  }

}
