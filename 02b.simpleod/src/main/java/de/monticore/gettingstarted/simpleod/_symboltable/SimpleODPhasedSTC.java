/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod._symboltable;

import de.monticore.gettingstarted.simpleod._ast.ASTODCompilationUnit;
import de.monticore.gettingstarted.simpleod._visitor.SimpleODTraverser;

import java.util.List;

public class SimpleODPhasedSTC {

  protected List<SimpleODTraverser> priorityList;

  protected SimpleODScopesGenitorDelegator scopesGenitorDelegator;

  public SimpleODPhasedSTC(){
    //TODO implement me!
  }

  public ISimpleODArtifactScope createFromAST(ASTODCompilationUnit ast){
    //TODO implement me!
    return null;
  }

}
