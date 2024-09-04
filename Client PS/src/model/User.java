package model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String email;
    private Integer score;
    private String password;
    private UserType role;

    public User() {
        this.name = "";
        this.email = "";
        this.score = 0;
        this.password = "";
        this.role = UserType.PLAYER;
    }

    public User(String name, String email, Integer score, String password) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.password = password;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return this.name + " -> " + this.score;
    }

    public boolean equals(Object o){
        if (o instanceof User)
            return this.email.equals(((User) o).email);
        return false;
    }

}
