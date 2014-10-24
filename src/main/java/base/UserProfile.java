package base;

/**
 * Created by smike on 13.09.14.
 */
public class UserProfile {

    private String password;
    private String email;

    public UserProfile(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UserProfile(UserProfile profile) {
        this.email = profile.email;
        this.password = profile.password;
    }

    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }

}
