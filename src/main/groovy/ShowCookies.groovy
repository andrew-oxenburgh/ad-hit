@Grab(group = 'net.sourceforge.htmlunit', module = 'htmlunit', version = '2.12')
@Grab(group = 'commons-io', module = 'commons-io', version = '2.4')

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.util.Cookie
import groovy.json.JsonSlurper
import groovy.transform.Field

import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson
import static org.apache.commons.io.FileUtils.readFileToString
import static org.apache.commons.io.FileUtils.write


// todo: make it take an argument of a slot name or number
// todo: make it save state so same user can hit this or another site
// todo: make it show the ad shown, somehow. either the name of the file, or a url, or something.

def site = "file:///C:/Users/andrew/IdeaProjects/ad-hit/slot.html"

File userfile = new File("${System.getProperty('user.home')}/.t-is-for-toolbox/users/${username()}.usr")

@Field WebClient client = new WebClient();

turnOffSomeErrorLogging()

private Object turnOffSomeErrorLogging() {
    System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal")
};

client.options.javaScriptEnabled = true
client.options.throwExceptionOnFailingStatusCode = false
client.options.throwExceptionOnScriptError = false
//client.setHTMLParserListener(null)
String url = "http://ih.adscale.de"
if (userfile.exists()) {
    readFileIntoCookies(userfile, client)
}

hitSiteAndCheckUrlCookie(site, url)

String json = toJson(client.cookieManager.getCookies())


write(userfile, prettyPrint(json))

Set<Cookie> showCookies(String site) {
    println "showing cookies for $site"
    println "------------------------------"
    client.cookieManager.getCookies(new URL(site)).each {
        println it
    }
    println ""
}

private void hitSiteAndCheckUrlCookie(String site, String url) {
    println ''
    println ''
    println ''
    client.getPage(site);

    showCookies(url)
}

private String username() {
    String username = 'defaultUser'

    if (args.length > 0) {
        username = args[0]
    }
    username
}

private void readFileIntoCookies(File userfile, client) {
    JsonSlurper slurper = new JsonSlurper()
    def savedCookies = slurper.parseText(readFileToString(userfile))
    savedCookies.each {
        Map map ->
            Date expiresDate = map.expires != null ? Date.parse("yyyy-MM-dd'T'hh:mm:ssZ", map.expires) : null
            Cookie cookie = new Cookie(map.domain, map.name, map.value, map.path, expiresDate, map.secure, map.httpOnly)
            client.cookieManager.addCookie(cookie)
    }
}
