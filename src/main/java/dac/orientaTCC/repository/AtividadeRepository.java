package dac.orientaTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dac.orientaTCC.model.entities.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

	List<Atividade> findByTrabalhoId(Long trabalhoId);
}
