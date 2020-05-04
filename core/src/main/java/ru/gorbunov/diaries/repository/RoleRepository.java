package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gorbunov.diaries.domain.Role;

/**
 * Interface for generic CRUD operations with Roles of Users.
 *
 * @author Gorbunov.ia
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find a role by a description.
     *
     * @param description the description
     * @return role object
     */
    Role findByDescription(String description);

}
