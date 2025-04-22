/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava._symboltable;

//<#if solution>
import com.google.common.collect.Lists;
import de.monticore.gettingstarted.simplejava.FullDeriveFromSimpleJava;
import de.monticore.gettingstarted.simplejava.FullSynthesizeFromSimpleJava;
import de.monticore.gettingstarted.simplejava.SimpleJavaMill;
import de.monticore.types.check.TypeCalculator;
//</#if>
import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;

import java.util.List;

public class SimpleJavaPhasedSTC {

  protected List<SimpleJavaTraverser> priorityList;

  protected SimpleJavaScopesGenitorDelegator scopesGenitorDelegator;

  public SimpleJavaPhasedSTC(){
    //TODO implement me!
    //<#if solution>
    this.scopesGenitorDelegator = SimpleJavaMill.scopesGenitorDelegator();
    this.priorityList = Lists.newArrayList();

    TypeCalculator tc = new TypeCalculator(new FullSynthesizeFromSimpleJava(), new FullDeriveFromSimpleJava());
    SimpleJavaTraverser traverser = SimpleJavaMill.traverser();
    traverser.add4SimpleJava(new SimpleJavaSTCompleteTypes(tc));
    priorityList.add(traverser);
    //</#if>
  }

  public ISimpleJavaArtifactScope createFromAST(ASTJavaCompilationUnit ast){
    //TODO implement me!
    //<#if solution>
    ISimpleJavaArtifactScope as = scopesGenitorDelegator.createFromAST(ast);
    priorityList.forEach(ast::accept);
    return as;
    //</#if>
    //<#if !solution>return null;//</#if>
  }

}
