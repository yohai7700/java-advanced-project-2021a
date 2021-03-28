package openu.advanced.java_workshop.authentication;

import openu.advanced.java_workshop.model.UsersEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AdminFilter", urlPatterns = { "/secured/admin/*" })
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    private boolean isUserAdminBySession(HttpSession session) {
        if(session == null) return false;
        UsersEntity user = (UsersEntity) session.getAttribute("user");
        if(user == null) return false;
        return user.getIsAdmin();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            HttpSession session = request.getSession(false);
            boolean isUserAdmin = isUserAdminBySession(session);
            if (isUserAdmin)
                chain.doFilter(request, response);
            else
                response.sendRedirect("/login.xhtml");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void destroy() {
    }
}
