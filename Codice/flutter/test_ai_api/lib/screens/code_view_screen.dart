import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_highlight/flutter_highlight.dart';
import 'package:flutter_highlight/themes/androidstudio.dart';
import 'package:google_fonts/google_fonts.dart';

class CodeViewerScreen extends StatelessWidget {
  final String code;
  final String? language;

  const CodeViewerScreen({
    Key? key, 
    required this.code, 
    this.language,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final fullCode = '```${language ?? ''}\n$code\n```';
    
    return Scaffold(
      appBar: AppBar(
        title: Text(language?.toUpperCase() ?? 'CODE VIEWER'),
        actions: [
          IconButton(
            icon: const Icon(Icons.copy),
            tooltip: 'Copy to clipboard',
            onPressed: () async {
              await Clipboard.setData(ClipboardData(text: code));
              if (!context.mounted) return;
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('Code copied to clipboard'),
                  behavior: SnackBarBehavior.fixed,
                ),
              );
            },
          ),
        ],
      ),
      body: GestureDetector(
        onDoubleTap: () => Clipboard.setData(ClipboardData(text: code)),
        child: InteractiveViewer(
          minScale: 0.5,
          maxScale: 3.0,
          boundaryMargin: const EdgeInsets.all(double.infinity),
          child: InteractiveViewer(
        minScale: 0.5,
        maxScale: 4.0,
        child: SingleChildScrollView(
          padding: EdgeInsets.zero,
          child: HighlightView(
            code,
            language: language?.toLowerCase() ?? 'dart',
            theme: androidstudioTheme,
            padding: EdgeInsets.zero,
            textStyle: GoogleFonts.robotoMono(
              fontSize: 13,
              height: 1.4,
            ),
          ),
        ),
      ),
        ),
      ),
    );
  }
}
