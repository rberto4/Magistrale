
import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:test_ai_api/models/chat_message.dart';

class ApiService {
  static final String? _baseUrl = dotenv.env['ai_url'];
  final String apiKey;
  final String model;

  ApiService({
    required this.apiKey,
    String? model,
  }) : model = model ?? dotenv.env['ai_model'] ?? '';

  Future<ChatMessage> sendMessage(List<ChatMessage> messages) async {
    try {
      final apiMessages = messages.map((msg) => {
            'role': msg.isUser ? 'user' : 'assistant',
            'content': msg?.text,
          }).toList();

      final response = await http.post(
        Uri.parse(_baseUrl!),
        headers: {
          'Authorization': 'Bearer $apiKey',
          'HTTP-Referer': 'https://your-app.com',
          'Content-Type': 'application/json',
        },
        body: jsonEncode({
          'model': model,
          'messages': apiMessages,
          'max_tokens': 1500,
        }),
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final reply = data['choices'][0]['message']['content'];
        
        return ChatMessage(
          text: reply,
          isUser: false,
          
        );
      } else {
        throw Exception('API Error: ${response.statusCode}');
      }
    } catch (e) {
      return ChatMessage(
        text: 'Connection error: $e',
        isUser: false,
        isError: true,
      );
    }
  }

  String? _detectCodeLanguage(String text) {
    final codeBlockRegex = RegExp(r'```(\w+)?\n([\s\S]*?)```');
    final match = codeBlockRegex.firstMatch(text);
    return match?.group(1);
  }
}