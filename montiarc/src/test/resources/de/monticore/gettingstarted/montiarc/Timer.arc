/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarc;

component Timer(int delay) {
  port in String cmd;
  port out int signal;
}
