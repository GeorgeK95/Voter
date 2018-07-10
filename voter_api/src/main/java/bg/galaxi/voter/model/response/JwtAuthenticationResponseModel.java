package bg.galaxi.voter.model.response;

public class JwtAuthenticationResponseModel {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseModel(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
