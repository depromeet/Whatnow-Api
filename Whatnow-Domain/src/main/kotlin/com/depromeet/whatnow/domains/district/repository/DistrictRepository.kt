package com.depromeet.whatnow.domains.district.repository

import com.depromeet.whatnow.domains.district.domain.District
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DistrictRepository: MongoRepository<District, String>