@Grab(group = 'net.sourceforge.htmlunit', module = 'htmlunit', version = '2.12')

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.util.Cookie
import groovy.transform.Field

// todo: make it take an argument of a slot name or number
// todo: make it save state so same user can hit this or another site
// todo: make it show the ad shown, somehow. either the name of the file, or a url, or something.

def site = "file:///C:/Users/andrew/IdeaProjects/ad-hit/slot.html"

@Field WebClient client = new WebClient();
client.options.javaScriptEnabled = true
client.options.throwExceptionOnFailingStatusCode = false
client.options.throwExceptionOnScriptError = false
client.getPage(site);

showCookies("http://ih.adscale.de")

Set<Cookie> showCookies(String site) {
    println "showing cookies for $site"
    println "------------------------------"
    client.cookieManager.getCookies(new URL(site)).each {
        println it
    }
    println ""
}