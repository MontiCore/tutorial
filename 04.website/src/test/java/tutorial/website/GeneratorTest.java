/* (c) https://github.com/MontiCore/monticore */
package tutorial.website;

import tutorial.website._ast.ASTWebsite;
import tutorial.website._visitor.WebsiteTraverser;
import tutorial.website._visitor.WebsiteVisitor2;
import org.junit.Test;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class GeneratorTest extends AbstractTest {

  @Test
  @Ignore
  public void testSERWTH() throws IOException {
    ASTWebsite ast = parse("src/test/resources/tutorial/website/valid/SERWTH.web");
    final Path outputDirectory = Paths.get("target/website/");
    executeGenerator(ast, outputDirectory);
  }

  @Test
  @Ignore
  public void testDream() throws IOException {
    ASTWebsite ast = parse("src/test/resources/tutorial/website/valid/Dream.web");
    final Path outputDirectory = Paths.get("target/website/");
    executeGenerator(ast, outputDirectory);
  }

  @Test
  @Ignore
  public void testSinglePage() throws IOException {
    ASTWebsite ast = parse("src/test/resources/tutorial/website/valid/SinglePage.web");
    final Path outputDirectory = Paths.get("target/website/");
    executeGenerator(ast, outputDirectory);
  }

  public void executeGenerator(ASTWebsite ast, Path outputDirectory){
    // TODO comment in the test and fix the AST-imports
    final Path expectedFolder = Paths
      .get(outputDirectory + "/" + ast.getName().toLowerCase());

    WebsiteGenerator.generate(ast, outputDirectory.toFile());

    // check that for each page in the model a html file is created within the
    // outputDirectory. Therefore, we can use a visitor.
    WebsiteVisitor2 visitor = new WebsiteVisitor2() {
      public void visit(tutorial.website._ast.ASTPage node) {
        File expectedFile = Paths.get(expectedFolder + "/" + node.getName() + ".html").toFile();
        assertTrue(
          String.format("Expected generated file '%s' to exist", expectedFile.getAbsolutePath()),
          expectedFile.exists());
      }
    };

    // run the checking visitor
    WebsiteTraverser traverser = WebsiteMill.traverser();
    traverser.add4Website(visitor);
    ast.accept(traverser);
  }

}
