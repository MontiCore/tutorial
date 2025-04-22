/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.types.check.AbstractSynthesize;

@Deprecated(forRemoval = true)
public class FullSynthesizeFromSimpleJava extends AbstractSynthesize {

  public FullSynthesizeFromSimpleJava(){
    this(SimpleJavaMill.traverser());
  }

  public FullSynthesizeFromSimpleJava(SimpleJavaTraverser traverser) {
    super(traverser);
    init(traverser);
  }

  public void init(SimpleJavaTraverser traverser) {
    //TODO implement me!
  }
}
