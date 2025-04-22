/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTPage;
import de.monticore.gettingstarted.website._ast.ASTWebsite;
import de.monticore.gettingstarted.website._cocos.WebsiteASTWebsiteCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class ExactlyOneStartPage /*<#if solution>*/ implements WebsiteASTWebsiteCoCo /*</#if>*/ {

  public static final String errorCode = "0xB003";

  public static final String errorMsg = " There must be exactly one start page";

  //<#if solution>
  @Override
  public void check(ASTWebsite node) {
    if(node.getPageList().stream().filter(ASTPage::isStart).count() != 1){
      Log.error(errorCode +errorMsg);
    }
  }
  //</#if>

}
