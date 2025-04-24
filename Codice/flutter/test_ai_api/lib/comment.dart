/**
 * 
class ChatScreen extends StatefulWidget {
  const ChatScreen({Key? key}) : super(key: key);

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final TextEditingController _messageController = TextEditingController();
  final List<ChatMessage> _messages = [];
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    // Aggiunge un messaggio di benvenuto dall'AI
    _addAIMessage("Ciao! Sono l'assistente AI. Come posso aiutarti oggi?");
  }

  void _handleSubmit(String text) async {
    _messageController.clear();
    if (text.trim().isEmpty) return;

    final userMessage = _createUserMessage(text);
    setState(() {
      _messages.add(userMessage);
      _isLoading = true;
    });

    try {
      // Effettua la richiesta a OpenAI
      final response = await _sendMessageToOpenAI(text);

      // Aggiunge la risposta dell'AI alla chat
      _addAIMessage(response);
    } catch (e) {
      print("Errore dettagliato nella richiesta OpenAI: $e");
      _addAIMessage(
        "Mi dispiace, c'è stato un errore di comunicazione con l'AI. Riprova più tardi.",
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  ChatMessage _createUserMessage(String text) {
    return ChatMessage(text: text, isMe: true, time: _getCurrentTime());
  }

  void _addAIMessage(String text) {
    setState(() {
      _messages.add(
        ChatMessage(text: text, isMe: false, time: _getCurrentTime()),
      );
    });
  }

  String _getCurrentTime() {
    final now = DateTime.now();
    return "${now.hour}:${now.minute.toString().padLeft(2, '0')}";
  }

  Future<String> _sendMessageToOpenAI(String userMessage) async {
    try {
      // the system message that will be sent to the request.
      final systemMessage = OpenAIChatCompletionChoiceMessageModel(
        content: [
          OpenAIChatCompletionChoiceMessageContentItemModel.text(
            "return any message you are given as JSON.",
          ),
        ],
        role: OpenAIChatMessageRole.assistant,
      );

      // the user message that will be sent to the request.
      final userMessageModel = OpenAIChatCompletionChoiceMessageModel(
        content: [
          OpenAIChatCompletionChoiceMessageContentItemModel.text(userMessage),
        ],
        role: OpenAIChatMessageRole.user,
      );

      final requestMessages = [systemMessage, userMessageModel];

      // the actual request.
      OpenAIChatCompletionModel chatCompletion = await OpenAI.instance.chat
          .create(
            model: "deepseek/deepseek-chat-v3-0324:free",
            messages: [
              OpenAIChatCompletionChoiceMessageModel(
                content: [
                  OpenAIChatCompletionChoiceMessageContentItemModel.text(
                    "hello, what is Flutter and Dart ?",
                  ),
                ],
                role: OpenAIChatMessageRole.assistant,
              ),
            ],
            temperature: 0.2,
            maxTokens: 300,
          );

      // Null check before accessing content
      final response = chatCompletion.choices.first.message.content;
      if (response == null) {
        throw Exception("No response content received");
      }

      return response.toString();
    } catch (e) {
      print("Detailed OpenAI Error: $e");
      throw Exception("Failed to fetch response from OpenAI: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Chat AI'), centerTitle: true),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(8.0),
              itemCount: _messages.length,
              itemBuilder: (_, int index) => _messages[index],
            ),
          ),
          if (_isLoading)
            const Padding(
              padding: EdgeInsets.symmetric(vertical: 8.0),
              child: Center(child: CircularProgressIndicator()),
            ),
          Container(
            decoration: BoxDecoration(
              color: Theme.of(context).cardColor,
              boxShadow: [
                BoxShadow(
                  blurRadius: 8,
                  color: Colors.black12,
                  offset: Offset(0, -2),
                ),
              ],
            ),
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _messageController,
                    decoration: const InputDecoration(
                      hintText: 'Scrivi un messaggio...',
                      border: InputBorder.none,
                    ),
                    onSubmitted: _handleSubmit,
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.send),
                  onPressed: () => _handleSubmit(_messageController.text),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class ChatMessage extends StatelessWidget {
  final String text;
  final bool isMe;
  final String time;

  const ChatMessage({
    Key? key,
    required this.text,
    required this.isMe,
    required this.time,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        mainAxisAlignment:
            isMe ? MainAxisAlignment.end : MainAxisAlignment.start,
        children: [
          if (!isMe) ...[
            CircleAvatar(child: Text('AI'), backgroundColor: Colors.purple),
            const SizedBox(width: 8),
          ],
          Flexible(
            child: Container(
              padding: const EdgeInsets.symmetric(
                horizontal: 16.0,
                vertical: 10.0,
              ),
              decoration: BoxDecoration(
                color: isMe ? Colors.blue[300] : Colors.grey[300],
                borderRadius: BorderRadius.circular(20),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    text,
                    style: TextStyle(color: isMe ? Colors.white : Colors.black),
                  ),
                  const SizedBox(height: 2),
                  Text(
                    time,
                    style: TextStyle(
                      color: isMe ? Colors.white70 : Colors.black54,
                      fontSize: 12,
                    ),
                  ),
                ],
              ),
            ),
          ),
          if (isMe) const SizedBox(width: 8),
        ],
      ),
    );
  }
}

 * 
 * 
 * 
 */



// codice funzionante --------------
/**
 * import 'dart:convert';

import 'package:dart_openai/dart_openai.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

final String url = "https://api.openai.com";
final String key ="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";
void main() {
  OpenAI.apiKey = key;
  OpenAI.baseUrl = url;
  OpenAI.requestsTimeOut = Duration(seconds: 60);
  OpenAI.showLogs = true;
  OpenAI.showResponsesLogs = true;

  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chat App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home:  DeepSeekChatScreen(),
    );
  }
}


class DeepSeekChatScreen extends StatefulWidget {
  @override
  _DeepSeekChatScreenState createState() => _DeepSeekChatScreenState();
}

class _DeepSeekChatScreenState extends State<DeepSeekChatScreen> {
  final TextEditingController _controller = TextEditingController();
  String _response = "Scrivi un messaggio e premi 'Invia'...";

  Future<void> _sendMessage() async {
    if (_controller.text.isEmpty) return;

    setState(() {
      _response = "Caricamento...";
    });

    const model = "deepseek/deepseek-chat-v3-0324:free"; // ID del modello su OpenRouter

    final response = await http.post(
      Uri.parse("https://openrouter.ai/api/v1/chat/completions"),
      headers: {
        "Authorization": "Bearer $key",
        "HTTP-Referer": "https://tuo-sito.com", // Obbligatorio (può essere fittizio)
        "X-Title": "La tua App", // Opzionale
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "model": model,
        "messages": [
          {"role": "user", "content": _controller.text},
        ],
        "max_tokens": 1000, // Limite di risposta
      }),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      setState(() {
        _response = data["choices"][0]["message"]["content"];
      });
    } else {
      setState(() {
        _response = "Errore: ${response.statusCode} - ${response.body}";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Chat con DeepSeek V3")),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                child: Text(_response),
              ),
            ),
            TextField(
              controller: _controller,
              decoration: InputDecoration(
                hintText: "Scrivi un messaggio...",
                suffixIcon: IconButton(
                  icon: Icon(Icons.send),
                  onPressed: _sendMessage,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

 */