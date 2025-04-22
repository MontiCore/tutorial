package de.monticore.gettingstarted.montiarc._symboltable;


import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;

public class InstanceSymbolDeSer extends InstanceSymbolDeSerTOP {
  public InstanceSymbolDeSer() {
  }

  @Override
  protected void serializeType(SymTypeExpression type, MontiArcSymbols2Json s2j) {

  }

  @Override
  protected SymTypeExpression deserializeType(JsonObject symbolJson) {
    return null;
  }
}
