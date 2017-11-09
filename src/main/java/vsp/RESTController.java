package vsp;


import vsp.utility.GETRequest;

import java.io.IOException;

public class RESTController {

    private static final String IP = "172.19.0.3";
    private static final String PORT = "5000";
    private static final String URL_STR = "http://" + IP + ":" + PORT;

    /**
     * Private constructor for utility classes.
     */
    private RESTController() {
    }

    public static void main(String[] args) {
        try {

            System.out.println("Output from Server .... \n");
            GETRequest request = new GETRequest(URL_STR);

            System.out.println(request.getResponse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
