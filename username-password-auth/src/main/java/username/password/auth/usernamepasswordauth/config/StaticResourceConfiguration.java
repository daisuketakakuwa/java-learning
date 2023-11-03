package username.password.auth.usernamepasswordauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1. DefaultServlet ではなく、Spring MVCのDispatcherServletが動作している。
 * 2. DispatcherServlet を経由して クラスパス(src/main/resources)配下の
 * 静的リソースにアクセスさせるために ResourceHandlerRegistry を設定する。
 * https://qiita.com/kazuki43zoo/items/e12a72d4ac4de418ee37
 */
@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // http://localhost:8080/loginSuccess.html で /static/loginSuccess.html へアクセス可能
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
