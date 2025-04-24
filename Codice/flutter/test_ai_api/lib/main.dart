import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:test_ai_api/screens/chat_history.dart';
import 'package:test_ai_api/utils/theme.dart';

Future<void> main() async {
  await dotenv.load(fileName: ".env");
  runApp(const DeepSeekChatApp());
}

class DeepSeekChatApp extends StatelessWidget {
  const DeepSeekChatApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'DeepSeek Chat',
      theme: AppTheme.lightTheme,
      darkTheme: AppTheme.darkTheme,
      themeMode: ThemeMode.system,
      debugShowCheckedModeBanner: false,
      home: const ChatHistoryScreen(),
    );
  }
}
