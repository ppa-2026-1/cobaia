package com.example.demo.model.port;

import java.util.List;

import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.Island.Disposition;

public interface IIslandRepository {
  
    List<Island> findByDisposition(Disposition disposition);

    List<Island> findIslandWithAvailableWorkstations();

    Island save(Island islandToAssignTo);
}
