import java.util.Random;
import java.util.ArrayList;

public class MessagingApp {

    private ArrayList<Message> messages;
    private static int globalMessageId = 1;

    public MessagingApp(){
        messages = new ArrayList<>();
    }

    public void addMessage(String messageContent, boolean status){
        String messageID = String.valueOf(globalMessageId++);
        messages.add(new Message(messageContent, messageID, status));
    }

    public void displayMessages(){
        System.out.println("Displaying all messages : ");
        for(Message m : messages){
            System.out.println(m.toString());
            System.out.println("-----------------------");
        }
    }

    public void searchMessage(String messageID){
        boolean found = false;
        for(Message m : messages){
            if(m.getMessageID().equals(messageID)){
                System.out.println("Message found.");
                System.out.println(m.toString());
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("Message not found.");
        }
    }

    public void deleteMessage(String messageID){
        boolean found = false;
        for(int i=0;i<messages.size();i++){
            if(messages.get(i).getMessageID().equals(messageID)){
                messages.remove(i);
                System.out.println("Message deleted successfully.");
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("Message not found.");
        }
    }

    public void displayUnseenMessages(){
        boolean hasUnseen = false;
        for(Message m : messages){
            if(!m.getStatus()){
                System.out.println(m.toString());
                System.out.println("-----------------------");
               
                hasUnseen = true;
            }
        }
        if(!hasUnseen){
            System.out.println("No unseen messages");
        }
    }

    public void displaySeenMessages(){
        boolean hasSeen = false;
        for(Message m : messages){
            if(m.getStatus()){
                System.out.println(m.toString());
                System.out.println("-----------------------");
                hasSeen = true;
            }
        }
        if(!hasSeen){
            System.out.println("No unseen messages");
        }
    }

    public void shuffleMessages(){
        Random rand = new Random();
        for(int i=messages.size()-1;i>0;i--) {
            int j = rand.nextInt(i + 1);
            Message temp = messages.get(i);
            messages.set(i, messages.get(j));
            messages.set(j, temp);
        }
            System.out.println("Shuffled messages : ");
            for(Message m : messages){
                System.out.println(m.toString());
                System.out.println("-----------------------");
            }
        }
    }