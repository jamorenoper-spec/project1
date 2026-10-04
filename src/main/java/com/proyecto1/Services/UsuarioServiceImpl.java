package com.proyecto1.Services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto1.Entities.Usuario;
import com.proyecto1.Exception.BusinessException;
import com.proyecto1.Exception.ResourceNotFoundException;
import com.proyecto1.Repository.UsuarioRepository;
import com.proyecto1.Services.Interfaces.IUsuarioService;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService {

    // ========== INYECCION DE DEPENDENCIAS ==========
    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    // ====================== LOGS ======================
    private static final Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Cambia el password de un usuario. El nuevo password se guarda cifrado.
    @Override
    @Transactional
    public void cambiarPassword(String login, String nuevoPassword) {
        if (nuevoPassword == null || nuevoPassword.isBlank()) {
            logger.error("ERROR CAMBIAR_PASSWORD: EL NUEVO PASSWORD ES NULO O VACIO!");
            throw new BusinessException("El nuevo password es obligatorio");
        }
        Usuario usuario = buscarPorLogin(login);
        usuario.setPassword(passwordEncoder.encode(nuevoPassword));
        usuarioRepository.save(usuario);
    }

    // Genera un nuevo APIKey para el usuario y lo devuelve.
    @Override
    @Transactional
    public String regenerarApikey(String login) {
        Usuario usuario = buscarPorLogin(login);
        String nuevaApikey = UUID.randomUUID().toString();
        usuario.setApikey(nuevaApikey);
        usuarioRepository.save(usuario);
        return nuevaApikey;
    }

    private Usuario buscarPorLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new BusinessException("El login es obligatorio");
        }
        return usuarioRepository.findByLogin(login.trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con login " + login));
    }
}