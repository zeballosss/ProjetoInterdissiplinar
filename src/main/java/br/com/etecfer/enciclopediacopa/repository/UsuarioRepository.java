package br.com.etecfer.enciclopediacopa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.etecfer.enciclopediacopa.model.Usuario;
import jakarta.persistence.LockModeType;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    // Serializa favoritos e avaliações feitos ao mesmo tempo pela mesma conta.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from Usuario u where u.id = :id")
    Optional<Usuario> buscarParaInteracao(@Param("id") Long id);
}
