package com.foodgest.auth;

import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntities usuario = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPasswordHash())
                .roles(mapTipoUsuarioToRole(usuario.getTipoUsuario()))
                .disabled(isAccountDisabled(usuario.getEstado()))
                .build();
    }

    private String mapTipoUsuarioToRole(String tipoUsuario) {
        if (tipoUsuario == null) {
            return "USER";
        }
        return switch (tipoUsuario) {
            case "agricultor" -> "AGRICULTOR";
            case "comprador" -> "COMPRADOR";
            case "transportista" -> "TRANSPORTISTA";
            case "admin" -> "ADMIN";
            default -> "USER";
        };
    }

    private boolean isAccountDisabled(String estado) {
        return !"activo".equals(estado);
    }
}
