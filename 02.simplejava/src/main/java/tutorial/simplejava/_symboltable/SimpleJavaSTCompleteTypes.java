/* (c) https://github.com/MontiCore/monticore */
package tutorial.simplejava._symboltable;

import com.google.common.collect.Lists;
import de.monticore.symbols.oosymbols._symboltable.FieldSymbol;
import de.monticore.symbols.oosymbols._symboltable.MethodSymbol;
import de.monticore.symbols.oosymbols._symboltable.OOTypeSymbol;
import tutorial.simplejava._ast.ASTFormalParameter;
import tutorial.simplejava._ast.ASTJavaArtifact;
import tutorial.simplejava._ast.ASTJavaMethod;
import tutorial.simplejava._ast.ASTJavaVarDecl;
import tutorial.simplejava._visitor.SimpleJavaVisitor2;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;


public class SimpleJavaSTCompleteTypes implements SimpleJavaVisitor2 {


  @Override
  public void visit(ASTJavaMethod node) {
    MethodSymbol symbol = node.getSymbol();
    SymTypeExpression symType;
    //TODO implement me!

    // Our language does not support visibilities,
    // but the OOSymbols require it
    // Thus, we mark all our symbols as public
    symbol.setIsPublic(true);
  }

  @Override
  public void visit(ASTJavaVarDecl node) {
    //TODO implement me!

    // Our language does not support visibilities,
    // but the OOSymbols require it
    // Thus, we mark all our symbols as public
    node.getSymbol().setIsPublic(true);
  }

  @Override
  public void visit(ASTJavaArtifact node) {
    //TODO implement me!

    // Our language does not support visibilities,
    // but the OOSymbols require it
    // Thus, we mark all our symbols as public
    node.getSymbol().setIsPublic(true);
  }

  @Override
  public void visit(ASTFormalParameter node) {
    //TODO implement me!
  }

}
