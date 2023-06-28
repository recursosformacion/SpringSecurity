package com.rf.usuarios.security.jwtUtil;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.rf.usuarios.security.model.UsuarioSec;
import io.jsonwebtoken.*;

@Service
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${com.recursosformacion.secretKey}")
	private String secretKey;

	@Value("${com.recursosformacion.jwtCookie}")
	private String jwtCookie;

	@Value("${com.recursosformacion.roles}")
	private String rolesLit;

	@Value("${com.recursosformacion.vigencia-segundos}")
	private long vigenciaSeg;

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";

	@Autowired
	UserDetailsService userDetailsService;

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	public ResponseCookie generateJwtCookie(UsuarioSec userPrincipal) {
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		return ResponseCookie.from(jwtCookie, jwt)
				.path("/api")
				.maxAge(24 * 60 * 60)
				.httpOnly(true)
				.build();
	}

	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
		return cookie;
	}

	public String getUserNameFromJwtToken(String token) {
		if (token != null) {
			try {
				return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
			} catch (Exception e) {
				throw e;
			}
		}
		return null;
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(HttpServletRequest request) {
		return getClaimFromToken(request, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(HttpServletRequest request , Function<Claims, T> claimsResolver) {
		final Claims claims = obtenClaimsToken(request);
		return claimsResolver.apply(claims);
	}


	// check if the token has expired
	private Boolean isTokenExpired(HttpServletRequest request) {
		final Date expiration = getExpirationDateFromToken(request);
		return expiration.before(new Date());
	}

	
	/**
	 * Genera el token. Si se modifica la generacion, se ha de revisar la comprobacion en checkJWTToken
	 * @param username
	 * @return
	 */
	public String generateTokenFromUsername(String username) {
		UsuarioSec userDetails = (UsuarioSec) userDetailsService.loadUserByUsername(username);

		return Jwts.builder().setSubject(username)
				.claim(rolesLit,
						userDetails.getAuthorities()
						.stream()
						.map(GrantedAuthority::getAuthority)
						.toList())
				.setIssuedAt(new Date()).
				setExpiration(new Date((new Date()).getTime() + (vigenciaSeg * 1000)))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
				.compact();
	}

	/**
	 * Comprueba token repasando la estructura ,los roles recibidos y la caducidad
	 * @param request
	 * @param res
	 * @return
	 */
	public boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		if (!validateJwtToken(request)) return false;
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)				)
			return false;
		Claims claims = obtenClaimsToken(request);
		if (claims.get(rolesLit) != null) {
			setUpSpringAuthentication(claims);
			return true;
		} else {
			SecurityContextHolder.clearContext();
			return false;
		}		
	}
	public boolean validateJwtToken(HttpServletRequest request) {
		try {
			obtenClaimsToken(request);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	
	public String obtenToken(HttpServletRequest request) {
		String header = request.getHeader(HEADER);
		if (header ==null) {
			return "";
		}
		return header.replace(PREFIX, "");
	}

	public Claims obtenClaimsToken(HttpServletRequest request) {
		String jwtToken = obtenToken(request);
		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Autentificacion con los roles recibidos en el token	 * 
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {		
		List<String> authorities = (List<String>) claims.get(rolesLit);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream()
				.map(SimpleGrantedAuthority::new)
				.toList());
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
}
