/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava._symboltable;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;

import java.util.List;

public class SimpleJavaPhasedSTC {

  protected List<SimpleJavaTraverser> priorityList;

  protected SimpleJavaScopesGenitorDelegator scopesGenitorDelegator;

  public SimpleJavaPhasedSTC(){
    //TODO implement me!
  }

  public ISimpleJavaArtifactScope createFromAST(ASTJavaCompilationUnit ast){
    //TODO implement me!
    return null;
  }

}
