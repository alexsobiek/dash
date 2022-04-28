# dash
High-performance, multithreaded Java HTTP server written using the Netty framework

### ⚠️ WIP
Dash is currently a work in progress and is not production ready. To access full functionality, you'll also have to
depend on Netty since methods in the `HttpRequest` and `HttpResponse` class currently directly expose Netty classes. This will
change by the time of the first release.

## Example
[See more](https://github.com/alexsobiek/dash/blob/main/example/src/main/java/com/alexsobiek/dash/example/Server.java)
```java
public class Server {
    private final Dash dash;
    private final InetSocketAddress address;
    private final HttpServer server;

    public Server() {
        dash = new Dash();
        address = new InetSocketAddress("localhost", 3000); // bind to localhost:3000
        server = dash.createServer(address); // Create HttpServer
        server.addHandler(new ExampleHandler()); // Add a handler
        listen(); // Start listening for requests
    }

    private void listen() {
        server.listen(future -> {
            if (future.isSuccess()) System.out.printf("Example HTTP server started on %s\n", address);
            else System.out.println(System.out.printf("Failed to start HTTP Server on %s\n", address));
        });
    }
}

class ExampleHandler implements RequestHandler {
    
    @GET(path = "/") // Get requests to "/"
    public void onGetRoot(HttpRequest req, HttpResponse res) {
        res.writeString("Homepage"); // Write string as response
    }
}
```
