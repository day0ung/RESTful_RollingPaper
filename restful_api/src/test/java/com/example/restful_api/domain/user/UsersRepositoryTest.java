package com.example.restful_api.domain.user;

import com.example.restful_api.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@Import(TestConfig.class)
class UsersRepositoryTest {

    private String name = "test";
    private String email = "testEmail";
    private String password = "testpwd";
    private Role role = Role.USER;
    private Provider provider = Provider.LOCAL;
    private String providerId = "testproviderid";

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void 유저_등록() {
        //given
        final User users = User.builder()
                        .name(name).email(email).password(passwordEncoder.encode(password)).build();

        //when
        final User result = userRepository.save(users);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(name);

        // Verify that the password is encoded
        assertThat(password).isNotEqualTo(result.getPassword());
    }

    @Test
    public void 유저_등록_실패_이미_존재함(){
        //given
        final User user1 = User.builder()
                .name(name).email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user1);

        final User user2 = User.builder()
                .name(name).email(email).password(passwordEncoder.encode(password)).build();


        // when, then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user2);
        });

    }

    @Test
    public void 유저_조회_성공(){

        //given
        final User user = User.builder()
                .name(name).email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);

        //when
        final User foundUser = userRepository.findByEmail(email).orElse(null);

        //then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(name);
    }

    @Test
    public void 유저_조회_실패_정보가일치하지않음(){
        //given
        final User user = User.builder()
                .name(name).email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);

        //when
        final Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertThat(foundUser).isEmpty();

    }

}