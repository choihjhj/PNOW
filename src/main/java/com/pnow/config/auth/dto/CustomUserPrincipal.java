package com.pnow.config.auth.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //test용
@NoArgsConstructor()
public class CustomUserPrincipal implements OAuth2User, Serializable {
	private static final long serialVersionUID = 1L; //고정 UID, 역직렬화 오류 방지

	private Long id;
	private String name;
	private String email;
	private String picture;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public CustomUserPrincipal(
			Long id,
			String name,
			String email,
			String picture,
			Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> attributes
			) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return name;
	}

}
