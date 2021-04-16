package openu.advanced.java_workshop.authentication;

import javax.servlet.*;
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
     * This function filters all servlet requests that are not sent by a content user and
     * redirects unauthorized requests to login.
     *
     * @param servletRequest the request
     * @param servletResponse response object for redirect
     * @param chain the filter chain of the request
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