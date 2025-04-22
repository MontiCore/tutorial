/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTWebsite;
import de.monticore.gettingstarted.website._cocos.WebsiteASTWebsiteCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class WebsiteNameStartUpperCase  /*<#if solution>*/ implements WebsiteASTWebsiteCoCo /*</#if>*/ {
  
  public static final String errorCode = "0xB001";

  public static final String errorMsg = " The name of the website %s must be capitalized";

  //<#if solution>
  @Override
  public void check(ASTWebsite node) {
    if(!Character.isUpperCase(node.getName().charAt(0))){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }
  //</#if>
}
