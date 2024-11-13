import java.io.*;
import java.net.*;
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5555);


            System.out.println("Server is waiting for a client....");

            Socket socket = serverSocket.accept();
            System.out.println("Client Connected!");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            MessagingApp messagingApp = new MessagingApp();

            Thread receiveThread = new Thread(new ServerRunnable(dataInputStream, messagingApp));
            receiveThread.start();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Choose an option : \n1.Send Message\n2.Exit");
            while(true){
                
                String operation = bufferedReader.readLine();
                if("1".equals(operation)){
                    System.out.println("Enter the message : ");
                    String message = bufferedReader.readLine();
                    dataOutputStream.writeUTF(message);
                    dataOutputStream.flush();
                    messagingApp.addMessage(message, false);
                }
                else if("2".equals(operation)){
                    dataOutputStream.writeUTF("Exit");
                    dataOutputStream.flush();
                    System.out.println("Server exiting...");
                    break;
                }
            }

            displayMessageOptions(bufferedReader, messagingApp);

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayMessageOptions(BufferedReader bufferedReader, MessagingApp messagingApp) throws IOException{

        while(true){
            System.out.println("Choose an option : \n1. Display All messages\n2. Search a message\n3. Delete message\n4. Shuffle messages\n5. Display Unseen Messages\n6. Display Seen Messages\n7. Exit");
            String operation = bufferedReader.readLine();
            switch(operation){
                case "1":
                    messagingApp.displayMessages();
                    break;
                case "2":
                    System.out.println("Enter message id to search : ");
                    String searchID = bufferedReader.readLine();
                    messagingApp.searchMessage(searchID);
                    break;
                case "3":
                    System.out.println("Enter message id to delete : ");
                    String deleteID = bufferedReader.readLine();
                    messagingApp.deleteMessage(deleteID);
                    break;
                case"4":
                    messagingApp.shuffleMessages();
                    break;
                case "5":
                    messagingApp.displayUnseenMessages();
                    break;
                case "6":
                    messagingApp.displaySeenMessages();
                    break;
                case "7":
                    System.out.println("Server shutting down.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");

            }

        }
    }

}

    class ServerRunnable implements Runnable{

        private DataInputStream dataInputStream;
        private MessagingApp messagingApp;

        public ServerRunnable(DataInputStream dataInputStream, MessagingApp messagingApp) {
            this.dataInputStream = dataInputStream;
            this.messagingApp = messagingApp;
        }

        @Override
        public void run() {

            try{
                while(true){
                    String clientMessage = dataInputStream.readUTF();
                    if("exits".equalsIgnoreCase(clientMessage)){
                        break;
                    }
                    messagingApp.addMessage(clientMessage, true);
                    System.out.println("Client : "+ clientMessage);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }