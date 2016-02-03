package be.ovam.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


public class ExceptionHandler extends SimpleMappingExceptionResolver {

    private static final Logger myLogger = LoggerFactory.getLogger(ExceptionHandler.class);
    
    private String ajaxErrorView;
    private String ajaxDefaultErrorMessage = "An error has occurred";
    private boolean ajaxShowTechMessage = true;
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        
        myLogger.error("Een fout : ", e);
       
        if( isAjax(request) ) {
            String exceptionMessage = ajaxDefaultErrorMessage;
            if( ajaxShowTechMessage ) {
                exceptionMessage += "\n" + getExceptionMessage(e);
            }
            Map __MODEL__ = new HashMap();
            __MODEL__.put("server_exception_class", e.getClass().getName());
            __MODEL__.put("server_exception_msg", exceptionMessage);
            
            ModelAndView m = new ModelAndView(ajaxErrorView);
            m.addObject("__MODEL__", __MODEL__);
            
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return m;
        } else {
            //return super.resolveException(request, response, o, e);
            
            // Otherwise setup and send the user to a default error-view.
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", e);
            mav.addObject("url", request.getRequestURL());
            mav.setViewName("error-view");
            return mav;
        }
    }

    private String getExceptionMessage(Throwable e) {
        String message = "";
        while( e != null ) {
            message += e.getMessage() + "\n";
            e = e.getCause();
        }
        return message;
    }
    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public void setAjaxDefaultErrorMessage(String ajaxDefaultErrorMessage) {
        this.ajaxDefaultErrorMessage = ajaxDefaultErrorMessage;
    }

    public void setAjaxErrorView(String ajaxErrorView) {
        this.ajaxErrorView = ajaxErrorView;
    }

    public void setAjaxShowTechMessage(boolean ajaxShowTechMessage) {
        this.ajaxShowTechMessage = ajaxShowTechMessage;
    }

    public String getAjaxDefaultErrorMessage() {
        return ajaxDefaultErrorMessage;
    }

    public String getAjaxErrorView() {
        return ajaxErrorView;
    }

    public boolean isAjaxShowTechMessage() {
        return ajaxShowTechMessage;
    }
    
    
    
}