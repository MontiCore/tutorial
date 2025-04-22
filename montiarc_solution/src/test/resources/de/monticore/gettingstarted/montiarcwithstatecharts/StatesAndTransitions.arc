/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarcwithstatecharts;

/**
 * valid
 * a bit more complex than SomeStates
 */
component StatesAndTransitions {
  port in boolean open, unlock;
  port out boolean ringing;

  statechart Door {
    initial state Closed;
    state Locked;
    state Opened;

    Opened -> Locked;
    Locked -> Closed;
    Closed -> Opened;

  }
}
