@Grab(group = 'net.sourceforge.htmlunit', module = 'htmlunit', version = '2.12')


import com.gargoylesoftware.htmlunit.util.Cookie

Cookie cookie1 = new Cookie("", "", "")
cookie1.setName('uid')
cookie1.domain = 'adform.net'
cookie1.value = 5332261958996383530L

Cookie cookie = [name: 'uid', domain: 'adform.net', value: 5332261958996383530L]
//Cookie cookie2 = [expires:new Date(), name:'uid', path:'/', domain:'adform.net', value:5332261958996383530, secure:false, httpOnly:false]


println cookie

