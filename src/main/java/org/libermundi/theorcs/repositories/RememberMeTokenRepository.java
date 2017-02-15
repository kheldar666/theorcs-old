package org.libermundi.theorcs.repositories;

import org.libermundi.theorcs.domain.RememberMeToken;
import org.springframework.data.repository.CrudRepository;

public interface RememberMeTokenRepository extends CrudRepository<RememberMeToken, Long> {
	public RememberMeToken findBySeries(String seriesId);
	
	public Iterable<RememberMeToken> findAllByUsername(String username);

}
