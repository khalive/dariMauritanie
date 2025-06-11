// package com.DariM.darim.repository;

// import com.DariM.darim.model.User;
// import com.DariM.darim.model.User.Role;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByEmail(String email);
//     Optional<User> findByResetToken(String resetToken);
//     List<User> findByRole(Role role);  
// }

package com.DariM.darim.repository;

import com.DariM.darim.model.User;
import com.DariM.darim.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
    List<User> findByRole(Role role);
    boolean existsByEmail(String email); // Ajout de la méthode manquante
}