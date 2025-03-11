package com.nestrr.apps.flock.messaging.repository;

import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.messaging.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface EmailRepository
    extends ListPagingAndSortingRepository<Email, String>,
        ListCrudRepository<Email, String>,
        JpaRepository<Email, String> {}
