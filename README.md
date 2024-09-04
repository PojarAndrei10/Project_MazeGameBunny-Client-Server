# Maze Game with Bunny - Client/Server
I developed an application for the following game: a maze of size nxn boxes is given in which there can be traps for wild animals and a "treasure" of carrots. In a corner of the maze is a hungry bunny. Help the bunny to reach the "treasure" of carrots so that he doesn't get caught in one of the traps, knowing that he can move by jumping diagonally in the boxes that share a top with the current box.

Architectural template: Client - Server
Communication between client and server is handled by sending and receiving serialized objects. Also on both the server side and the client side a Socket object is used for communication between them.

Programming language: Java

The graphical user interface was developed using Java Swing

Database: MySQL
