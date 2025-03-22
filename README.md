# java-learning

|プロジェクト|内容|
|----|----|
|api-client|RestTemplateのリトライ処理実装|
|hikari-cp|HikariCP検証|
|jwt-auth|SpringBootでJWT検証するやり方<br>・パターン1(`com.auth0.java-jwt`,`com.auth0.jwks-rsa`)<br>・パターン2(`io.jsonwebtoken.jjwt-api`)|
|policy-api|・SpringBootApp → Logstash → Elasticsearch/Kibana連携<br>・SpringDataJpa/Specification実装<br>・テスト実装(RepositoryIntegration/ServiceUT/ControllerIntegration)|
|username-password-auth|SpringSecurity勉強<br>・UsernamePasswordAuthenticationFilterでPOST /login<br>・AuthenticationManager\<IF\> → ProviderManager\<Impl\><br>・AuthenticationProvider\<IF\> → DaoAuthenticationProvider等|
|validator<br> - javax-validator|`spring-boot-starter-validation(jakarta.validation)`検証|
|validator<br> - custom-validator|<br>・SpringBoot×Kotlinでのバリデージョン実装<br>・BusinessException実装<br>・`@ExceptionHandler`、`@ControllerAdvice`|

