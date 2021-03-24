package org.ishare.app.repositories;

import org.ishare.app.domains.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
}

