package de.monticore.gettingstarted.simplejava.invalid;

class InheritedCannotUseStaticFromSuper {

  //should not work in oo type check
  int a = de.monticore.gettingstarted.simplejava.valid.InheritanceBar.getMax()

}
