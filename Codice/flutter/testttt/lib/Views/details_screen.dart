import 'package:flutter/material.dart';
import 'package:testttt/model/user_model.dart';

class DetailsScreen extends StatelessWidget {

  final UserModel user;
  const DetailsScreen({super.key, required this.user});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: true,
        backgroundColor: Colors.blue,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, color: Colors.white),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [          
            
            ListTile(
              leading: CircleAvatar(
              backgroundColor: Colors.grey,
              child: Icon(
                Icons.person,
                color: Colors.white,
              ),
            ),
              title: Text(
                user.nome,
                style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              ),
              subtitle: Text(
                user.cognome,
                style: const TextStyle(fontSize: 20, color: Colors.grey),
              ),
            ),
          ],
        ),
      ),
    );
  }
}