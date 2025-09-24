package com.example.my_API.repository.lanzamiento;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.filter.lanzamientoFilter;
import com.example.my_API.repository.projection.ResumoLanzamiento;

public class lanzamientoRepositoryImpl implements lanzamientoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<lanzamiento> filtrar(lanzamientoFilter lanzamientoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<lanzamiento> criteria = builder.createQuery(lanzamiento.class);
        Root<lanzamiento> root = criteria.from(lanzamiento.class);

        Predicate[] predicates = criarRestricoes(lanzamientoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<lanzamiento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lanzamientoFilter));
    }

    @Override
    public Page<ResumoLanzamiento> resumir(lanzamientoFilter lanzamientoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoLanzamiento> criteria = builder.createQuery(ResumoLanzamiento.class);
        Root<lanzamiento> root = criteria.from(lanzamiento.class);

        // Adaptado a tus campos
        criteria.select(builder.construct(ResumoLanzamiento.class,
                root.get("codigo"),
                root.get("descripcion"),
                root.get("fechavencimiento"),
                root.get("fechapago"),
                root.get("valor"),
                root.get("tipo"),
                root.get("categoria").get("nombre"),
                root.get("personas").get("nombre")
        ));

        Predicate[] predicates = criarRestricoes(lanzamientoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoLanzamiento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lanzamientoFilter));
    }

    private Predicate[] criarRestricoes(lanzamientoFilter lanzamientoFilter, CriteriaBuilder builder,
                                        Root<lanzamiento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (lanzamientoFilter != null) {
            if (!StringUtils.isEmpty(lanzamientoFilter.getDescripcion())) {
                predicates.add(builder.like(
                        builder.lower(root.get("descripcion")),
                        "%" + lanzamientoFilter.getDescripcion().toLowerCase() + "%"
                ));
            }

            // Fechas adaptadas a "fechavencimiento"
            if (lanzamientoFilter.getFechavencimientoDe() != null) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get("fechavencimiento"), lanzamientoFilter.getFechavencimientoDe()
                ));
            }

            if (lanzamientoFilter.getFechavencimientoAte() != null) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get("fechavencimiento"), lanzamientoFilter.getFechavencimientoAte()
                ));
            }
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(lanzamientoFilter lanzamientoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<lanzamiento> root = criteria.from(lanzamiento.class);

        Predicate[] predicates = criarRestricoes(lanzamientoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}

