package vsp.utility;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BlackBoard {

    private static final Logger LOG = Logger.getLogger(GETRequest.class);

    /**
     * Private constructor for utility classes.
     */
    private BlackBoard() {
    }

    /**
     * @param remotePort 301
     * @return 121das
     * @throws IOException adas
     */
    public static InetAddress remoteIpAddress(int remotePort) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(remotePort);
        LOG.debug(String.format("Hey, Im now listen on port: %d", remotePort));
        byte[] byteArray = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(byteArray, byteArray.length);
        LOG.debug(String.format("Have created a new DatagramPacket-receivePacket: %s", receivePacket.getLength()));
        datagramSocket.receive(receivePacket);
        LOG.debug("DatagramSocket now receive the receivePacket!");
        datagramSocket.close();
        return receivePacket.getAddress();
    }

}
