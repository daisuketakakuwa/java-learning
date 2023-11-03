package jwt.auth.jwtauth.filter;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtFilter implements Filter {

    private static JwkProvider provider;

    public JwtFilter() {
        try {
            String jwksUrl = "http://localhost:3001/jwks";
            provider = new JwkProviderBuilder(new URL(jwksUrl)).build();
        } catch (Exception e) {
            System.out.println("failed to connect to JwkProvider.");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("########## Initiating Jwtfilter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorizaionHeader = request.getHeader("Authorization");
        if (authorizaionHeader != null) {
            Boolean bToken = false;
            String sMessage = "";
            try {
                String token = authorizaionHeader.replaceFirst("Bearer ", "");
                DecodedJWT jwt = JWT.decode(token);
                // トークン内に格納されてるKeyIDに紐づく公開鍵を取得する。
                Jwk jwk = provider.get(jwt.getKeyId());
                // 署名アルゴリズムは「RS256」を選択。
                Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
                // 検証 -> NGであれば SignatureVerificationException が投げられる。
                algorithm.verify(jwt);
                bToken = true;
            } catch (Exception e) {
                sMessage = e.getMessage();
                bToken = false;
            }

            if (bToken) {
                // call next filter in the filter chain
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "not authorized -> " + sMessage);
                return;
            }
        } else {
            response.sendError(401, "not authorized -> " + "No authorization header found.");
            return;
        }
    }
}
