package com.depromeet.whatnow.domains.promiseactive.repository

import com.depromeet.whatnow.domains.promiseactive.domain.PromiseActiveRedisEntity
import org.springframework.data.repository.CrudRepository

interface PromiseActiveRepository : CrudRepository<PromiseActiveRedisEntity, String>
