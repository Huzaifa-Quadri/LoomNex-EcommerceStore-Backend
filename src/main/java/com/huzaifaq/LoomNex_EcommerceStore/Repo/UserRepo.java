package com.huzaifaq.LoomNex_EcommerceStore.Repo;

import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
