package yoon_hyojin.hackmate.global.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest {

    private TokenProvider tokenProvider;
    private static final String SECRET = "this_is_a_very_secret_key_for_hackMateProject";
    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(SECRET);
    }

    @Test
    @DisplayName("액세스 토큰 생성 후 이메일 추출 성공")
    void createToken_thenGetEmail() {
        String token = tokenProvider.createToken(EMAIL);
        assertThat(tokenProvider.getEmail(token)).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("리프레시 토큰 생성 후 이메일 추출 성공")
    void createRefreshToken_thenGetEmail() {
        String token = tokenProvider.createRefreshToken(EMAIL);
        assertThat(tokenProvider.getEmail(token)).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("유효한 토큰 검증 통과")
    void validateToken_validToken_returnsTrue() {
        String token = tokenProvider.createToken(EMAIL);
        assertThat(tokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 토큰 검증 실패")
    void validateToken_invalidToken_returnsFalse() {
        assertThat(tokenProvider.validateToken("invalid.token.string")).isFalse();
    }

    @Test
    @DisplayName("빈 문자열 토큰 검증 실패")
    void validateToken_emptyToken_returnsFalse() {
        assertThat(tokenProvider.validateToken("")).isFalse();
    }

    @Test
    @DisplayName("액세스 토큰과 리프레시 토큰은 서로 다름")
    void accessTokenAndRefreshToken_areDifferent() {
        String access = tokenProvider.createToken(EMAIL);
        String refresh = tokenProvider.createRefreshToken(EMAIL);
        assertThat(access).isNotEqualTo(refresh);
    }
}
