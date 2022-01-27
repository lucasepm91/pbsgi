package br.edu.infnet.sgi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.sgi.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>, EventoCustomRepository {
	List<Evento> findByTipoEvento(String tipoEvento);
	List<Evento> findByNome(String nome);
}
