package web;

import nlp.ChatEngine;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class ChatServlet extends HttpServlet {
    private ChatEngine engine;

    public ChatServlet(ChatEngine engine) {
        this.engine = engine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userInput = req.getParameter("message");
        if (userInput == null || userInput.trim().isEmpty()) {
            userInput = "Hello"; // or any default fallback
        } else {
            userInput = userInput.trim();
        }
        String reply = engine.getResponse(userInput);


        resp.setContentType("text/plain");
        resp.getWriter().write(reply);
    }
}
