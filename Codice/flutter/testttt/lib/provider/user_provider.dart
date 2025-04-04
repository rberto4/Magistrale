import 'package:flutter/material.dart';
import 'package:testttt/model/user_model.dart';

class UserProvider extends ChangeNotifier{
  final List<UserModel> _userList = [];
  List<UserModel> get userList => _userList;

  void addUser(UserModel user) {
    _userList.add(user);
    notifyListeners();
  }

  void removeUser(int index) {
    if (index >= 0 && index < _userList.length) {
      _userList.removeAt(index);
      notifyListeners();
    }
  }
  
  String getNome (int index) {
    return _userList[index].nome;
  }
  String getCognome (int index) {
    return _userList[index].cognome;
  }

  int getEta (int index) {
    return _userList[index].eta;
  }

}