package openu.advanced.java_workshop;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
public class LoginBean {

    private String password, username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
