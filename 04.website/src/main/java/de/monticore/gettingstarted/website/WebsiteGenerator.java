/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website;

import de.monticore.generating.GeneratorEngine;
import de.monticore.generating.GeneratorSetup;
import de.monticore.gettingstarted.website._ast.ASTPage;
import de.monticore.gettingstarted.website._ast.ASTWebsite;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class WebsiteGenerator {

  private final static String LOGGER_NAME = WebsiteGenerator.class.getName();

  /**
   * Generates the website in ouputDirectory using the ast.
   *
   * @param ast the ast to generate the website for.
   * @param outputDirectory the direectory to generate the website in.
   */
  public static void generate(ASTWebsite ast, File outputDirectory) {
    GeneratorEngine generator = createGeneratorEngine(outputDirectory);
    String websiteName = ast.getName();

    String outputPath = websiteName.toLowerCase() + "/";

    // TODO generate separate html files for each page of the model.
    //<#if solution>
    for(ASTPage page: ast.getPageList()){
      Path outputFile = Paths.get(outputPath, page.getName() + ".html");
      generator.generate("website.Page.ftl", outputFile, page, ast);
    }

    generator = createCSSGeneratorEngine(outputDirectory);

    Path outputFile = Paths.get(outputPath, "main.css");
    generator.generate("website.CSS.ftl", outputFile, ast);
    //</#if>

    Log.trace(LOGGER_NAME, "Generated website " + websiteName
      + " in folder" + outputDirectory.getAbsolutePath());

  }

  private static GeneratorEngine createGeneratorEngine(File outputDirectory) {
    final GeneratorSetup setup = new GeneratorSetup();
    setup.setOutputDirectory(outputDirectory);
    setup.setCommentStart("<!--");
    setup.setCommentEnd("-->");
    return new GeneratorEngine(setup);
  }

  //<#if solution>
  private static GeneratorEngine createCSSGeneratorEngine(File outputDirectory) {
    final GeneratorSetup setup = new GeneratorSetup();
    setup.setOutputDirectory(outputDirectory);
    setup.setCommentStart("/*");
    setup.setCommentEnd("*/");
    return new GeneratorEngine(setup);
  }
  //</#if>


}
