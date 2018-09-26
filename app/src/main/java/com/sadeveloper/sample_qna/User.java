package com.sadeveloper.sample_qna;

class User {
    private String id;
    private String username, email, gender,firstname,lastname;

    public User(String id, String username, String email, String gender, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
}

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
