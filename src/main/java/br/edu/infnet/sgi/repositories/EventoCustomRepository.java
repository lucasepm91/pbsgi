package br.edu.infnet.sgi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.infnet.sgi.models.Evento;

public interface EventoCustomRepository extends JpaRepository<Evento, Long> {

	@Query(value = "SELECT * FROM eventos ev INNER JOIN usuarios u ON ev.usuario_id = u.id WHERE u.id = ?#{#id}", nativeQuery = true)
	List<Evento> obterHistoricoEventos(@Param("id")long id);
}
