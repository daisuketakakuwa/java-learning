# How to call GET /hello with JWT

1. Run spring boot.

```
mvn clean install spring-boot:run
```

2. Run jwks server.

```
cd jwks-provider
npm install
node index.js
```

3. Open SwaggerUI. Visit http://localhost:8080/swagger-ui/index.html.

- Request with the following token.

```
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InRlc3QifQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.cJhvFV2N_-llnvNJLtRutGsTGxsnOwY8OvVGT-TUv9pO2LZuVGzFlOu3mPe5MZWpc_ZAhMudGnvSTPpGTWxE1yHrNu1hWfmO80R4VI83rusHeTcbhPgfnwAuqofKYZg_dYWkd5HQz1tTl062oqcnGYQ8GjXxNZI6UXdbe0Hd1SHdlg4G31h8Q9z_qChBTTjrFJcqXJNd2FbEAiX_LcD5e7aySxNi_1zq5LkONCY8qb5sNLgH-kZoazqts308GXb7zpULbSPXfcB2H3xvwXlvXCJLmpd6zVZzVK77Jxtjr5gZUV50QEsKuvBoYtofoPhfalUk8jOWT9APTf--uN9tTg
```

## How to create JWT and JWKS returned by JWKS endpoint.

1. Create JWT in https://jwt.io/.

- Be sure to select `RS256`as the Algorithm.
- Be sure to add `kid` in header.
  ![](https://storage.googleapis.com/zenn-user-upload/8df7d94f590e-20231103.png)

2. Convert a public key from PEM to JWK in https://irrte.ch/jwt-js-decode/pem2jwk.html.
   ![](https://storage.googleapis.com/zenn-user-upload/9812c84e4bcf-20231103.png)
   ↓ add "kid" in so that the application can get public key from JWKS.
   ![](https://storage.googleapis.com/zenn-user-upload/5454237b0fba-20231103.png)

## io.jsonwebtoken を使う場合

```xml
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-api</artifactId>
	<version>0.11.2</version>
</dependency>

<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-impl</artifactId>
	<version>0.11.2</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-jackson</artifactId>
	<version>0.11.2</version>
	<scope>runtime</scope>
</dependency>
```

- `JwtTokenProvider`クラス
    - `validateToken関数`
        - Request内のJWT文字列取得 → JWTオブジェクトへ変換
        - JWT有効期限をチェックする
    - `buildAuthentication関数`
        - Request内のJWT文字列取得 → JWTオブジェクトへ変換
        - JWTオブジェクトから、SecurityContext に保存する Authentication 生成

```java
package jp.english.hub.englishhubback.domain.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jp.english.hub.englishhubback.domain.user.UserInfo;

@Component
public class JwtTokenProvider implements TokenProvider {

    private Key secretkey;

    @PostConstruct
    protected void init() {
        secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // JWT(文字列) → Authenticationオブジェクトに変換
    // (UserDetails principal, "", Collection<? extends GrantedAuthority> roles)
    @Override
    public Authentication buildAuthentication(String token) {
        // PrincipalとRolesが欲しい。
        Jws<Claims> claims = parseClaimsJws(token);
        UserDetails userDetails = new UserInfo("id", claims.getBody().get("name").toString(),"password",
                claims.getBody().get("role").toString().split(","));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : claims.getBody().get("role").toString().split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // JWT(文字列)よりClaims(BODY部分)を取得
    private Jws<Claims> parseClaimsJws(String token) {
        return Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(token);
    }

}
```

```java
package jp.english.hub.englishhubback.domain.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        // リクエストからJWT(文字列)取得
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String token = httpServletRequest.getHeader("Authorization");

        if (token != null && tokenProvider.validateToken(token)) {
            // JWT(文字列) → Authenticationオブジェクト
            Authentication auth = tokenProvider.buildAuthentication(token);
            // SecurityContextHolderに保管※毎回入れる必要はないかも？
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 後続のフィルターにバトンタッチ
        filterChain.doFilter(req, res);
    }

}
````
