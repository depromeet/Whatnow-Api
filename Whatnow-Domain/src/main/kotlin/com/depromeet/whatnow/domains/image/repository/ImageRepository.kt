package com.depromeet.whatnow.domains.image.repository

import com.depromeet.whatnow.domains.image.domain.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long>
