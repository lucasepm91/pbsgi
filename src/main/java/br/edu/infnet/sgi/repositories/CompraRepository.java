package br.edu.infnet.sgi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.sgi.models.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>, CompraCustomRepository {

}
