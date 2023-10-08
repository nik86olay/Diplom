package com.nikolay_netology.diplom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_data")
public class UserData implements UserDetails {

    @Id
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 50, message = "Не меньше 2 знаков и не более 50")
    private String login;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 50, message = "Не меньше 2 знаков и не более 50")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FileData> userFiles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
