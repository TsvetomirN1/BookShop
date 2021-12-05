package finalproject.bookshop.model.binding;


import finalproject.bookshop.validator.UniqueEmail;
import finalproject.bookshop.validator.UniqueUserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {


    @NotBlank
    @Size(min=3, max=20,message = "Username must be minimum 3 characters. ")
    @UniqueUserName
    private String username;

    @NotNull
    @Size(min = 3, max = 15)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 4,max = 20)
    private String password;

    @NotNull
    @Size(min = 4,max = 20)
    private String confirmPassword;

    @NotNull
    @Email(message = "Enter Valid Email")
    @UniqueEmail
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
