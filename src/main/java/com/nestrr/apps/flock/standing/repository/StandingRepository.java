package com.nestrr.apps.flock.standing.repository;

import com.nestrr.apps.flock.standing.entity.Standing;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface StandingRepository
    extends ListPagingAndSortingRepository<Standing, String>,
        ListCrudRepository<Standing, String> {}
