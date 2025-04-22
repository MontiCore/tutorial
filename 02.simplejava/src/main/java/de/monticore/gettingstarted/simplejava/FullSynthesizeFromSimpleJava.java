/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava;

import de.monticore.gettingstarted.simplejava._visitor.SimpleJavaTraverser;
import de.monticore.types.check.AbstractSynthesize;
//<#if solution>
import de.monticore.types.check.SynthesizeSymTypeFromMCBasicTypes;
import de.monticore.types.check.SynthesizeSymTypeFromMCCollectionTypes;
import de.monticore.types.check.SynthesizeSymTypeFromMCSimpleGenericTypes;
//</#if>

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
    //<#if solution>
    SynthesizeSymTypeFromMCBasicTypes basicTypes = new SynthesizeSymTypeFromMCBasicTypes();
    SynthesizeSymTypeFromMCCollectionTypes collectionTypes = new SynthesizeSymTypeFromMCCollectionTypes();
    SynthesizeSymTypeFromMCSimpleGenericTypes simpleGenericTypes = new SynthesizeSymTypeFromMCSimpleGenericTypes();
    basicTypes.setTypeCheckResult(typeCheckResult);
    collectionTypes.setTypeCheckResult(typeCheckResult);
    simpleGenericTypes.setTypeCheckResult(typeCheckResult);

    traverser.setMCBasicTypesHandler(basicTypes);
    traverser.add4MCBasicTypes(basicTypes);
    traverser.setMCCollectionTypesHandler(collectionTypes);
    traverser.add4MCCollectionTypes(collectionTypes);
    traverser.setMCSimpleGenericTypesHandler(simpleGenericTypes);
    traverser.add4MCSimpleGenericTypes(simpleGenericTypes);
    //</#if>
  }
}
