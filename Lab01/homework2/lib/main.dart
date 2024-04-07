import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sentiment Analysis',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const SentimentAnalysisPage(),
    );
  }
}

class SentimentAnalysisPage extends StatefulWidget {
  const SentimentAnalysisPage({Key? key});

  @override
  _SentimentAnalysisPageState createState() => _SentimentAnalysisPageState();
}

class _SentimentAnalysisPageState extends State<SentimentAnalysisPage> {
  final TextEditingController _textEditingController = TextEditingController();
  String _sentiment = '';
  IconData _sentimentIcon = Icons.sentiment_neutral;
  Color _sentimentColor = Colors.black;

  Future<String> queryAPI(String text) async {
    const apiUrl =
        "https://api-inference.huggingface.co/models/wonrax/phobert-base-vietnamese-sentiment";
    final headers = {
      "Authorization": "Bearer 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",
      "Content-Type": "application/json",
    };

    final payload = jsonEncode({"inputs": text});
    final response =
        await http.post(Uri.parse(apiUrl), headers: headers, body: payload);

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body)[0] as List<dynamic>;
      String sentiment = '';
      double maxScore = 0.0;
      for (var item in data) {
        double score = item['score'];
        if (score > maxScore) {
          maxScore = score;
          sentiment = item['label'];
        }
      }
      return sentiment;
    } else {
      throw Exception('Failed to query API');
    }
  }

  void _analyzeSentiment() async {
    String inputText = _textEditingController.text;
    String sentiment;
    try {
      sentiment = await queryAPI(inputText);
    } catch (e) {
      sentiment = 'Error';
    }

    IconData sentimentIcon;
    Color sentimentColor;
    switch (sentiment) {
      case 'POS':
        sentimentIcon = Icons.sentiment_very_satisfied;
        sentimentColor = Colors.green;
        break;
      case 'NEU':
        sentimentIcon = Icons.sentiment_neutral;
        sentimentColor = Colors.grey;
        break;
      case 'NEG':
        sentimentIcon = Icons.sentiment_very_dissatisfied;
        sentimentColor = Colors.red;
        break;
      default:
        sentimentIcon = Icons.sentiment_neutral;
        sentimentColor = Colors.black;
    }

    setState(() {
      _sentiment = sentiment;
      _sentimentIcon = sentimentIcon;
      _sentimentColor = sentimentColor;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sentiment Analysis'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller: _textEditingController,
              keyboardType: TextInputType.text,
              inputFormatters: [
                FilteringTextInputFormatter.allow(
                  RegExp(r'[a-zA-ZÀ-ỹ\s\d]+$'),
                ),
              ], // Allow Vietnamese characters and digits
              decoration: const InputDecoration(
                labelText: 'Enter text to analyze',
              ),
            ),
            const SizedBox(height: 20.0),
            ElevatedButton(
              onPressed: _analyzeSentiment,
              child: const Text('Analyze'),
            ),
            const SizedBox(height: 20.0),
            Text(
              'Sentiment: $_sentiment',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 20.0,
                color: _sentimentColor,
              ),
            ),
            const SizedBox(height: 10.0),
            Icon(
              _sentimentIcon,
              size: 50.0,
              color: _sentimentColor,
            ),
          ],
        ),
      ),
    );
  }
}
