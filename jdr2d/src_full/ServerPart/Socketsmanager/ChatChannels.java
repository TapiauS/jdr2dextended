package ServerPart.Socketsmanager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatChannels implements Runnable{
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

    public ChatChannels(){
        run();
    }



    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(group);
            while (running){
                byte[] buffer=new byte[1024];
                DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String msg=new String(packet.getData());
                System.out.println(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        new ChatChannels();
    }
}
