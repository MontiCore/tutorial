package de.monticore.gettingstarted.montiarc._symboltable;


import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;

import java.util.List;

public class ComponentSymbolDeSer extends ComponentSymbolDeSerTOP {
  public ComponentSymbolDeSer() {
  }

  @Override
  protected void serializeSuperTypes(List<SymTypeExpression> superTypes, MontiArcSymbols2Json s2j) {

  }

  @Override
  protected List<SymTypeExpression> deserializeSuperTypes(JsonObject symbolJson) {
    return null;
  }
}
