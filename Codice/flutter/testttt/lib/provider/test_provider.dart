import 'package:flutter/material.dart';

class TestProvider extends ChangeNotifier{

  int _counter = 0;
  bool _isActive = false;

  bool getActive(){
    return _isActive;
  }
  void setActive(bool value) {
    _isActive = value;
    notifyListeners();
  }

  int get getCounter => _counter;

  void incrementCounter() {
    _counter++;
    notifyListeners();
  }

  void decrementCounter() {
    if (_counter > 0) {
      _counter--;
      notifyListeners();
    }
  }

}