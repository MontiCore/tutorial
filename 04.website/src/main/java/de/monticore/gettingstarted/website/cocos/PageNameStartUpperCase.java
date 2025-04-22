/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTPage;
import de.monticore.gettingstarted.website._cocos.WebsiteASTPageCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class PageNameStartUpperCase  /*<#if solution>*/ implements WebsiteASTPageCoCo /*</#if>*/ {

  public static final String errorCode = "0xB004";

  public static final String errorMsg = " The name of the page %s must be capitalized";

  //<#if solution>
  @Override
  public void check(ASTPage node) {
    if(!Character.isUpperCase(node.getName().charAt(0))){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }
  //</#if>
}
