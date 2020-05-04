package ru.gorbunov.diaries.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * User description.
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_user")
public class User extends GeneralEntity {

    /**
     * User login.
     */
    @NotNull
    @Size(min = 3, max = 32)
    @Column(unique = true, nullable = false, length = 32)
    private String login;

    /**
     * Hash of user password.
     */
    @NotNull
    @Size(min = 60, max = 60)
    @Column(nullable = false, length = 60)
    private String password;

    /**
     * User email.
     */
    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    /**
     * Indicator of user activate.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /**
     * Roles of user.
     */
    @ManyToMany
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(getId(), other.getId());
    }

}
