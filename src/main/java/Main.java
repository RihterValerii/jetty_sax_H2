import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.DropServlet;
import servlets.InputServlet;
import servlets.OutputServlet;

public class Main {
    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new InputServlet()), "/jobs");
        context.addServlet(new ServletHolder(new OutputServlet()), "/statistics");
        context.addServlet(new ServletHolder(new DropServlet()), "/cleanTable");

        ResourceHandler resource_handler = new ResourceHandler();

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8083);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started on port 8083");
        server.join();
    }
}
