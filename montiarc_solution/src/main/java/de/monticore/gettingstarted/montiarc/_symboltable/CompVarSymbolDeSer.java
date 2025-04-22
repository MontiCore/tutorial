package de.monticore.gettingstarted.montiarc._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;

public class CompVarSymbolDeSer extends CompVarSymbolDeSerTOP {
  public CompVarSymbolDeSer() {
  }

  @Override
  protected void serializeType(SymTypeExpression type, MontiArcSymbols2Json s2j) {

  }

  @Override
  protected SymTypeExpression deserializeType(JsonObject symbolJson) {
    return null;
  }
}
