package com.alexsobiek.dash.example;

import com.alexsobiek.dash.Dash;
import com.alexsobiek.dash.net.HttpServer;
import com.alexsobiek.dash.request.HttpRequest;
import com.alexsobiek.dash.request.HttpResponse;
import com.alexsobiek.dash.request.RequestHandler;
import com.alexsobiek.dash.request.annotation.GET;

import java.net.InetSocketAddress;

public class Server{
    private final Dash dash;
    private final InetSocketAddress address;
    private final HttpServer server;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        dash = new Dash();
        address = new InetSocketAddress("localhost", 3000);
        server = dash.createServer(address);
        server.addHandler(new ExampleHandler());
        listen();
    }

    private void listen() {
        server.listen(future -> {
            if (future.isSuccess()) System.out.printf("Example HTTP server started on %s\n", address);
            else System.out.printf("Failed to start HTTP Server on %s\n", address);
        });
    }
}

class ExampleHandler implements RequestHandler {
    @GET(path = "/")
    public void onGetRoot(HttpRequest req, HttpResponse res) {
        res.writeString("Homepage");
    }
}
