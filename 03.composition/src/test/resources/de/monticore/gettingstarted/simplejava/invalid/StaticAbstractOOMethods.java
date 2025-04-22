package de.monticore.gettingstarted.simplejava.invalid;

class StaticAbstractOOMethods {

  void foo() {
    //should be okay with both abstract and oo type check
    int a = de.monticore.gettingstarted.simplejava.valid.Bar.getMax()
    //oo type check should throw an error here
    int b = de.monticore.gettingstarted.simplejava.valid.Bar.getAge()
  }

}
