import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage

import static org.junit.Assert.*

WebClient webClient = new WebClient();
HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

String pageAsXml = page.asXml();
assertTrue(pageAsXml.contains("<body class=\"composite\">"));

String pageAsText = page.asText();
assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

webClient.closeAllWindows();
