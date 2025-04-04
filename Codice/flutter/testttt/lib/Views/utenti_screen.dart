import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:testttt/Views/details_screen.dart';
import 'package:testttt/model/user_model.dart';
import 'package:testttt/provider/user_provider.dart';

class UtentiScreen extends StatelessWidget {
  const UtentiScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blue,
        centerTitle: true,
        title: const Text('Utenti', style: TextStyle(color: Colors.white)),
      ),
      body:
          context.watch<UserProvider>().userList.isEmpty
              ? Center(
                child: Column(
                  children: [
                    Spacer(),
                    Icon(Icons.person, size: 100, color: Colors.grey.shade400),
                    Text(
                      'Nessun utente trovato',
                      style: TextStyle(fontSize: 20, color: Colors.grey),
                    ),
                    Spacer(),
                  ],
                ),
              )
              : ListView.builder(
                shrinkWrap: true,
                itemCount:
                    context
                        .watch<UserProvider>()
                        .userList
                        .length, // Replace with the actual number of users
                itemBuilder: (context, index) {
                  return ListTile(
                    onTap: () => Navigator.push(context, MaterialPageRoute(
                      builder: (context) => DetailsScreen(
                        user: context.watch<UserProvider>().userList[index],
                      ),
                    )),
                    leading: CircleAvatar(
                      backgroundColor: Colors.blue,
                      child: Text(
                        context.watch<UserProvider>().userList[index].eta.toString(),
                        style: const TextStyle(
                          color: Colors.white,
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    title: Text(
                      context.watch<UserProvider>().userList[index].nome,
                      style: const TextStyle(fontSize: 18),
                    ), // Replace with user data
                    subtitle: Text(
                      context.watch<UserProvider>().userList[index].cognome,
                    ),
                    trailing: IconButton(
                      onPressed: () {
                        context.read<UserProvider>().removeUser(index);
                      },
                      icon: Icon(Icons.delete, color: Colors.red.shade400),
                    ), // Replace with user details
                  );
                },
              ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.blue,
        onPressed: () {
          showUserDialog(context);
        },
        child: const Icon(Icons.add, color: Colors.white),
      ),
    );
  }

  void showUserDialog(BuildContext context) {

    TextEditingController nomeController = TextEditingController();
    TextEditingController cognomeController = TextEditingController();
    TextEditingController etaController = TextEditingController();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Aggiungi Utente'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: nomeController,
                decoration: const InputDecoration(labelText: 'Nome'),
              ),
              TextField(
                controller: cognomeController,
                decoration: const InputDecoration(labelText: 'Cognome'),
              ),
              TextField(
                controller: etaController,
                decoration: const InputDecoration(labelText: 'Età'),
                keyboardType: TextInputType.number,
              ),
            ],
          ),
          actions: [
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade400,
              ),
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text('Annulla'),
            ),
            ElevatedButton(
              onPressed: () {
                // Add user logic here
                context.read<UserProvider>().addUser(
                  UserModel(
                    nome: nomeController.text,
                    cognome: cognomeController.text,
                    eta:
                        etaController.text.isNotEmpty
                            ? int.parse(etaController.text)
                            : 0,
                  ),
                );
                Navigator.of(context).pop();
              },
              child: const Text('Aggiungi'),
            ),
          ],
        );
      },
    );
  }
}
