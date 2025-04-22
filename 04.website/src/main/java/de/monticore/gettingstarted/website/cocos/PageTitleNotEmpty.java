/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTPage;
import de.monticore.gettingstarted.website._cocos.WebsiteASTPageCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>

public class PageTitleNotEmpty  /*<#if solution>*/ implements WebsiteASTPageCoCo /*</#if>*/ {

  public static final String errorCode = "0xB008";

  public static final String errorMsg = " The page %s cannot have an empty title";

  //<#if solution>
  @Override
  public void check(ASTPage node) {
    if(node.isPresentTitle() && node.getTitle().getStringLiteral().getSource().isEmpty()){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
  }
  //</#if>

}
