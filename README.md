# Java Swing Chat Application

This is a simple Java Swing-based chat application that allows multiple clients to connect to a server and exchange messages in real-time. It uses a PostgreSQL database for user authentication and tracking online users.

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Server](#server)
- [Client](#client)
- [DataAccess](#dataaccess)
- [Usage](#usage)
- [Thoughts](#thoughts)
- [License](#license)

## Features

- **Server**: The server listens for incoming connections from clients and manages client communication. It handles multiple clients simultaneously using separate threads.
- **Client**: Clients can connect to the server, send messages, and receive messages from other clients. The user interface is built using Java Swing.
- **DataAccess**: Manages user authentication and tracks online users in a PostgreSQL database.

## Screenshots
![image](https://github.com/Chrisvasa/JavaChat/assets/29359169/c592aff4-97e3-4814-8fa0-d4545b2a9278)

![image](https://github.com/Chrisvasa/JavaChat/assets/29359169/abb618f0-f990-4438-be6d-0298b49a08fd)


## Getting Started

To get started with the chat application, you'll need to set up the server, client, and database components.

### Server
The server class is responsible for handling incoming client connections and managing client communication. The `Server` class listens on a specified port and creates a `ServerSocket` to accept incoming connections.
### Client 

The Client class represents a client connecting to the server. It sets up communication with the server through sockets and provides methods to send and receive messages. The user interface is built using Java Swing.

### DataAccess

The DataAccess class is used for database-related operations. It connects to a PostgreSQL database with user credentials and provides methods for user authentication and tracking online users.

## Usage
To use the chat application:
1. Setup a postgres database according to the SQL dump and update the connection string to match your database.
2. Compile and run the server.
3. Compile and run the client(s).
4. Clients can log in, send and receive messages in real-time.
5. The server handles client connections and broadcasts messages to all connected clients.

## Thoughts
I used this project as a small intro into the world of sockets and all that jazz. I will most likely revisit this project in the future once we touch on the networking subject in my studies.
Also, this was an introduction to Java Swing for me so the code might be overly messy but it does the job. (For now)
Doing this project I have learned way more about Sockets/Websockets and how the communication between them works. But there is still a lot of gray-areas which I do not understand. Overall, I felt that this was a fun small project to have and I will probably build a second version or update this in the future.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
