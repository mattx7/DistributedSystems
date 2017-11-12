package vsp.api_client.utility;

import org.apache.log4j.Logger;
import vsp.api_client.http.HTTPRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BlackBoard {

    private static final Logger LOG = Logger.getLogger(HTTPRequest.class);

    /**
     * Private constructor for utility classes.
     */
    private BlackBoard() {
    }

    /**
     * @param remotePort
     * @return
     * @throws IOException
     */ // TODO LP WTF is that
    public static InetAddress getIP(int remotePort) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(remotePort);
        byte[] byteArray = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(byteArray, byteArray.length);
        datagramSocket.receive(receivePacket);
        datagramSocket.close();
        return receivePacket.getAddress();
    }

    // TODO getPort

}
