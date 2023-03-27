package Control;

import Graphic.ChatGraphicInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatClientProtocol implements Runnable{
    private static final int chatportnumber=5020;

    private static final String groupIp="203.0.113.0";

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

    private static ChatGraphicInterface chats;

    public void begin(ChatGraphicInterface chat) throws IOException {
        chats=chat;
        this.run();
    }

    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(group);
            byte[] buffer=new byte[1024];
            while (running){
                DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                chats.add
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
