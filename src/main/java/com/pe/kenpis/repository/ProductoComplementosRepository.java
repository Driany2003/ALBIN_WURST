package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoComplemetosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoComplementosRepository extends JpaRepository<ProductoComplemetosEntity, Integer> {


  /*
select TPC.emp_id, TE.emp_razon_social, TPC.pro_comp_id, TPC.pro_comp_nombre, TPC.pro_comp_precio, TPC.pro_comp_id_padre
from T_PRODUCTO_COMPLEMENTOS TPC inner join dbo.T_EMPRESA TE on TPC.emp_id = TE.emp_id
WHERE TPC.pro_comp_id_padre = 0
GROUP BY TPC.emp_id, TE.emp_razon_social, TPC.pro_comp_id, TPC.pro_comp_nombre, TPC.pro_comp_precio, TPC.pro_comp_id_padre; */
}
