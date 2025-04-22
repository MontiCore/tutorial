/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava._symboltable;

import com.google.common.collect.Lists;
import de.monticore.gettingstarted.simplejava._ast.ASTJavaArtifact;
import de.monticore.gettingstarted.simplejava._ast.ASTJavaMethod;
import de.monticore.gettingstarted.simplejava._ast.ASTJavaVarDecl;
import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaVisitor2;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.symbols.basicsymbols._symboltable.VariableSymbol;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.monticore.types.check.TypeCalculator;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleJavaSTCompleteTypes implements SimpleJavaVisitor2 {


  @Override
  public void visit(ASTJavaMethod node) {
    //TODO implement me!
    //<#if solution>
    JavaMethodSymbol symbol = node.getSymbol();
    SymTypeExpression symType = TypeCheck3.symTypeFromAST(node.getMCReturnType());
    symbol.setType(symType);
    replaceSurrogate(symbol);
    //</#if>
  }

  @Override
  public void visit(ASTJavaVarDecl node) {
    //TODO implement me!
    //<#if solution>
    JavaVarDeclSymbol symbol = node.getSymbol();
    SymTypeExpression symType = TypeCheck3.symTypeFromAST(node.getMCType());
    symbol.setType(symType);
    replaceSurrogate(symbol);
    //</#if>
  }

  @Override
  public void visit(ASTJavaArtifact node) {
    //TODO implement me!
    //<#if solution>
    JavaArtifactSymbol symbol = node.getSymbol();
    if(node.isPresentSuperType()) {
      SymTypeExpression superType = TypeCheck3.symTypeFromAST(node.getSuperType());
      symbol.setSuperTypesList(Lists.newArrayList(superType));
    }
    replaceSurrogates(symbol);
    //</#if>
  }

  public void replaceSurrogates(TypeSymbol type){
    List<SymTypeExpression> superTypes = new ArrayList<>();
    for(TypeSymbol superType: type.streamSuperTypes().map(SymTypeExpression::getTypeInfo).collect(Collectors.toList())) {
      Optional<TypeSymbol> ts = superType.getEnclosingScope().resolveType(superType.getName());
      if (ts.isPresent()) {
        superTypes.add(SymTypeExpressionFactory.createTypeExpression(ts.get()));
      } else {
        Log.error("Could not find the type " + superType.getName());
        superTypes.add(SymTypeExpressionFactory.createTypeExpression(superType));
      }
    }
    type.setSuperTypesList(superTypes);
  }

  public void replaceSurrogate(VariableSymbol variable){
    TypeSymbol type = variable.getType().getTypeInfo();
    Optional<TypeSymbol> ts = type.getEnclosingScope().resolveType(type.getName());
    if(ts.isPresent()){
      variable.setType(SymTypeExpressionFactory.createTypeExpression(ts.get()));
    } else {
      Log.error("Could not find the type " + type.getName());
    }
  }

  public void replaceSurrogate(FunctionSymbol function){
    if(!function.getType().isVoidType()) {
      TypeSymbol type = function.getType().getTypeInfo();
      Optional<TypeSymbol> ts = type.getEnclosingScope().resolveType(type.getName());
      if (ts.isPresent()) {
        function.setType(SymTypeExpressionFactory.createTypeExpression(ts.get()));
      } else {
        Log.error("Could not find the type " + type.getName());
      }
    }
  }
}
