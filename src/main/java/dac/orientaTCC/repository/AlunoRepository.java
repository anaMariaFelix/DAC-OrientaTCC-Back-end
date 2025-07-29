package dac.orientaTCC.repository;

import dac.orientaTCC.model.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a JOIN FETCH a.usuario u WHERE u.email = :email")
    Optional<Aluno> findByUsuarioEmail(@Param("email") String email);

    Optional<Aluno> findByMatricula(String matricula);

    Optional<Aluno> findByUsuarioId(Long usuarioId);
}
