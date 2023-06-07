package com.depromeet.whatnow.domains.picture.repository

import com.depromeet.whatnow.domains.picture.domain.Picture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PictureRepository : JpaRepository<Picture, Long>
