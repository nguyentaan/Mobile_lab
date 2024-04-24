import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_tts/flutter_tts.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'News'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<dynamic> articles = [];
  late FlutterTts flutterTts;
  late ScrollController _scrollController;
  int _pageNumber = 1;

  Future<void> fetchNews() async {
    try {
      String apiUrl =
          'https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=47afa6be6bc1439aa2fdb6b9f60eeff4&page=$_pageNumber';

      final response = await http.get(Uri.parse(apiUrl));
      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body);
        setState(() {
          articles.addAll(jsonData['articles']);
        });
        _pageNumber++;
      } else {
        throw Exception('Failed to load news: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching news: $e');
      // Handle error, e.g., display a message to the user
    }
  }

  @override
  void initState() {
    super.initState();
    fetchNews();
    flutterTts = FlutterTts();
    _scrollController = ScrollController();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        fetchNews();
      }
    });
  }

  Future<void> speakArticle(String text) async {
    await flutterTts.setLanguage('en-US');
    await flutterTts.setPitch(1);
    await flutterTts.speak(text);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.greenAccent,
        title: Text(
          widget.title,
          style: const TextStyle(
              color: Colors.black, fontWeight: FontWeight.bold, fontSize: 24),
        ),
      ),
      body: ListView.builder(
        controller: _scrollController,
        itemCount: articles.length,
        itemBuilder: (context, index) {
          final article = articles[index];
          return Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(12),
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const SizedBox(height: 12),
                Image.network(
                  article['urlToImage'] ?? '',
                  height: 150,
                ),
                const SizedBox(height: 12),
                Text(
                  article['title'] ?? '',
                  style: const TextStyle(
                      fontWeight: FontWeight.bold, fontSize: 21),
                ),
                const SizedBox(height: 12),
                Text(article['description'] ?? ''),
                const SizedBox(height: 12),
                const Divider(color: Colors.grey),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(article['author'] ?? ''),
                    Text(article['publishedAt'] ?? '')
                  ],
                ),
                // const SizedBox(height: 24),
                const Divider(color: Colors.grey),
              ],
            ),
          );
        },
      ),
    );
  }
}
