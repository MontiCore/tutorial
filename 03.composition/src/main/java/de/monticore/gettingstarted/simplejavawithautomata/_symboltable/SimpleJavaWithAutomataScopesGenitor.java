/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata._symboltable;

import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;

public class SimpleJavaWithAutomataScopesGenitor extends SimpleJavaWithAutomataScopesGenitorTOP {

  public SimpleJavaWithAutomataScopesGenitor(){
    super();
  }

  @Override
  public ISimpleJavaWithAutomataArtifactScope createFromAST(ASTJavaCompilationUnit ast){
    //TODO implement me!
    //<#if solution>
    ISimpleJavaWithAutomataArtifactScope as = super.createFromAST(ast);
    as.setPackageName(ast.getMCPackageDeclaration().getMCQualifiedName().getQName());
    return as;
    //</#if>
    //<#if !solution>return null;//</#if>
  }

}
