package com.depromeet.whatnow.domains.invitecode.repository

import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import org.springframework.data.repository.CrudRepository

interface InviteCodeRepository : CrudRepository<InviteCodeRedisEntity, String>
