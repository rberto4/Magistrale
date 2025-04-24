import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_markdown/flutter_markdown.dart';
import 'package:test_ai_api/screens/code_view_screen.dart';
import 'package:test_ai_api/services/api_services.dart';
import '../models/chat_message.dart';
import '../models/chat_session.dart';
import '../services/storage_service.dart';
import 'dart:convert' show utf8;


class DeepSeekChatScreen extends StatefulWidget {
  final ChatSession? initialSession;

  const DeepSeekChatScreen({Key? key, this.initialSession}) : super(key: key);

  @override
  _DeepSeekChatScreenState createState() => _DeepSeekChatScreenState();
}

class _DeepSeekChatScreenState extends State<DeepSeekChatScreen> {
  late ChatSession _currentSession;
  final TextEditingController _messageController = TextEditingController();
  final ScrollController _scrollController = ScrollController();
  final ApiService _apiService = ApiService(
    apiKey: dotenv.env['ai_key'] ?? '',
  );
  final StorageService _storageService = StorageService();
  bool _isLoading = false;
  bool _isFirstLoad = true;

  @override
  void initState() {
    super.initState();
    _initializeSession();
  }

  void _initializeSession() {
    // Use initial session or create a new one
    _currentSession = widget.initialSession ?? ChatSession();

    // If this is a brand new session, save it immediately
    if (widget.initialSession == null) {
      _saveSession();
    }

    // Scroll to bottom after initialization
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _scrollToBottom();
      setState(() {
        _isFirstLoad = false;
      });
    });
  }

  Future<void> _saveSession() async {
    try {
      await _storageService.saveChatSession(_currentSession);
    } catch (e) {
      print('Error saving session: $e');
    }
  }

  void _scrollToBottom() {
    if (_scrollController.hasClients) {
      _scrollController.animateTo(
        _scrollController.position.maxScrollExtent,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeOut,
      );
    }
  }

  Future<void> _sendMessage() async {
    final message = _messageController.text.trim();
    if (message.isEmpty) return;

    // Clear text field and set loading state
    _messageController.clear();
    setState(() {
      _isLoading = true;
      _currentSession.addMessage(ChatMessage(text: message, isUser: true));
    });

    // Immediately save user message
    await _saveSession();

    try {
      // Send message and get AI response
      final response = await _apiService.sendMessage(_currentSession.messages);

      setState(() {
        _currentSession.addMessage(response);
        _isLoading = false;
      });

      // Save session after AI response
      await _saveSession();

      // Scroll to bottom
      _scrollToBottom();
    } catch (e) {
      setState(() {
        _currentSession.addMessage(
          ChatMessage(
            text: 'Errore di connessione: ${e.toString()}',
            isUser: false,
            isError: true,
          ),
        );
        _isLoading = false;
      });

      // Save error message
      await _saveSession();
    }
  }

  void _showCodeView(String code, {String? language}) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => CodeViewerScreen(code: code, language: language),
      ),
    );
  }

  void _updateSessionTitle() {
    // Automatic title generation from first message
    if (_currentSession.title == 'New Chat' &&
        _currentSession.messages.isNotEmpty &&
        _currentSession.messages.first.isUser) {
      String firstMessage = _currentSession.messages.first.text;
      setState(() {
        _currentSession.title =
            firstMessage.length > 30
                ? '${firstMessage.substring(0, 30)}...'
                : firstMessage;
      });
      _saveSession();
    }
  }

  @override
  Widget build(BuildContext context) {
    // Ensure title is updated
    _updateSessionTitle();

    return WillPopScope(
      onWillPop: () async {
        // Ensure session is saved before closing
        await _saveSession();
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          title: Text(
            _currentSession.title,
            style: const TextStyle(fontSize: 18),
          ),
          actions: [
            IconButton(
              icon: const Icon(Icons.delete_outline),
              onPressed: () {
                _showClearChatDialog();
              },
            ),
          ],
        ),
        body: SafeArea(
          child: Column(
            children: [
              Expanded(child: _buildMessageList()),
              _buildMessageInput(),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildMessageList() {
    return ListView.builder(
      controller: _scrollController,
      itemCount: _currentSession.messages.length + (_isLoading ? 1 : 0),
      itemBuilder: (context, index) {
        // Handle loading indicator
        if (index == _currentSession.messages.length) {
          return _buildLoadingIndicator();
        }

        final message = _currentSession.messages[index];
        return _buildMessageBubble(message, context);
      },
    );
  }

  Widget _buildMessageBubble(ChatMessage message, BuildContext context) {

bool hasProgrammingKeywords(String text) {
  const keywords = ["function", "def ", "class ", "=>", "{", "}", "print("];
  return keywords.any((keyword) => text.contains(keyword));
}
    return Align(
      alignment: message.isUser ? Alignment.centerRight : Alignment.centerLeft,
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 12),
        constraints: BoxConstraints(
          maxWidth: MediaQuery.of(context).size.width * 0.8,
        ),
        child: Column(
          crossAxisAlignment:
              message.isUser
                  ? CrossAxisAlignment.end
                  : CrossAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color:
                    message.isUser
                        ? Colors.lightGreen
                        : (message.isError ? Colors.red : Theme.of(context).cardColor),
                borderRadius: BorderRadius.circular(12),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  MarkdownBody(
                  
                    data: utf8.decode(message.text.codeUnits, allowMalformed: true),
                    styleSheet: MarkdownStyleSheet(
                      // Stile del testo normale
                      p: TextStyle(
                        fontSize: 16.0,
                        height: 1.4,
                        color: Theme.of(context).colorScheme.onSurface,
                      ),

                      // Titoli
                      h1: TextStyle(
                        fontSize: 24.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.blue[800],
                      ),
                      h2: TextStyle(
                        fontSize: 22.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.blue[700],
                      ),
                      h3: TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.blue[600],
                      ),

                      // Link
                      a: TextStyle(
                        color: Colors.blue,
                        decoration: TextDecoration.underline,
                      ),

                      // Codice
                      code: TextStyle(
                        backgroundColor: Colors.grey[300],
                        fontFamily: 'monospace',
                        fontSize: 14.0,
                        color: Colors.purple[800],
                      ),

                      // Blocchi di codice
                      codeblockPadding: EdgeInsets.all(12.0),
                      codeblockDecoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(4.0),
                      ),

                      // Liste
                      listBullet: TextStyle(
                        fontSize: 16.0,
                        color: Colors.black87,
                      ),
                      listIndent: 24.0,

                      // Citazioni
                      blockquote: TextStyle(
                        color: Colors.grey[700],
                        fontStyle: FontStyle.italic,
                      ),
                      blockquoteDecoration: BoxDecoration(
                        color: Colors.grey[100],
                        border: Border(
                          left: BorderSide(color: Colors.grey, width: 4.0),
                        ),
                      ),

                      // Tabelle (se supportate)
                      tableBorder: TableBorder.all(
                        color: Colors.grey,
                        width: 1.0,
                      ),
                     

                      // Emoji (aggiungi il font specifico)
              
                      // Spaziatura generale
                      blockSpacing: 12.0,
                    ),
                  ),
                  if (hasProgrammingKeywords(message.text))
                    Padding(
                      padding: const EdgeInsets.only(top: 8),
                      child: ElevatedButton.icon(
                        onPressed:
                            () => _showCodeView(
                              message.text,
                              language: message.codeLanguage,
                            ),
                        icon: const Icon(Icons.code, size: 16),
                        label: Text(
                          'Visualizza codice',
                        ),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.blue[700],
                          foregroundColor: Colors.white,
                        ),
                      ),
                    ),
                ],
              ),
            ),
            // Timestamp
            Padding(
              padding: const EdgeInsets.only(top: 4),
              child: Text(
                _formatTimestamp(message.timestamp),
                style: const TextStyle(fontSize: 10, color: Colors.grey),
              ),
            ),
          ],
        ),
      ),
    );
  }

  String _formatTimestamp(DateTime timestamp) {
    return DateTime.fromMillisecondsSinceEpoch(
      timestamp.millisecondsSinceEpoch,
    ).toString();
  }

  Widget _buildLoadingIndicator() {
    return const Center(
      child: Padding(
        padding: EdgeInsets.all(8.0),
        child: CircularProgressIndicator(),
      ),
    );
  }

  Widget _buildMessageInput() {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        children: [
          Expanded(
            child: TextField(
              controller: _messageController,
              decoration: InputDecoration(
                hintText: 'Scrivi un messaggio...',
                filled: true,
                fillColor: Theme.of(context).cardColor,
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                  borderSide: BorderSide.none,
                ),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.clear),
                  onPressed: () => _messageController.clear(),
                ),
              ),
              maxLines: null,
              keyboardType: TextInputType.multiline,
              textInputAction: TextInputAction.newline,
              onSubmitted: (_) => _sendMessage(),
            ),
          ),
          const SizedBox(width: 8),
          IconButton(
            icon: const Icon(Icons.send),
            onPressed: _sendMessage,
            style: IconButton.styleFrom(
              backgroundColor: Colors.blue,
              foregroundColor: Colors.white,
            ),
          ),
        ],
      ),
    );
  }

  void _showClearChatDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Cancella Chat'),
          content: const Text(
            'Sei sicuro di voler cancellare questa conversazione?',
          ),
          actions: [
            TextButton(
              child: const Text('Annulla'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            ElevatedButton(
              child: const Text('Cancella'),
              onPressed: () {
                setState(() {
                  _currentSession = ChatSession();
                });
                _saveSession();
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  void dispose() {
    _messageController.dispose();
    _scrollController.dispose();
    super.dispose();
  }
}
