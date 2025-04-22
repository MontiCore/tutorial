/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simpleod._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;

public class ODNamedObjectSymbolDeSer extends ODNamedObjectSymbolDeSerTOP {
  @Override
  protected void serializeType(SymTypeExpression type, SimpleODSymbols2Json s2j) {
    //TODO implement me!
  }

  @Override
  protected SymTypeExpression deserializeType(JsonObject symbolJson) {
    return null; //TODO implement me!
  }
}
