package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Game;
import com.evoucherapp.evoucher.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT gm FROM Game gm WHERE gm.isDeleted = false ")
    List<Game> findAllGames();
}
