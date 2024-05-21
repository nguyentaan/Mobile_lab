import 'package:flutter/material.dart';
import 'movie_service.dart';
import 'movie.dart';
import 'movie_tile.dart'; // Import the MovieTile widget

class MovieList extends StatelessWidget {
  const MovieList({super.key});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Movie>>(
      future: MovieService.getPopularMovies(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        } else {
          final movies = snapshot.data!;
          return FocusTraversalGroup(
            policy: ReadingOrderTraversalPolicy(),
            child: Expanded(
              // Add this
              child: GridView.builder(
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 4, // Number of columns for TV screen
                  childAspectRatio: 0.7, // Adjusted aspect ratio for TV screen
                  crossAxisSpacing: 16.0, // Spacing between columns
                  mainAxisSpacing: 16.0, // Spacing between rows
                ),
                padding: const EdgeInsets.all(16.0), // Padding around the grid
                itemCount: movies.length,
                itemBuilder: (context, index) {
                  final movie = movies[index];
                  return Focus(
                    child: MovieTile(movie: movie), // Use the MovieTile widget
                  );
                },
              ),
            ),
          );
        }
      },
    );
  }
}
