/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejavawithautomata.types3;

import de.monticore.gettingstarted.simplejava.types3.SimpleJavaTypeCheck3;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.visitor.ITraverser;

public class SimpleJavaWithAutomataTypeCheck3 extends SimpleJavaTypeCheck3 {

  public SimpleJavaWithAutomataTypeCheck3(ITraverser typeTraverser,
                                          Type4Ast type4Ast,
                                          InferenceContext4Ast ctx4Ast) {
    super(typeTraverser, type4Ast, ctx4Ast);
  }

  // If needed, the init method could be overridden here
}
