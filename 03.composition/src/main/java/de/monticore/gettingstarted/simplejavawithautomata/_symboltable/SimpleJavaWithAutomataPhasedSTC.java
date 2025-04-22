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
  }

  public ISimpleJavaWithAutomataArtifactScope createFromAST(ASTJavaCompilationUnit ast){
    //TODO implement me!
    return null;
  }

}

