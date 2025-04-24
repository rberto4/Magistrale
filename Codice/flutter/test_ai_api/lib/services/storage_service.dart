import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/chat_session.dart';

class StorageService {
  static const _chatHistoryKey = 'chat_sessions_v2';

  Future<void> saveChatSession(ChatSession session) async {
    final prefs = await SharedPreferences.getInstance();
    
    // Retrieve existing sessions
    List<ChatSession> existingSessions = await getAllChatSessions();
    
    // Check if session already exists
    final existingIndex = existingSessions.indexWhere((s) => s.id == session.id);
    
    if (existingIndex != -1) {
      // Update existing session
      existingSessions[existingIndex] = session;
    } else {
      // Add new session
      existingSessions.add(session);
    }
    
    // Save all sessions
    await prefs.setString(
      _chatHistoryKey, 
      jsonEncode(existingSessions.map((s) => s.toJson()).toList())
    );
  }

  Future<List<ChatSession>> getAllChatSessions() async {
    final prefs = await SharedPreferences.getInstance();
    final jsonString = prefs.getString(_chatHistoryKey);
    
    if (jsonString == null) return [];

    final List<dynamic> jsonList = jsonDecode(jsonString);
    return jsonList
      .map((json) => ChatSession.fromJson(json))
      .toList()
      .reversed
      .toList();
  }

  Future<void> deleteChatSession(String sessionId) async {
    final prefs = await SharedPreferences.getInstance();
    List<ChatSession> sessions = await getAllChatSessions();
    
    sessions.removeWhere((session) => session.id == sessionId);
    
    await prefs.setString(
      _chatHistoryKey, 
      jsonEncode(sessions.map((s) => s.toJson()).toList())
    );
  }
}