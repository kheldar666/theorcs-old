package org.libermundi.theorcs.repositories;

import org.libermundi.theorcs.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
