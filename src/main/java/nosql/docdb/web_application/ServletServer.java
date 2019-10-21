package nosql.docdb.web_application;

import com.vaadin.server.VaadinServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServletServer {
    public static void startServer(){
        Server server = new Server(8181);

        ServletContextHandler vaadinHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        vaadinHandler.setContextPath("/application");
        ServletHolder vaadinPages = new ServletHolder(new VaadinServlet());
        vaadinHandler.addServlet(vaadinPages, "/*");
        vaadinHandler.setInitParameter("ui", SimplePage.class.getCanonicalName());

        ContextHandler resourceContextHandler=new ContextHandler("/static/*");
        ResourceHandler resourceHandler=new ResourceHandler();
        resourceHandler.setResourceBase("static");
        resourceHandler.setDirectoriesListed(true);
        resourceContextHandler.setHandler(resourceHandler);

        HandlerList handlers=new HandlerList();
        handlers.setHandlers(new Handler[]{vaadinHandler,resourceContextHandler});
        server.setHandler(handlers);


        try {
            server.start();
            server.join();

        } catch (Exception ex) {
            Logger.getLogger(ServletServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
