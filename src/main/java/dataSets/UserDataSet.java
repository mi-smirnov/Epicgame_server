package dataSets;

import javax.persistence.*;

/**
 * Created by smike on 08.11.14.
 */



@Entity
@Table(name = "user")
public class UserDataSet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public UserDataSet(){

    }

    public UserDataSet(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
