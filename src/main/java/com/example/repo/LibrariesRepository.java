//package com.example.repo;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.entity.CrossStateLib;
//
//@Repository
//public interface LibrariesRepository extends JpaRepository<CrossStateLib, Long> {
//
//	@Query("Select c from CrossStateLib c order by c.id")
//	List<CrossStateLib> findByOrdered();
//
//}
