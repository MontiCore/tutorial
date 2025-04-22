/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTNavigationElement;
import de.monticore.gettingstarted.website._cocos.WebsiteASTNavigationElementCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class NavigationItemPageExists  /*<#if solution>*/ implements WebsiteASTNavigationElementCoCo /*</#if>*/  {

  public static final String errorCode = "0xB002";

  public static final String errorMsg = " The navigation item %s must refer to a page";

  //<#if solution>
  @Override
  public void check(ASTNavigationElement node) {
    if(node.getEnclosingScope().resolvePage(node.getName()).isEmpty()){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }
  //</#if>

}
