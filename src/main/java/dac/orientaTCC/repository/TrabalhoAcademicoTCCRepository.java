package dac.orientaTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;

@Repository
public interface TrabalhoAcademicoTCCRepository extends JpaRepository<TrabalhoAcademicoTCC, Long> {

    @Query("SELECT t FROM TrabalhoAcademicoTCC t JOIN FETCH t.aluno a JOIN FETCH a.usuario WHERE a.matricula = :matricula")
    TrabalhoAcademicoTCC findByAlunoMatricula(@Param("matricula") String matricula);

    List<TrabalhoAcademicoTCC> findByOrientadorSiape(String orientador_Siape);

    TrabalhoAcademicoTCC findByAlunoId(Long id);

    TrabalhoAcademicoTCC findByOrientadorId(Long id);
}
