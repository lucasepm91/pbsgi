package br.edu.infnet.sgi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.infnet.sgi.models.Compra;

public interface CompraCustomRepository extends JpaRepository<Compra, Long> {

	@Query(value = "SELECT * FROM compras c INNER JOIN usuarios u ON c.usuario_id = u.id WHERE u.id = ?#{#id}", nativeQuery = true)
	List<Compra> obterHistoricoCompras(@Param("id")long id);
}
