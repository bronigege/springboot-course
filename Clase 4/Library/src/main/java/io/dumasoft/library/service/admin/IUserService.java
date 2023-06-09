package io.dumasoft.library.service.admin;

import io.dumasoft.library.models.entity.Author;
import io.dumasoft.library.models.entity.admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    public List<User> findAll();

    public void save(User user);

    public User findOne(Long id);

    public void delete(Long id);

    public Page<User> findAll(Pageable pageable);

    public User findByUsername(String username);
}
