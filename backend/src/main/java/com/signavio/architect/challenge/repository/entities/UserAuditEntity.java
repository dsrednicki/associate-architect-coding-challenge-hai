package com.signavio.architect.challenge.repository.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "useraudits")
public class UserAuditEntity extends BaseEntity {

    private String credential;

    private Boolean credentialsNonExpired;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> grantedAuthorities;

    @OneToOne(cascade = { CascadeType.ALL }, optional = false)
    private UserEntity user;

    public String getCredential() {
        return credential;
    }

    public void setCredential(final String credential) {
        this.credential = credential;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(final Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(final Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(final Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Set<String> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(final Set<String> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }
}
