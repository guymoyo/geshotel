package org.adorsys.geshotel.domain.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.adorsys.geshotel.domain.RoleName;
import org.adorsys.geshotel.domain.UserAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="userAccountDetailsService")
@Transactional
public class UserAccountDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String identifier)
			throws UsernameNotFoundException, DataAccessException {
		
		try {

			if(StringUtils.isBlank(identifier)){
				throw new UsernameNotFoundException(
				"Estate user with email not found");
			}
			TypedQuery<UserAccount> typedQuery = UserAccount.findUserAccountsByIdentifierEquals(identifier);
			List<UserAccount> resultList = typedQuery.getResultList();
			UserAccount baseAdUser = null;
			for (UserAccount found : resultList) {
				baseAdUser = found;
			}
			if (baseAdUser == null)
				throw new UsernameNotFoundException(
				"Estate user with email not found");
			return createUserDetails(baseAdUser);
		} finally {

		}
	}

	public static UserDetails createUserDetails(UserAccount baseAdUser)
			throws UsernameNotFoundException, DataAccessException 
	{
		String password = baseAdUser.getPassword();
		boolean enabled = !baseAdUser.isDisableLogin();
		Date accountExpiration = baseAdUser.getAccountExpiration();
		boolean accountNonExpired = false;
		if (accountExpiration != null) {
			accountNonExpired = new Date().before(accountExpiration);
		}
		Date credentialExpiration = baseAdUser.getCredentialExpiration();
		boolean credentialsNonExpired = false;
		if (credentialExpiration != null) {
			credentialsNonExpired = new Date().before(credentialExpiration);
		}
		boolean accountNonLocked = !baseAdUser.isAccountLocked();

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<RoleName> roles = baseAdUser.getRoleNames();
		for (RoleName roleName : roles) {
			GrantedAuthorityImpl ga = new GrantedAuthorityImpl(
					roleName.name());
			authorities.add(ga);
		}
		UserDetails userDetails = new User(baseAdUser.getIdentifier(), password, enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities);
		return userDetails;
	}
}
