import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client1(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();

                if (messageToSend.startsWith("/dm")) {
                    String[] messageParts = messageToSend.split(" ", 3);
                    if (messageParts.length == 3) {
                        String recipient = messageParts[1];
                        String privateMessage = messageParts[2];
                        bufferedWriter.write("/dm " + recipient + " " + privateMessage);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } else {
                        System.out.println("Usage: /dm <username> <message>");
                    }
                } else if (messageToSend.equalsIgnoreCase("/Exit")) {
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    System.out.println("You have left the chat.");
                    break;
                } else {
                    bufferedWriter.write(username + ": " + messageToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
            scanner.close();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        if (msgFromGroupChat == null) {
                            break;
                        }
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        break;
                    }
                }
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        try {
            Socket socket = new Socket("localhost", 1234);
            Client1 client = new Client1(socket, username);
            client.listenForMessage();
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
