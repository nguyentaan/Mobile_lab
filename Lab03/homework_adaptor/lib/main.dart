import 'package:flutter/material.dart';

// User class
class User {
  final String name;
  final int age;

  User(this.name, this.age);
}

// Adapter class
class UserAdapter {
  final List<User> users;

  UserAdapter(this.users);

  List<Widget> get userWidgets {
    return users.map((user) => UserListItem(user)).toList();
  }
}

class UserListItem extends StatelessWidget {
  final User user;

  const UserListItem(this.user, {super.key});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(user.name),
      subtitle: Text('Age: ${user.age}'),
    );
  }
}

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  final List<User> users = [
    User('Alice', 30),
    User('Bob', 25),
    User('Charlie', 35),
  ];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'User List Display',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: const Text('User List'),
        ),
        body: Center(
          child: ListView(
            children: UserAdapter(users).userWidgets,
          ),
        ),
      ),
    );
  }
}
