package org.springframework.samples.dwarf.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    @Query("SELECT user FROM User user ORDER BY user.estadistica.puntos DESC")
    List<User> findAllSortedByPuntuacion();
}
