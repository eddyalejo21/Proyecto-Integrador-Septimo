package ec.gob.epmmop.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CorsFilter
 */
@WebFilter(urlPatterns = {"/api/*"})
public class CorsFilter implements Filter {

	public void destroy() {	
	}
	


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Authorization,Access-Control-Request-Method,Access-Control-Request-Headers,x-token");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "false");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT");
        httpServletResponse.addHeader("Access-Control-Max-Age", "10");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        String path = httpServletRequest.getRequestURI();
        if (path.contains("/api")) {
            chain.doFilter(request, response);
        }
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
