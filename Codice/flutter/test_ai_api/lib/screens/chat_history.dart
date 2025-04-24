import 'package:flutter/material.dart';
import '../models/chat_session.dart';
import '../services/storage_service.dart';
import 'chat_screen.dart';

class ChatHistoryScreen extends StatefulWidget {
  const ChatHistoryScreen({Key? key}) : super(key: key);

  @override
  _ChatHistoryScreenState createState() => _ChatHistoryScreenState();
}

class _ChatHistoryScreenState extends State<ChatHistoryScreen> {
  final StorageService _storageService = StorageService();
  List<ChatSession> _chatSessions = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadChatSessions();
  }

  Future<void> _loadChatSessions() async {
    setState(() {
      _isLoading = true;
    });

    try {
      final sessions = await _storageService.getAllChatSessions();
      setState(() {
        _chatSessions = sessions;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      print('Errore nel caricamento delle sessioni: $e');
    }
  }

  Future<void> _deleteSession(String sessionId) async {
    await _storageService.deleteChatSession(sessionId);
    await _loadChatSessions();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Chat History'),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const DeepSeekChatScreen(),
                ),
              ).then((_) => _loadChatSessions()); // Reload after returning
            },
          ),
        ],
      ),
      body: _isLoading
        ? const Center(child: CircularProgressIndicator())
        : _chatSessions.isEmpty
          ? _buildEmptyState()
          : _buildChatList(),
    );
  }

  Widget _buildEmptyState() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.chat_bubble_outline,
            size: 100,
            color: Colors.grey[300],
          ),
          const SizedBox(height: 20),
          const Text(
            'Nessuna chat salvata',
            style: TextStyle(
              fontSize: 18,
              color: Colors.grey,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildChatList() {
    return ListView.builder(
      itemCount: _chatSessions.length,
      itemBuilder: (context, index) {
        final session = _chatSessions[index];
        return Dismissible(
          key: Key(session.id),
          background: Container(
            color: Colors.red,
            alignment: Alignment.centerRight,
            padding: const EdgeInsets.only(right: 20),
            child: const Icon(
              Icons.delete,
              color: Colors.white,
            ),
          ),
          direction: DismissDirection.endToStart,
          onDismissed: (_) => _deleteSession(session.id),
          child: ListTile(
            title: Text(
              session.title,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
            subtitle: Text(
              '${session.messages.length} messaggi',
              style: const TextStyle(color: Colors.grey),
            ),
            trailing: Text(
              _formatDate(session.lastUpdated),
              style: const TextStyle(color: Colors.grey, fontSize: 12),
            ),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => DeepSeekChatScreen(
                    initialSession: session,
                  ),
                ),
              ).then((_) => _loadChatSessions()); // Reload after returning
            },
          ),
        );
      },
    );
  }

  // Metodo _formatDate rimane invariato
  String _formatDate(DateTime date) {
    final now = DateTime.now();
    if (now.year == date.year && 
        now.month == date.month && 
        now.day == date.day) {
      return '${date.hour.toString().padLeft(2, '0')}:'
             '${date.minute.toString().padLeft(2, '0')}';
    } else {
      return '${date.day}/${date.month}/${date.year}';
    }
  }
}