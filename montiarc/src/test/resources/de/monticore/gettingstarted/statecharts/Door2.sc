/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.statecharts;

statechart Door2 {
  state Opened;
  initial state Closed;
  state Locked;

  // event definitions
  event close();
  event open(boolean ringBell);
  event timeout();
  event boolean unlock();

  Opened -> Closed close() ;
  Closed -> Opened open(true) / {ringTheDoorBell();};
  Closed -> Opened open(false) / ;
  Closed -> Locked timeOut() / { lockDoor(); } ;
  Locked -> Closed [isAuthorized] unlock() / {return true;};
}
