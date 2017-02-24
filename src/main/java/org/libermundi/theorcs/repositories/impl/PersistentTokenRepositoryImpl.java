package org.libermundi.theorcs.repositories.impl;

import java.util.Date;

import org.libermundi.theorcs.domain.RememberMeToken;
import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {
	private static final Logger logger = LoggerFactory.getLogger(PersistentTokenRepositoryImpl.class); 
	private final RememberMeTokenRepository rememberMeTokenRepository;
	
	public PersistentTokenRepositoryImpl(RememberMeTokenRepository rememberMeTokenRepository) {
		this.rememberMeTokenRepository = rememberMeTokenRepository;
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		RememberMeToken tokenToSave = transform(token);
		if(logger.isDebugEnabled()) {
			logger.debug("Saving RememberMeToken : " + tokenToSave);
		}
		rememberMeTokenRepository.save(tokenToSave);

	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = rememberMeTokenRepository.findBySeries(series);
		token.setDate(lastUsed);
		token.setTokenValue(tokenValue);
		if(logger.isDebugEnabled()) {
			logger.debug("Updating RememberMeToken : " + token);
		}
		rememberMeTokenRepository.save(token);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return transform(rememberMeTokenRepository.findBySeries(seriesId));
	}

	@Override
	public void removeUserTokens(String username) {
		Iterable<RememberMeToken> listToDelete = rememberMeTokenRepository.findAllByUsername(username);
		if(logger.isDebugEnabled()) {
			logger.debug("Deleting RememberMeToken : " + listToDelete);
		}
		rememberMeTokenRepository.delete(listToDelete);
	}

	private static PersistentRememberMeToken transform(RememberMeToken token){
		if(token != null) {
			return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
		}
		return null;
	}

	private static RememberMeToken transform(PersistentRememberMeToken token){
		return new RememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
	}
	
}
