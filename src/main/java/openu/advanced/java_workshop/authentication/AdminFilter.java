package openu.advanced.java_workshop.authentication;

import openu.advanced.java_workshop.model.UsersEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class filters the admins from all of the users, implementing the Filter interface
 */

@WebFilter(filterName = "AdminFilter", urlPatterns = { "/secured/admin/*" })
public class AdminFilter implements Filter {
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
     * Checks if a session is ran by a user who is an admin
     * @param session - the session of the user who we check for being admin
     * @return true if the session is ran by an admin and false otherwise
     */
    private boolean isUserAdminBySession(HttpSession session) {
        if(session == null) return false;
        UsersEntity user = (UsersEntity) session.getAttribute("user"); // The user who's executing the session
        if(user == null) return false;
        return user.getIsAdmin();
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
            boolean isUserAdmin = isUserAdminBySession(session);
            if (isUserAdmin)
                chain.doFilter(request, response);
            else
                response.sendRedirect("/login.xhtml");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
