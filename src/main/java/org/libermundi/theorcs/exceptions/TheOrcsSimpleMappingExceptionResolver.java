package org.libermundi.theorcs.exceptions;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class TheOrcsSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected String findMatchingViewName(Properties exceptionMappings, Exception ex) {
		String viewName = null;
		String dominantMapping = null;
		int deepest = Integer.MAX_VALUE;
		for (Enumeration<?> names = exceptionMappings.propertyNames(); names.hasMoreElements();) {
			String exceptionMapping = (String) names.nextElement();
			int depth = getDepth(exceptionMapping, ex);
			if (depth >= 0 && (depth < deepest || (depth == deepest &&
					dominantMapping != null && exceptionMapping.length() > dominantMapping.length()))) {
				deepest = depth;
				dominantMapping = exceptionMapping;
				viewName = exceptionMappings.getProperty(exceptionMapping);
			} else if (ex.getCause() instanceof Exception) {
				return findMatchingViewName(exceptionMappings, (Exception) ex.getCause());
			}
		}
		if (viewName != null && logger.isDebugEnabled()) {
			logger.debug("Resolving to view '" + viewName + "' for exception of type [" + ex.getClass().getName() +
					"], based on exception mapping [" + dominantMapping + "]");
		}
		return viewName;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
	    // Call super method to get the ModelAndView
	    ModelAndView mav = super.doResolveException(req, resp, handler, ex);
	        
	    // Make the full URL available to the view - note ModelAndView uses
	    // addObject() but Model uses addAttribute(). They work the same. 
	    mav.addObject("url", req.getRequestURL());
	    return mav;
	  }
	

}
