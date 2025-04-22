/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.website.cocos;

//<#if solution>
import de.monticore.gettingstarted.website._ast.ASTPage;
import de.monticore.gettingstarted.website._cocos.WebsiteASTPageCoCo;
import de.se_rwth.commons.logging.Log;
//</#if>
import java.util.ArrayList;
import java.util.List;

public class PageNameIsUnique  /*<#if solution>*/ implements WebsiteASTPageCoCo /*</#if>*/  {

  public static final String errorCode = "0xB005";

  public static final String errorMsg = " The page name %s is not unique";

  protected List<String> pageNames = new ArrayList<>();

  //<#if solution>
  @Override
  public void check(ASTPage node) {
    if(pageNames.stream().filter(p -> p.equals(node.getName())).count() == 1){
      Log.error(errorCode + String.format(errorMsg, node.getName()));
    }
    pageNames.add(node.getName());
  }
  //</#if>
}
