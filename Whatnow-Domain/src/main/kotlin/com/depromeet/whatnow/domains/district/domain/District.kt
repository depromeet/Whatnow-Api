package com.depromeet.whatnow.domains.district.domain
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "reversegeo")
data class District(
    @Id
    val id: String,
    val type: String,
    val properties: Properties,
    val geometry: Geometry,
) {
    data class Properties(
        val OBJECTID: Int,
        val adm_nm: String,
        val adm_cd: String,
        val adm_cd2: String,
        val sgg: String,
        val sido: String,
        val sidonm: String,
        val sggnm: String,
    )
    data class Geometry(
        val type: String,
        val coordinates: List<List<List<Double>>>,
    )
}
