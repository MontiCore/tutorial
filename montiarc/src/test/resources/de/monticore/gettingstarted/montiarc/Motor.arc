/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarc;

component Motor(int motorPort) {
  port in String cmd;
  port in int speed;
}
