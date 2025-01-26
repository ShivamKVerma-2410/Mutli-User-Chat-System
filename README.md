Multi-User Chat System
A multi-user chat application built using Java and Socket Programming that allows multiple clients to communicate in real-time with a central server. The system supports simultaneous messaging, client identification, and efficient connection management.

Features:
Real-time communication between multiple users.
Multi-threading: Handles multiple client connections at once.
Client Identification: Each user is identified by a unique name.
Message Exchange: Clients can send and receive messages simultaneously.
Graceful Exit: Type "exit" to disconnect from the server.
Technologies Used:
Java (Socket Programming, I/O Streams)
Multi-threading for handling multiple client connections
Networking (TCP/IP Sockets)
How to Run:

Clone the Repository:
git clone https://github.com/yourusername/multi-user-chat-system.git

Compile the Server and Client Code:
javac Server1.java
javac Client1.java

Run the Server:
java Server1

Run the Client (on multiple terminals):
java Client1

Enter your name when prompted and start chatting.
Type "exit" to disconnect from the server.

Future Improvements:
Implement encryption for secure communication.
Add a GUI for a better user experience (using JavaFX or Swing).
Implement a database to store user data and chat history.
Contributing:
Feel free to open issues or submit pull requests for any improvements or bug fixes!
