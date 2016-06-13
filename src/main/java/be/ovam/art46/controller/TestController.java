package be.ovam.art46.controller;

import be.ovam.art46.common.mail.MailService;
import be.ovam.art46.model.User;
import be.ovam.art46.service.BudgetRestService;
import be.ovam.art46.service.EsbService;
import be.ovam.art46.service.HandtekeningenService;
import be.ovam.art46.service.TestService;
import be.ovam.dms.alfresco.DmsUserAlfresco;
import be.ovam.dms.model.DmsFolder;

import org.alfresco.webservice.types.Node;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.net.URI;

@Controller
public class TestController {
	
    @Autowired
    private TestService testService;

    @Autowired
	private SqlSession sqlSession;

	@Autowired
	private MailService mailService;

	@Autowired
	private DmsUserAlfresco dmsAlfresco;

    @Autowired
    private BudgetRestService budgetRestService;
	
    @Autowired
    private EsbService esbService;
    
    @Autowired
    private HandtekeningenService handtekeningenService;
    

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() throws Exception {
		return "test";
	}

	@RequestMapping(value = "/test/setUserId", method = RequestMethod.GET)
	public @ResponseBody
	String setUserId(@RequestParam("userId") String userId, HttpSession session,  Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		user.setUser_id(userId);
		return user.getUser_id();
	}
	
	@RequestMapping(value = "/test/getUserId", method = RequestMethod.GET)
	public @ResponseBody
	String getUserId(HttpSession session,  Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		return user.getUser_id();
	}
	
    @RequestMapping(value = "/test/updateBrief/{brief_id}", method = RequestMethod.GET)
    public @ResponseBody String updateBrief(@PathVariable Integer brief_id, HttpSession session, Model model) throws Exception {
        testService.updateBrief(brief_id);
        return "gedaan";
    }

	@RequestMapping(value = "/testrunner", method = RequestMethod.GET)
	public String testRun(HttpSession session,  Model model) throws Exception {
		return "testrunner";
	}

	@RequestMapping(value = "/test/getMsg", method = RequestMethod.GET)
	public @ResponseBody
	String getMsg(@RequestParam("betreft") String betreft, @RequestParam("test") String test, HttpSession session,  Model model) throws Exception {		
		return "Bericht je " + betreft + " " + test;
	}
	@RequestMapping(value = "/test/getGetMsg", method = RequestMethod.GET)
	public @ResponseBody
	String getGetMsg(HttpSession session,  Model model) throws Exception {
		String response = null;
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("test-in.ovam.be").setPath("/pad/ws/test/getMsg")
		    .setParameter("betreft", "P & R : todo")
		    .setParameter("test", "testje");
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		HttpResponse response1 = httpclient.execute(httpGet);

		// The underlying HTTP connection is still held by the response object 
		// to allow the response content to be streamed directly from the network socket. 
		// In order to ensure correct deallocation of system resources 
		// the user MUST either fully consume the response content  or abort request 
		// execution by calling HttpGet#releaseConnection().

		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    response = EntityUtils.toString(entity1);
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity1);
		} finally {
		    httpGet.releaseConnection();
		}
		return response;
	}

	@RequestMapping(value = "/test/mail/send", method = RequestMethod.GET)
	public @ResponseBody
	String sendmail(HttpSession session,  Model model) throws Exception {
        String[] to = new String[] {"dvdveken@ovam.be", "dimitri.van.der.veken@ovam.be"};
        String message = "In dossier  " + "blabla" + " (" + "blabla" + ") werd voor " +
                " bestek " + "blabla" + " de volgende deelopdracht goedgekeurd: " + "blabla" + " (" + "blabla" +
                ")." + "<br>" + "<br>" +
                " Meer info over het bestek op: " + "http://bla/bla/URL" + "/s/bestek/" + "blabla";
		mailService.sendHTMLMail(to, "Pad test send mail" , "pad@ovam.be", message);		
		return "Mail verzonden";
	}
	
	@RequestMapping(value = "/test/getDmsObject", method = RequestMethod.GET)
	public @ResponseBody
	String getDmsObject(@RequestParam("pad") String pad) throws Exception {
		// vb pad = "/ivs/13/13008-2";
		try {
			Node node = dmsAlfresco.getObjectByPath(pad, null);
			return "Object gevonden : " + pad;
		} catch (Exception e) {
			return "Object NIET gevonden : " + pad;
		}
	}
    
	@RequestMapping(value = "/test/getNodeRefFor", method = RequestMethod.GET)
	public @ResponseBody
	String getNodeRefFor(@RequestParam("path") String path, @RequestParam("filename") String filename) throws Exception {
		// vb path=/Toepassingen/ivs/07/07194-1&filename=WEBLOKET
		return esbService.getNodeRefFor(path, filename, System.getProperty("ovam.dms.user"));
	}
    
	@RequestMapping(value = "/test/handtekeningBeschikbaar", method = RequestMethod.GET)
	public @ResponseBody
	String getHandtekeningBeschikbaar() throws Exception {
		return handtekeningenService.handtekenningBeschikbaar();
	}
    

	@RequestMapping(value = "/test/createDmsFolder", method = RequestMethod.GET)
	public @ResponseBody
	String createDmsFolder(@RequestParam("pad") String pad) throws Exception {
		// vb pad = "/ivs/13/13008-2/test";
		//DmsFolder dmsFolder = new DmsFolder(pad, "/", null);
		
		// vb pad = "/13/13008-2/test";
		DmsFolder dmsFolder = new DmsFolder(pad, "/ivs", null);
		String dmsId = dmsAlfresco.createFolder(dmsFolder, null);
		
		return "Folder aangemaakt : " + pad + ", dmsId : " + dmsId;
	}

    @RequestMapping(value = "/test/verzend/sv/{vordering_id}", method = RequestMethod.GET)
    public @ResponseBody
    String verzendSv(@PathVariable Integer vordering_id) throws Exception {
        budgetRestService.verzendSchuldvordering(vordering_id);
        return "gedaan";
    }
	
}
