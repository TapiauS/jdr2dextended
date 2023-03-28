package Control;

import Graphic.ChatGraphicInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatClientProtocol extends Thread{
    private static final int chatportnumber=5020;

    private static final String groupIp="224.0.0.1";

    private static final InetAddress group;

    private static boolean running=true;

    static {
        try {
            group = InetAddress.getByName(groupIp);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static final MulticastSocket multicastSocket;

    static {
        try {
            multicastSocket = new MulticastSocket(chatportnumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ChatGraphicInterface chat;


    public ChatClientProtocol(ChatGraphicInterface chat) throws IOException {
        this.chat=chat;
        this.start();
    }

    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(group);
            byte[] buffer=new byte[1024];
            while (running){
                DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String msgrecu=new String(buffer);
                chat.addline(msgrecu);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendmessage(String message) throws IOException {
        byte[] msgbyte=message.getBytes();
        DatagramPacket packetsend=new DatagramPacket(msgbyte,msgbyte.length,group,chatportnumber);
        multicastSocket.send(packetsend);
    }


}
