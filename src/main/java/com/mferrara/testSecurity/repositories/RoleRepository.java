package com.mferrara.testSecurity.repositories;

import com.mferrara.testSecurity.auth.ERole;
import com.mferrara.testSecurity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Query(value = "select count(*) from role",
            nativeQuery = true)
    int isRoleEmpty();

}