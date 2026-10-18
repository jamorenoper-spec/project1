package com.proyecto1.labs.lab2.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto1.labs.lab2.entity.Usuario;
import com.proyecto1.labs.lab2.entity.UsuarioPK;
import com.proyecto1.labs.lab2.repository.UsuarioRepository;

@Service("lab2UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    // ========= INYECCIÓN DE DEPENDENCIAS =========
    @Autowired
    @Qualifier("lab2UsuarioRepository")
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Qualifier("lab2PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    // ==================== LOGS ====================
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private boolean pkInvalida(UsuarioPK id) {
        return id == null || id.getPersona() == null || id.getPersona() == 0
                || id.getLogin() == null || id.getLogin().isEmpty();
    }

    // INSERT
    @Override
    public boolean guardar(Usuario usuario) {
        try {
            if (usuario == null || pkInvalida(usuario.getId())) {
                logger.error("ERROR AGREGAR_USUARIO: EL USUARIO ES NULO O SU LOGIN/PERSONA ES INVÁLIDO");
                return false;
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            if (usuario.getApikey() == null || usuario.getApikey().isBlank()) {
                usuario.setApikey(UUID.randomUUID().toString());
            }
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_USUARIO: EL USUARIO NO SE HA GUARDADO", e);
            return false;
        }
    }

    // UPDATE
    @Override
    public boolean actualizar(Usuario usuario) {
        try {
            if (usuario == null || pkInvalida(usuario.getId())) {
                logger.error("ERROR EDITAR_USUARIO: EL USUARIO ES NULO O EL ID/LOGIN ES INVÁLIDO");
                return false;
            }
            Usuario actual = usuarioRepository.getUsuarioANDPersona(
                    usuario.getId().getLogin(), usuario.getId().getPersona());
            if (actual == null) {
                logger.error("ERROR EDITAR_USUARIO: EL USUARIO NO EXISTE");
                return false;
            }
            // Si no mandan password, se conserva; si mandan uno nuevo (no cifrado), se cifra
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(actual.getPassword());
            } else if (!usuario.getPassword().startsWith("$2")) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            if (usuario.getApikey() == null || usuario.getApikey().isBlank()) {
                usuario.setApikey(actual.getApikey());
            }
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            logger.error("ERROR EDITAR_USUARIO: EL USUARIO NO SE HA EDITADO", e);
            return false;
        }
    }

    // DELETE
    @Override
    public boolean eliminar(UsuarioPK id) {
        try {
            if (pkInvalida(id)) {
                logger.error("ERROR ELIMINAR_USUARIO: EL ID DEL USUARIO ES 0 O NULL");
                return false;
            }
            Usuario usuario = usuarioRepository.getUsuarioANDPersona(id.getLogin(), id.getPersona());
            if (usuario == null) {
                return false;
            }
            usuarioRepository.delete(usuario);
            return true;
        } catch (Exception e) {
            logger.error("ERROR ELIMINAR_USUARIO: EL USUARIO NO SE HA ELIMINADO", e);
            return false;
        }
    }

    // LISTA
    @Override
    public List<Usuario> consultarUsuario(Pageable pageable) {
        return usuarioRepository.findAll(pageable).getContent();
    }

    @Override
    public Usuario getUsuarioById(UsuarioPK id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario getUsuarioByLogin(String login) {
        List<Usuario> lista = usuarioRepository.findByUsername(login);
        return (lista == null || lista.isEmpty()) ? null : lista.get(0);
    }

    // Método exigido por la interface UserDetailsService (objetivo del Lab 2)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = getUsuarioByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                usuario.getId().getLogin(),
                usuario.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
