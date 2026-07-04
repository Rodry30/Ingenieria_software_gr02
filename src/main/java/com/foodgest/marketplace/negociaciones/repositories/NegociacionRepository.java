package com.foodgest.marketplace.negociaciones.repositories;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NegociacionRepository extends JpaRepository<Negociacion, UUID> {

    // JOIN FETCH evita el N+1 que disparaba NegociacionResponseDto.from(), que
    // accede a oferta.producto.getNombre() y comprador.getRazonSocial() por cada fila.
    @Override
    @Query("SELECT n FROM Negociacion n JOIN FETCH n.oferta o JOIN FETCH o.producto JOIN FETCH n.comprador")
    List<Negociacion> findAll();

    @Query("SELECT n FROM Negociacion n JOIN FETCH n.oferta o JOIN FETCH o.producto JOIN FETCH n.comprador WHERE n.oferta.id = :ofertaId")
    List<Negociacion> findByOfertaId(@Param("ofertaId") UUID ofertaId);

    @Query("SELECT n FROM Negociacion n JOIN FETCH n.oferta o JOIN FETCH o.producto JOIN FETCH n.comprador WHERE n.comprador.id = :compradorId")
    List<Negociacion> findByCompradorId(@Param("compradorId") UUID compradorId);

    @Query("SELECT n FROM Negociacion n JOIN FETCH n.oferta o JOIN FETCH o.producto JOIN FETCH n.comprador WHERE n.estado = :estado")
    List<Negociacion> findByEstado(@Param("estado") String estado);

    List<Negociacion> findByOfertaIdAndEstado(UUID ofertaId, String estado);

    List<Negociacion> findByCompradorIdAndEstado(UUID compradorId, String estado);
}
