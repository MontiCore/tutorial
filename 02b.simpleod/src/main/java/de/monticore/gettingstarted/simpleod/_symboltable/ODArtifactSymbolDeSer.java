/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;

import java.util.List;

public class ODArtifactSymbolDeSer extends ODArtifactSymbolDeSerTOP {
  @Override
  protected void serializeSuperTypes(List<SymTypeExpression> superTypes, SimpleODSymbols2Json s2j) {
    //TODO implement me!
  }

  @Override
  protected List<SymTypeExpression> deserializeSuperTypes(JsonObject symbolJson) {
    return null;  //TODO implement me!
  }
}
