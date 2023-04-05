import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

public class MyExporter {
	private static final Counter requestsTotal = Counter.build()
		.name("my_requests_total")
		.help("Total number of requests.")
		.register();

	public static void main(String[] args) throws Exception {
		// Register default JVM metrics.
		DefaultExports.initialize();

		// Create a counter and register it.
		requestsTotal.inc();

		// Create an HTTP configuration that uses HTTPS.
		HttpConfiguration httpsConfig = new HttpConfiguration();
		httpsConfig.addCustomizer(new org.eclipse.jetty.server.SecureRequestCustomizer());

		// Create an SSL context factory.
		SslContextFactory sslContextFactory = new SslContextFactory.Server();
		sslContextFactory.setKeyStorePath("/root/ks/my-exporter.keystore");
		sslContextFactory.setKeyStorePassword("chenyiqiang");

		// Create a server.
		Server server = new Server();

		// Create an HTTP connector that uses HTTPS.
		ServerConnector httpsConnector = new ServerConnector(server,
				new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(httpsConfig));
		httpsConnector.setPort(8443);
		server.setConnectors(new Connector[] { httpsConnector });

		// Create a context for the metrics servlet.
		ServletContextHandler context = new ServletContextHandler();
		context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");

		// Create a context handler collection.
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { context });

		// Set the contexts to the server.
		server.setHandler(contexts);

		// Start the server.
		server.start();
		server.join();
	}
}
