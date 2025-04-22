/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import tutorial.website._ast.ASTWebsite;
import tutorial.website._cocos.WebsiteCoCoChecker;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import tutorial.website.cocos.*;

import java.io.IOException;

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
  @Ignore
  public void testDream() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/Dream.web");
    checkValid(website);
  }

  @Test
  public void testSinglePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/valid/SinglePage.web");
    checkValid(website);
  }

  @Test
  @Ignore
  public void testLinkTitleEmpty() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/LinkTitleEmpty.web");
    checkInvalid(website, LinkTitleNotEmpty.errorCode);
  }

  @Test
  @Ignore
  public void testMultipleStartPages() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/MultipleStartPages.web");
    checkInvalid(website, ExactlyOneStartPage.errorCode);
  }

  @Test
  @Ignore
  public void testNavigationReferencesInaccessiblePage() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/NavigationReferencesInaccessiblePage.web");
    checkInvalid(website, NavigationItemPageExists.errorCode);
  }

  @Test
  @Ignore
  public void testPageNameLowerCase() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameLowerCase.web");
    checkInvalid(website, PageNameStartUpperCase.errorCode);
  }

  @Test
  @Ignore
  public void testPageNameNotUnique() throws IOException {
    ASTWebsite website = parse("src/test/resources/tutorial/website/invalid/PageNameNotUnique.web");
    checkInvalid(website, PageNameIsUnique.errorCode);
  }

  @Test
  @Ignore
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
    assertTrue(Log.getFindings().get(0).getMsg().startsWith(errorCode));
  }
}
