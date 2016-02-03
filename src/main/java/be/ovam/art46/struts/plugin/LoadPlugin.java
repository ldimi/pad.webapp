package be.ovam.art46.struts.plugin;

import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.art46.util.converters.BigDecimalConverterArt46;
import be.ovam.art46.util.converters.DateConverterArt46;
import be.ovam.art46.util.converters.DoubleConverterArt46;
import be.ovam.art46.util.converters.StringConverterArt46;
import be.ovam.art46.util.menu.MenuHelper;
import be.ovam.art46.util.menu.MenuSluisHelper;
import be.ovam.web.util.json.JsonObjectMapperFactory;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.LabelValueBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class LoadPlugin implements PlugIn {

	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String url;
	public static ApplicationContext applicationContext;
    private static Log log = LogFactory.getLog(LoadPlugin.class);
	
	public void destroy() {
	} 

	public void init(ActionServlet servlet, ModuleConfig applicationConfig)
		throws ServletException {	

			applicationContext = WebApplicationContextUtils.getWebApplicationContext(servlet.getServletContext());
			
			String pad_versie = (String) applicationContext.getBean("pad_versie");
			servlet.getServletContext().setAttribute("pad_versie", pad_versie);
			log.info("pad_versie opgeslagen in applicatie scope : " + pad_versie);
			
            String build_profile = (String) applicationContext.getBean("build_profile");
            servlet.getServletContext().setAttribute("build_profile", build_profile);
            log.info("build_profile opgeslagen in applicatie scope : " + build_profile);

			String build_timestamp = (String) applicationContext.getBean("build_timestamp");
			servlet.getServletContext().setAttribute("build_timestamp", build_timestamp);
			log.info("build_timestamp opgeslagen in applicatie scope : " + build_timestamp);

			servlet.getServletContext().setAttribute("DDH", DropDownHelper.INSTANCE);
			servlet.getServletContext().setAttribute("menuHelper", MenuHelper.INSTANCE);
            servlet.getServletContext().setAttribute("menuSluisHelper", MenuSluisHelper.INSTANCE);
			servlet.getServletContext().setAttribute("APP", Application.INSTANCE);
			servlet.getServletContext().setAttribute("JsonMapper", JsonObjectMapperFactory.getMapper());
			
			Object[] jaartallen = new Object[8];
			jaartallen[0] = new LabelValueBean("", null);
			for (int t=2004; t<2011; t++) {
				jaartallen[t-2003] = new LabelValueBean("" + t, "" + t);	
			}	
			servlet.getServletContext().setAttribute("jaartallen", jaartallen);				
			log.debug("Jaartallen opgeslagen in applicatie scope onder jaartallen.");	
			ArrayList bestek_jaar =new ArrayList();			
			for (int t=1990; t<2000; t++) {
				bestek_jaar.add(new LabelValueBean("" + t, "" + (t - 1900)));	
			}
			for (int t=2000; t<2010; t++) {
				bestek_jaar.add(new LabelValueBean("" + t, "0" + (t - 2000)));	
			}
			for (int t=2010; t<2031; t++) {
				bestek_jaar.add(new LabelValueBean("" + t, "" + (t - 2000)));	
			}
			servlet.getServletContext().setAttribute("bestek_jaar", bestek_jaar.toArray());				
			log.debug("bestek_jaar opgeslagen in applicatie scope onder bestek_jaar.");
			ArrayList vastlegging_jaar =new ArrayList();			
			for (int t=1990; t<2031; t++) {
				vastlegging_jaar.add(new LabelValueBean("" + t, "" + t));	
			}			
			servlet.getServletContext().setAttribute("vastlegging_jaar", vastlegging_jaar.toArray());				
			log.debug("vastlegging_jaar opgeslagen in applicatie scope onder vastlegging_jaar.");
			Object[] provincies = new Object[6];
			provincies[0] = new LabelValueBean("","");
			provincies[1] = new LabelValueBean("Antwerpen","1");
			provincies[2] = new LabelValueBean("Limburg","7");
			provincies[3] = new LabelValueBean("Oost Vlaanderen","4");
			provincies[4] = new LabelValueBean("West Vlaanderen","3");
			provincies[5] = new LabelValueBean("Vlaams Brabant","2");
			servlet.getServletContext().setAttribute("provincies", provincies);				
			log.debug("Provincies opgeslagen in applicatie scope onder provincies.");				
			LabelValueBean[] pagesize = new LabelValueBean[5];
			pagesize[0] = new LabelValueBean("Alles","0");
			pagesize[1] = new LabelValueBean("10","10");
			pagesize[2] = new LabelValueBean("20","20");
			pagesize[3] = new LabelValueBean("50","50");
			pagesize[4] = new LabelValueBean("100","100");			
			servlet.getServletContext().setAttribute("pagesizes", pagesize);		
			log.debug("Pagesizes opgeslagen in applicatie scope onder pagesizes.");	
			LabelValueBean[] prioriteiten = new LabelValueBean[5];
			prioriteiten[0] = new LabelValueBean("","");
			prioriteiten[1] = new LabelValueBean("Prioriteit 1","1");
			prioriteiten[2] = new LabelValueBean("Prioriteit 2","2");
			prioriteiten[3] = new LabelValueBean("Prioriteit 3","3");
			prioriteiten[4] = new LabelValueBean("Prioriteit 4","4");
			servlet.getServletContext().setAttribute("prioriteitenlijst", prioriteiten);		
			log.debug("Prioriteiten opgeslagen in applicatie scope onder prioriteitenlijst.");
			LabelValueBean[] toestandsdata = new LabelValueBean[5];
			toestandsdata[0] = new LabelValueBean("01.01.2007", "01.01.2007");
			toestandsdata[1] = new LabelValueBean("01.01.2008", "01.01.2008");
			toestandsdata[2] = new LabelValueBean("01.01.2009", "01.01.2009");
			toestandsdata[3] = new LabelValueBean("01.01.2010", "01.01.2010");
			toestandsdata[4] = new LabelValueBean("01.01.2011", "01.01.2011");			
			servlet.getServletContext().setAttribute("toestandsdata", toestandsdata);
			List ramingdata = new ArrayList();
			for (int jaar = 2004; jaar< 2031; jaar++) {
				for (int maand=1; maand<13; maand++) {
					ramingdata.add(new LabelValueBean("01/" + StringUtils.leftPad("" + maand, 2 ,"0") + "/" + jaar, "" + maand + "/01/" + jaar));
				}
			}
			servlet.getServletContext().setAttribute("ramingdata", ramingdata);
			loadAlphabet(servlet.getServletContext());
			log.info("Plugin finished");	
			try {
				ConvertUtils.register(new DateConverterArt46(), Date.class);
				ConvertUtils.register(new DoubleConverterArt46(), Double.class);
				ConvertUtils.register(new BigDecimalConverterArt46(), BigDecimal.class);
				ConvertUtils.register(new StringConverterArt46(null), String.class);
				ConvertUtils.register(new LongConverter(null), Long.class);
				ConvertUtils.register(new IntegerConverter(null), Integer.class);
				
				//Locale.setDefault(Locale.FRANCE);
				log.info("Locale set to: " + Locale.getDefault());
			} catch (Exception e) {
				log.error("Error on loading converters: " + e.getMessage());
			}
			
	}
	
	private void loadAlphabet(ServletContext context) {
		ArrayList params = new ArrayList();
		for (int t=0; t< alphabet.length(); t++) {
			params.add(new LabelValueBean("" + alphabet.charAt(t) , "" + alphabet.charAt(t)));
		}	
		context.setAttribute("alphabet", params);
	}

}
