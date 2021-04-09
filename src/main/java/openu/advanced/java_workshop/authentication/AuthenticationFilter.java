package openu.advanced.java_workshop.authentication;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class filters the sessions ran by users from all other session, implementing the Filter interface
 */

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/secured/*" })
public class AuthenticationFilter implements Filter {

    /**
     * For Implementation of Filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * For Implementation of Filter
     */
    @Override
    public void destroy() {
    }

    /**
     * TODO - Document
     * @param servletRequest
     * @param servletResponse
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            HttpSession session = request.getSession(false);
            boolean isUserConnected = session != null && session.getAttribute("username") != null;
            if (isUserConnected)
                chain.doFilter(request, response);
            else
                response.sendRedirect("/login.xhtml");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}