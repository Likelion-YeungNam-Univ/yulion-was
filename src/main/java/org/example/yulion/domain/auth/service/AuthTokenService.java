package org.example.yulion.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yulion.domain.auth.dto.request.LoginRequest;
import org.example.yulion.domain.auth.dto.response.TokenDto;
import org.example.yulion.domain.auth.exception.AuthException;
import org.example.yulion.domain.auth.exception.AuthExceptionType;
import org.example.yulion.domain.user.domain.User;
import org.example.yulion.domain.user.repository.UserRepository;
import org.example.yulion.global.auth.TokenFacade;
import org.example.yulion.global.auth.userdetails.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class AuthTokenService {

    private final AuthenticationManager authenticationManager;
    private final TokenFacade tokenFacade;

    private final UserRepository userRepository;


    /**
     * 로그인 요청이 오면 이메일과 비밀번호로 확인 후, 인증에 성공한 경우 토큰 DTO를 반환하는 함수
     *
     * @param loginRequest - 로그인 처리를 위한 정보
     * @return - 토큰 DTO
     */
    public TokenDto login(final LoginRequest loginRequest) {

        final CustomUserDetails userDetails = setAuthentication(loginRequest);
        log.info("1");
        final String userId = userDetails.getId().toString();

        log.info("로그인 성공. Member ID : {}", userId);

        return tokenFacade.generate(userId);
    }


    /**
     * 기존의 유효한 Refresh 토큰으로 새로운 Access 토큰과 Refresh 토큰을 발급하는 함수
     *
     * @param requestRefreshToken - 토큰 재발급 정보
     * @return - 재발급된 토클들 반환
     */
    public TokenDto refreshTokens(final String requestRefreshToken) {
        return tokenFacade.refreshTokens(requestRefreshToken);
    }


    private CustomUserDetails setAuthentication(final LoginRequest loginRequest) {
        final Long userId = findUserIdByEmail(loginRequest.email());
        log.info(userId.toString());
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userId, loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (CustomUserDetails) authentication.getPrincipal();
    }

    private Long findUserIdByEmail(final String email) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.INVALID_CREDENTIALS));
        return user.getId();
    }
}
