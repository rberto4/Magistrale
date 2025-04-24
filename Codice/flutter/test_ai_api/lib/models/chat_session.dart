import 'package:uuid/uuid.dart';
import 'chat_message.dart';

class ChatSession {
  final String id;
  String title;
  final DateTime createdAt;
  DateTime lastUpdated;
  List<ChatMessage> messages;

  ChatSession({
    String? id,
    String? title,
    DateTime? createdAt,
    DateTime? lastUpdated,
    List<ChatMessage>? messages,
  })  : id = id ?? const Uuid().v4(),
        title = title ?? 'New Chat',
        createdAt = createdAt ?? DateTime.now(),
        lastUpdated = lastUpdated ?? DateTime.now(),
        messages = messages ?? [];

  void addMessage(ChatMessage message) {
    messages.add(message);
    lastUpdated = DateTime.now();
    
    // Auto-generate title from first user message if not set
    if (title == 'New Chat' && message.isUser) {
      title = _generateTitle(message.text);
    }
  }

  String _generateTitle(String text) {
    // Truncate and clean up the first message to create a title
    return text.length > 30 
      ? '${text.substring(0, 30)}...' 
      : text;
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'title': title,
        'createdAt': createdAt.toIso8601String(),
        'lastUpdated': lastUpdated.toIso8601String(),
        'messages': messages.map((msg) => msg.toJson()).toList(),
      };

  factory ChatSession.fromJson(Map<String, dynamic> json) => ChatSession(
        id: json['id'],
        title: json['title'],
        createdAt: DateTime.parse(json['createdAt']),
        lastUpdated: DateTime.parse(json['lastUpdated']),
        messages: (json['messages'] as List)
            .map((msgJson) => ChatMessage.fromJson(msgJson))
            .toList(),
      );
}