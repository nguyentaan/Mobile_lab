import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Dictionary App',
      theme: ThemeData(
        primaryColor: Colors.blue,
        hintColor: Colors.blueAccent,
        fontFamily: 'Roboto',
      ),
      home: const DictionaryHomePage(),
    );
  }
}

class DictionaryHomePage extends StatelessWidget {
  const DictionaryHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dictionary'),
      ),
      body: const DictionarySearchPage(),
    );
  }
}

class DictionarySearchPage extends StatefulWidget {
  const DictionarySearchPage({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _DictionarySearchPageState createState() => _DictionarySearchPageState();
}

class _DictionarySearchPageState extends State<DictionarySearchPage> {
  final TextEditingController _textEditingController = TextEditingController();
  List<dynamic> _definitions = [];

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Container(
          padding: const EdgeInsets.all(16),
          color: Theme.of(context).primaryColor,
          child: TextField(
            controller: _textEditingController,
            style: const TextStyle(color: Colors.white),
            decoration: InputDecoration(
              hintText: 'Enter a word',
              hintStyle: const TextStyle(color: Colors.white70),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(8),
                borderSide: BorderSide.none,
              ),
              filled: true,
              fillColor: Colors.black.withOpacity(0.2),
              // Replace the prefixIcon with GestureDetector
              prefixIcon: GestureDetector(
                onTap: () {
                  _searchWord(_textEditingController.text);
                },
                child: const Icon(Icons.search, color: Colors.white),
              ),
            ),
          ),
        ),
        Expanded(
          child: ListView.builder(
            itemCount: _definitions.length,
            itemBuilder: (context, index) {
              final definition = _definitions[index];
              return Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Word: ${definition['word']}',
                      style: const TextStyle(
                          fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    if (definition['phonetic'] != null)
                      Text(
                        'Phonetic: ${definition['phonetic']}',
                        style: const TextStyle(fontStyle: FontStyle.italic),
                      ),
                    const SizedBox(height: 8),
                    if (definition['phonetics'] != null)
                      Text(
                        'Phonetics: ${definition['phonetics'].map((e) => e['text']).join(', ')}',
                        style: const TextStyle(fontStyle: FontStyle.italic),
                      ),
                    const SizedBox(height: 8),
                    if (definition['origin'] != null)
                      Text(
                        'Origin: ${definition['origin']}',
                        style: const TextStyle(fontStyle: FontStyle.italic),
                      ),
                    const SizedBox(height: 8),
                    for (final meaning in definition['meanings']) ...[
                      Text(
                        'Part of Speech: ${meaning['partOfSpeech']}',
                        style: const TextStyle(fontWeight: FontWeight.bold),
                      ),
                      const SizedBox(height: 8),
                      for (final def in meaning['definitions'])
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              'Definition: ${def['definition']}',
                              style: const TextStyle(fontSize: 16),
                            ),
                            if (def['example'] != null)
                              Text(
                                'Example: ${def['example']}',
                                style: const TextStyle(
                                    fontStyle: FontStyle.italic),
                              ),
                            if (def['synonyms'] != null &&
                                def['synonyms'].isNotEmpty)
                              Text(
                                'Synonyms: ${def['synonyms'].join(', ')}',
                                style: const TextStyle(
                                    fontStyle: FontStyle.italic),
                              ),
                            if (def['antonyms'] != null &&
                                def['antonyms'].isNotEmpty)
                              Text(
                                'Antonyms: ${def['antonyms'].join(', ')}',
                                style: const TextStyle(
                                    fontStyle: FontStyle.italic),
                              ),
                            const SizedBox(height: 8),
                          ],
                        ),
                    ],
                    Divider(color: Theme.of(context).primaryColor),
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }

  Future<void> _searchWord(String word) async {
    final url =
        Uri.parse('https://api.dictionaryapi.dev/api/v2/entries/en/$word');
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      setState(() {
        _definitions = jsonResponse;
      });
    } else {
      setState(() {
        _definitions = [];
      });
    }
  }
}
