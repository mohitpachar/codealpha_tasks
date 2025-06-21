# AI Chatbot (Java + Jetty Web Interface)

## How to Run
1. Ensure you have Java 8+ and Maven installed.
2. Compile the code and run `Main.java`.
3. Visit [http://localhost:8080](http://localhost:8080) in your browser.
4. Start chatting!

## Project Structure
- `faq.json`: Predefined FAQs
- `ChatEngine.java`: Core chatbot logic
- `ChatServlet.java`: Handles chat via HTTP
- `index.html`: Web interface

## Dependencies
- Jetty Server
- org.json (for JSON parsing)