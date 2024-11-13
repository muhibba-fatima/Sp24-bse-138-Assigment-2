import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	public static void main(String[] args){
		try{

			Socket socket = new Socket("192.168.223.71", 5000);
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			MessagingApp messagingApp = new MessagingApp();

			Thread recieveThread = new Thread(new ClientRunnable(dis, messagingApp));
			recieveThread.start();
Scanner sc=new Scanner(System.in);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                        System.out.println("Choose an option: \n1. Send Message\n2. Exit");
			while(true){
				
				//String operation = reader.readLine();
String operation = sc.nextLine();

				if("1".equals(operation)){
					System.out.println("Enter message");
					String clientMessage = reader.readLine();
					dos.writeUTF(clientMessage);
					dos.flush();
					messagingApp.addMessage(clientMessage,false);
				}else if ("2".equals(operation)){
					dos.writeUTF("exit");
					dos.flush();
					System.out.println("Client exiting...");
					break;
				}
			}
			displayMessageOptions(reader, messagingApp);
			dis.close();
			dos.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		}
		private static void displayMessageOptions(BufferedReader reader, MessagingApp messagingApp) throws IOException{
			while(true){
				System.out.println("\nChoose an opteration:\n1. Display All Messages\n2. Search Message\n3. Delete Message\n4. Display Unseen Messages\n5. Display Seen Messages\n6. Shuffle Messages\n7. Exit");
				String operation = reader.readLine();

				switch(operation){
					case "1":
					messagingApp.displayMessages();
					break;
					case "2":
					System.out.print("Enter MessageId to search: ");
					String searchID = reader.readLine();
					messagingApp.searchMessage(searchID);
					break;
					case "3":
					System.out.println("Enter Message Id to delete: ");
					String deleteID = reader.readLine();
					messagingApp.deleteMessage(deleteID);
					break;
					case "4":
					messagingApp.displayUnseenMessages();
					break;
					case "5":
					messagingApp.displaySeenMessages();
					break;
					case "6":
					messagingApp.shuffleMessages();
					break;
					case "7":
					System.out.println("Client Shutting Down");
					return;
					default:
					System.out.println("Invalid operation. Try again");
					
					}
				}
			}
		}
		class ClientRunnable implements Runnable{
			private DataInputStream dis;
			private MessagingApp messagingApp;
		
			public ClientRunnable(DataInputStream dis, MessagingApp messagingApp){
				this.dis = dis;
				this.messagingApp = messagingApp;
			}
			@Override
			public void run(){
			try{
				while(true){
					String serverMessage = dis.readUTF();
					if("exit".equalsIgnoreCase(serverMessage)){
						break;
					}
					System.out.println("Server: " + serverMessage);
                                        messagingApp.addMessage(serverMessage, true);
					}
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}