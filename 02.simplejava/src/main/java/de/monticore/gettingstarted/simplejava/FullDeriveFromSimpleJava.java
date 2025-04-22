/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.types.check.*;

public class FullDeriveFromSimpleJava extends AbstractDerive {

  public FullDeriveFromSimpleJava(){
    this(SimpleJavaMill.traverser());
  }

  public FullDeriveFromSimpleJava(SimpleJavaTraverser traverser){
    super(traverser);
    init(traverser);
  }

  public void init(SimpleJavaTraverser traverser) {
    //TODO implement me!
  }

}
