package server;

import nlp.ChatEngine;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import web.ChatServlet;

public class WebServer {
    public static void startServer() throws Exception {
        ChatEngine engine = new ChatEngine("data/faq.json");
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new ChatServlet(engine)), "/chat");
        context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/*").setInitParameter("resourceBase", "web");

        server.start();
        server.join();
    }
}