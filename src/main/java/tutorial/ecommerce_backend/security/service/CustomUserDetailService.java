package tutorial.ecommerce_backend.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.entity.LocalUser;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private LocalUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = userDao.findByEmailOrUsername(username, username);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            // Chuyển danh sách vai trò thành danh sách SimpleGrantedAuthority
            List<SimpleGrantedAuthority> authorities = List.of(user.getRole().getName())
                                                          .stream()
                                                          .map(SimpleGrantedAuthority::new)
                                                          .collect(Collectors.toList());
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("Người dùng không tìm thấy");
    }
}
