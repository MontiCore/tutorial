/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava._symboltable;

import tutorial.simplejava._ast.ASTJavaCompilationUnit;
import tutorial.simplejava._visitor.SimpleJavaTraverser;

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
