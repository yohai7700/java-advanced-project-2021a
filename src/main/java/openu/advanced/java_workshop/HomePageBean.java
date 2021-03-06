package openu.advanced.java_workshop;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class HomePageBean {
    String userName = "User Example";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
