/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import de.se_rwth.commons.logging.Finding;
import tutorial.website._ast.ASTWebsite;
import tutorial.website._cocos.WebsiteCoCoChecker;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import tutorial.website.cocos.*;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoCoTest extends AbstractTest {

  @Before
  public void setup(){
    WebsiteMill.globalScope().clear();
    Log.clearFindings();
    Log.enableFailQuick(false);
  }

  @Test
  public void testSERWTH() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/SERWTH.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testDream() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/Dream.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testSinglePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/SinglePage.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testLinkTitleEmpty() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/LinkTitleEmpty.web");
    checkInvalid(website, LinkTitleNotEmpty.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testMultipleStartPages() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/MultipleStartPages.web");
    checkInvalid(website, ExactlyOneStartPage.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testNavigationReferencesInaccessiblePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/NavigationReferencesInaccessiblePage.web");
    checkInvalid(website, NavigationItemPageExists.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testPageNameLowerCase() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameLowerCase.web");
    checkInvalid(website, PageNameStartUpperCase.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testPageNameNotUnique() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameNotUnique.web");
    checkInvalid(website, PageNameIsUnique.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  public void testWebsiteNameLowerCase() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/WebsiteNameLowerCase.web");
    checkInvalid(website, WebsiteNameStartUpperCase.errorCode);
  }

  public void checkValid(ASTWebsite node) {
    WebsiteCoCoChecker checker = new WebsiteCoCos().createChecker();
    checker.checkAll(node);
    assertTrue(Log.getFindings().isEmpty());
  }

  public void checkInvalid(ASTWebsite node, String errorCode) {
    WebsiteCoCoChecker checker = new WebsiteCoCos().createChecker();
    checker.checkAll(node);
    assertFalse(Log.getFindings().isEmpty());
    assertTrue(Log.getFindings().stream().map(Finding::getMsg).collect(Collectors.joining(System.lineSeparator())),
               Log.getFindings().get(0).getMsg().startsWith(errorCode));
  }
}
