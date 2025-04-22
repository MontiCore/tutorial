/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.statecharts;

statechart Car {
  initial state EngineOff;              // state with initial marker
  state EngineRunning [!fuelIsEmpty] {  // state with invariant and substates
    entry / {lightsOn();}               // entry action
    initial state Parking;              // substate
    state Driving;
    exit / {lightsOff();}               // exit action
  };

  EngineOff -> EngineRunning;
  EngineRunning -> EngineOff;
}
