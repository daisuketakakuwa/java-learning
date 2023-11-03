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
