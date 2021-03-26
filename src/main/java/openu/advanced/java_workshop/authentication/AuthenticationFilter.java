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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/secured/*" })
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) {
        try {

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession session = request.getSession(false);

            boolean isUserConnected = session != null && session.getAttribute("username") != null;
            if (isUserConnected)
                chain.doFilter(request, response);
            else
                resp.sendRedirect(request.getContextPath() + "login.xhtml");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void destroy() {
    }
}