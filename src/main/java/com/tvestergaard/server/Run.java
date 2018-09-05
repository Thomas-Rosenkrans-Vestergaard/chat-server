package com.tvestergaard.server;

import com.tvestergaard.server.configuration.ChatServerConfigurer;
import com.tvestergaard.server.configuration.ConfigurationParser;
import com.tvestergaard.server.configuration.JsonConfigurationParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Run
{

    public static void main(String[] args) throws Exception
    {
        if (args.length < 1)
            throw new IllegalArgumentException("No configuration file specified.");

        ConfigurationParser  parser     = new JsonConfigurationParser();
        ChatServerConfigurer configurer = new ChatServerConfigurer(parser);
        ChatServer           chatServer = configurer.configure(args[0]);
        chatServer.start();
        System.out.println("ChatServer started. Submit 'exit' to stop");

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = systemIn.readLine();
            chatServer.broadcast(in);
            if (in.equals("exit")) {
                chatServer.stop(1000);
                break;
            }
        }
    }
}