package dac.orientaTCC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dac.orientaTCC.model.entities.Orientador;

@Repository
public interface OrientadorRepository extends JpaRepository<Orientador, Long> {

    Optional<Orientador> findByUsuarioEmail(String email);

    Optional<Orientador> findBySiape(String siape);

    Optional<Orientador> findByUsuarioId(Long usuarioId);
}
