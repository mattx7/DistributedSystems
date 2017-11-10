package vsp.api_client.utility;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BlackBoard {
    private static final Logger LOG = Logger.getLogger(RESTRequest.class);

    /**
     * Private constructor for utility classes.
     */
    private BlackBoard() {
    }

    /**
     * Method to get the ip address from the blackboard
     *
     * @param remotePort in int
     * @return INetAddress
     * @throws IOException
     */
    public static InetAddress getIP(int remotePort) throws IOException {
        // Datagrammsocket erzeugen mit dem angegebenen Port
        DatagramSocket datagramSocket = new DatagramSocket(remotePort);
        LOG.debug(String.format("Hey, Im now listen on port: %d", remotePort));
        // 1024 Bytes Dummy zum verschicken erzeugen (1024 Buffer)
        byte[] byteArray = new byte[1024];
        // Erzeugen eines neuen Datagrammes mit dem ByteArray (Byte-Puffer & Größe des Puffers)
        DatagramPacket receivePacket = new DatagramPacket(byteArray, byteArray.length);
        // Warten auf Ankunft eines Datagrammes (solange, bis ein Paket eintrifft)
        datagramSocket.receive(receivePacket);
        // Schließen des Sockets
        datagramSocket.close();
        // Liefert die InetAddress des Host, welches im DatagramPacket vermerkt wurde!
        return receivePacket.getAddress();
    }

    // TODO getPort
}
