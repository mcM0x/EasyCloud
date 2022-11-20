package net.easycloud.session.factory;

import net.easycloud.session.CloudSession;
import net.easycloud.session.runner.CloudSessionRunner;

import java.net.Socket;

public class CloudSessionFactory {

    public static CloudSession create(Socket socket, CloudSessionRunner runner, int sessionId) {
        return new CloudSession(socket, runner, sessionId);
    }

}
