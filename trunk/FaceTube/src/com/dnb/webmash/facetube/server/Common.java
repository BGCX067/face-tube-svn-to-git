package com.dnb.webmash.facetube.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Common {
    public static String appName = "FACETUBE";

	/**
	* Connects to a given URL and returns the response as a string.
	* @param url
	* @return
	*/
	    public static String requestStringAtUrl(URL url) throws IOException {
	        StringBuffer sb = new StringBuffer("");
	        InputStream is = url.openStream();
	        int n = 0;
	        do {
	            n = is.read();
	            if (n >= 0) {
	                sb.append((char) n);
	            }
	        } while (n >= 0);
	        is.close();
	        return sb.toString();
	    }
	    
	    /**
		 * Escape an html string. Escaping data received from the client helps to
		 * prevent cross-site script vulnerabilities.
		 * 
		 * @param html the html string to escape
		 * @return the escaped string
		 */
		private String escapeHtml(String html) {
			if (html == null) {
				return null;
			}
			return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
		}
}
