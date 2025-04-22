/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod.types;

import de.monticore.gettingstarted.simpleod.SimpleODMill;
import de.monticore.gettingstarted.simpleod._visitor.SimpleODTraverser;
import de.monticore.types.check.AbstractDerive;

public class FullDeriveFromSimpleOD extends AbstractDerive {

  public FullDeriveFromSimpleOD(){
    this(SimpleODMill.traverser());
  }

  public FullDeriveFromSimpleOD(SimpleODTraverser traverser){
    super(traverser);
    init(traverser);
  }

  public void init(SimpleODTraverser traverser) {
    //TODO implement me!
  }

}
