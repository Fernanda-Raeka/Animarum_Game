package com.fernanda.backend.repository;

import com.fernanda.backend.model.StageDialogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageDialogueRepository extends JpaRepository<StageDialogue, Integer> {

    @Query("SELECT d FROM StageDialogue d WHERE d.stage.stage_id = :stageId ORDER BY d.sequence_order ASC")
    List<StageDialogue> findDialoguesByStageId(@Param("stageId") Integer stageId);
}