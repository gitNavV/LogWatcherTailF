# LogWatcherTailF
A log watching server implementation similar to `tail -f` in Java.

Further plan is to leverage WebSockets for client <-> server communications and Queues for logs.


This project implements a log watching solution (similar to the `tail -f` command in UNIX). However, in this case, the log file is hosted on a remote machine (same machine as your server code). The log file is in append-only mode.

The project implements the following:

A server side program to monitor the given log file which is capable of streaming updates that happen in it. This runs on the same machine as the log file. The server is implemented in Java. It also includes functionality to fetch last 10 lines of the log file.

Further plan is to include a web based client (accessible via URL like http://localhost/log) that prints the updates in the log file as and when they happen and 'NOT' upon page refresh. The page should be loaded once and it should keep getting updated in real-time. The user sees the last 10 lines in the file when he lands on the page.

Project Constraints -
- The server should push updates to the clients as we have to be as real time as possible.
- Log file may be of several GBs, server should be optimised for retrieving the last 10 lines.
- The server should not retransmit the entire file every time. It should only send the updates.
- The server should be able to handle multiple clients at the same time.
- The web page should not stay in loading state post the first load and it should not reload thereafter as well.
- We should not use any off-the-shelf external libraries or tools to read the file or provide tail-like functionalities.
