package username.password.auth.usernamepasswordauth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 本当はDBを参照してUserを探す。
        if (!username.equals("test")) {
            throw new UsernameNotFoundException(
                    new StringBuilder().append("username: ").append(username).append(" not found.").toString());
        }

        // 本当はDBに暗号化されたパスワードをそのままSetされる。
        String encodedPassword = encoder.encode("test");
        // UserDetails<IF> のSpring提供実装クラス Userを使う。
        return new User("test", encodedPassword, new ArrayList<>());
    }
}
