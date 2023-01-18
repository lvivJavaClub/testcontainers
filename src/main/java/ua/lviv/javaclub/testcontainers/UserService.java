package ua.lviv.javaclub.testcontainers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames={"users"})
public class UserService {
    final private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void init() {
        for(int i = 0; i < 5; i++) {
            var userEntity = UserEntity.builder().name(UUID.randomUUID().toString()).build();
            userRepository.save(userEntity);
        }
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @SneakyThrows
    @Cacheable
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        sleep(5000);
        return userRepository.findById(id);
    }
}
