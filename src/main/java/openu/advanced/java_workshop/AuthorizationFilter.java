package openu.advanced.java_workshop;
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
import javax.servlet.http.HttpSession;



@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            boolean isUserConnected = (ses != null && ses.getAttribute("username") != null);
            boolean inLoginOrRegister = reqURI.contains("/login.xhtml") || reqURI.contains("register.xhtml");
            if (inLoginOrRegister || isUserConnected)
                chain.doFilter(request, response);
            else
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}