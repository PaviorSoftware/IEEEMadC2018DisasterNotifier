package gr.teicm.ieee.madc.disasternotifierservice.dto.entity;

public class AuthDTO extends BaseEntityDTO {


    private String accessToken;

    private Long expire;

    private UserDTO user;

    public AuthDTO() {
    }

    public AuthDTO(String accessToken, UserDTO user, Long expire) {
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}