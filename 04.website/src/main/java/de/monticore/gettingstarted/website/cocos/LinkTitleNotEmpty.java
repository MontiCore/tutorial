/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTLink;
import de.monticore.gettingstarted.website._cocos.WebsiteASTLinkCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class LinkTitleNotEmpty  /*<#if solution>*/ implements WebsiteASTLinkCoCo /*</#if>*/  {

  public static final String errorCode = "0xB006";

  public static final String errorMsg = " The link to %s cannot have an empty title";

  //<#if solution>
  @Override
  public void check(ASTLink node) {
    if(node.isPresentTitle() && node.getTitle().getSource().isEmpty()){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }
  //</#if>
}
