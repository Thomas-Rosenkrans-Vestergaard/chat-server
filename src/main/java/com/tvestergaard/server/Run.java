package com.tvestergaard.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class Run
{

    public static void main(String[] args) throws Exception
    {
        String host = args.length > 0 ? args[0] : "localhost";
        int    port = args.length > 1 ? Integer.parseInt(args[1]) : 8887;

        ChatServer s = new ChatServer(new InetSocketAddress(host, port));
        s.start();
        System.out.println(String.format(
                "ChatServer started on host %s on port %d.",
                host,
                port));
        System.out.println("'exit' to stop");

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = systemIn.readLine();
            s.broadcast(in);
            if (in.equals("exit")) {
                s.stop(1000);
                break;
            }
        }
    }
}