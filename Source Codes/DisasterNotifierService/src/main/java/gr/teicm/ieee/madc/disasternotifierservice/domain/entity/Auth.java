package gr.teicm.ieee.madc.disasternotifierservice.domain.entity;

import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "access_tokens")
public class Auth extends BaseEntity {

    @Column(unique = true)
    @NonNull
    private String accessToken;

    @NonNull
    private Long expire;

    @ManyToOne(
            targetEntity = User.class,
            cascade = CascadeType.REMOVE,
            optional = false,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id")
    private User user;

    public Auth() {
    }

    public Auth(String accessToken, User user, Long expire) {
        this.accessToken = accessToken;
        this.user = user;
        this.expire = expire;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Auth)) {
            return false;
        }

        Auth tmp = (Auth) obj;

        return this.getId().equals(tmp.getId());
    }
}
