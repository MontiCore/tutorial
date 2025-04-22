/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata._symboltable;

import com.google.common.collect.Lists;
import de.monticore.gettingstarted.simplejava.FullDeriveFromSimpleJava;
import de.monticore.gettingstarted.simplejava.FullSynthesizeFromSimpleJava;
import de.monticore.gettingstarted.simplejava._ast.ASTJavaCompilationUnit;
import de.monticore.gettingstarted.simplejava._symboltable.SimpleJavaSTCompleteTypes;
import de.monticore.gettingstarted.simplejavawithautomata.SimpleJavaWithAutomataMill;
import de.monticore.gettingstarted.simplejavawithautomata._visitor.SimpleJavaWithAutomataTraverser;
import de.monticore.types.check.TypeCalculator;
import de.monticore.types.check.TypeRelations;

import java.util.List;

public class SimpleJavaWithAutomataPhasedSTC {

  protected List<SimpleJavaWithAutomataTraverser> priorityList;

  protected SimpleJavaWithAutomataScopesGenitorDelegator scopesGenitorDelegator;

  public SimpleJavaWithAutomataPhasedSTC(){
    //TODO implement me!
    //<#if solution>
    this.scopesGenitorDelegator = SimpleJavaWithAutomataMill.scopesGenitorDelegator();
    this.priorityList = Lists.newArrayList();

    TypeCalculator tc = new TypeCalculator(new FullSynthesizeFromSimpleJava(), new FullDeriveFromSimpleJava(), new TypeRelations());
    SimpleJavaWithAutomataTraverser traverser = SimpleJavaWithAutomataMill.traverser();
    traverser.add4SimpleJava(new SimpleJavaSTCompleteTypes(tc));
    priorityList.add(traverser);
    //</#if>
  }

  public ISimpleJavaWithAutomataArtifactScope createFromAST(ASTJavaCompilationUnit ast){
    //TODO implement me!
    //<#if solution>
    ISimpleJavaWithAutomataArtifactScope as = scopesGenitorDelegator.createFromAST(ast);
    priorityList.forEach(ast::accept);
    return as;
    //</#if>
    //<#if !solution>return null;//</#if>
  }

}

