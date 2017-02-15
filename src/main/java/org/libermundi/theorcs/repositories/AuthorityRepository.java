package org.libermundi.theorcs.repositories;

import org.libermundi.theorcs.domain.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	Authority findByAuthority(String authority);

}
