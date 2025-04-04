import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:testttt/Views/utenti_screen.dart';
import 'package:testttt/provider/test_provider.dart';
import 'package:testttt/provider/user_provider.dart';

void main() {
  runApp(
     MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => UserProvider())],
      child: const MyApp(),
    ),
);
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData.light().copyWith(
        colorScheme: ThemeData.light().colorScheme.copyWith(
          primary: Colors.blue,
                    surface: Colors.grey.shade200
        ),
      ),
      debugShowCheckedModeBanner: false,
      darkTheme: ThemeData.light().copyWith(
        colorScheme: ThemeData.dark().colorScheme.copyWith(
          primary: Colors.blue,
          surface: Colors.grey.shade800
        ),
      ),
      home: UtentiScreen()
    );
  }
}






// context.watch<TestProvider>().getCounter.toString(),
// context.read<TestProvider>().decrementCounter();
