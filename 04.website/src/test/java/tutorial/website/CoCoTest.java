/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import de.se_rwth.commons.logging.Finding;
import org.junit.jupiter.api.Assertions;
import tutorial.website._ast.ASTWebsite;
import tutorial.website._cocos.WebsiteCoCoChecker;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorial.website.cocos.*;

import java.io.IOException;
import java.util.stream.Collectors;

class CoCoTest extends AbstractTest {

  @BeforeEach
  public void setup(){
    WebsiteMill.globalScope().clear();
    Log.clearFindings();
    Log.enableFailQuick(false);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testSERWTH() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/SERWTH.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testDream() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/Dream.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testSinglePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/SinglePage.web");
    checkValid(website);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testLinkTitleEmpty() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/LinkTitleEmpty.web");
    checkInvalid(website, LinkTitleNotEmpty.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testMultipleStartPages() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/MultipleStartPages.web");
    checkInvalid(website, ExactlyOneStartPage.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testNavigationReferencesInaccessiblePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/NavigationReferencesInaccessiblePage.web");
    checkInvalid(website, NavigationItemPageExists.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testPageNameLowerCase() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameLowerCase.web");
    checkInvalid(website, PageNameStartUpperCase.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testPageNameNotUnique() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameNotUnique.web");
    checkInvalid(website, PageNameIsUnique.errorCode);
  }

  @Test
  @Ignore //TODO: Exercise 2 
  void testWebsiteNameLowerCase() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/WebsiteNameLowerCase.web");
    checkInvalid(website, WebsiteNameStartUpperCase.errorCode);
  }

  public void checkValid(ASTWebsite node) {
    WebsiteCoCoChecker checker = new WebsiteCoCos().createChecker();
    checker.checkAll(node);
    Assertions.assertTrue(Log.getFindings().isEmpty());
  }

  public void checkInvalid(ASTWebsite node, String errorCode) {
    WebsiteCoCoChecker checker = new WebsiteCoCos().createChecker();
    checker.checkAll(node);
    Assertions.assertFalse(Log.getFindings().isEmpty());
    Assertions.assertTrue(Log.getFindings().get(0).getMsg().startsWith(errorCode),
                          Log.getFindings().stream().map(Finding::getMsg).collect(
                                  Collectors.joining(System.lineSeparator())));
  }
}
