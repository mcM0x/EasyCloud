package net.easycloud.session.sessionid;

import java.net.Socket;

public class SessionIdFactory {

    public static int create(Socket socket){
        return socket.getLocalPort();
    }

}
