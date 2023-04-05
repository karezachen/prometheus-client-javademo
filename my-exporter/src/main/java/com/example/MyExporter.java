import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;

public class MyExporter {
	public static void main(String[] args) throws Exception {
		// Register JVM metrics.
		DefaultExports.initialize();

		// Start the HTTP server.
		HTTPServer server = new HTTPServer(8080);
	}
}
