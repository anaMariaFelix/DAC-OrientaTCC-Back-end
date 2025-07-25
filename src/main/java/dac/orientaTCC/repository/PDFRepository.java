package dac.orientaTCC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dac.orientaTCC.model.entities.PDF;

@Repository
public interface PDFRepository extends JpaRepository<PDF, Long>{

}
