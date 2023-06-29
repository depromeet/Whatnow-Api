package com.depromeet.whatnow.domains.interaction.service

import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.interaction.adapter.InteractionAdapter
import com.depromeet.whatnow.domains.interaction.domain.Interaction
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.helper.CunCurrencyExecutorHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import java.util.concurrent.atomic.AtomicLong

@DomainIntegrateSpringBootTest
@TestPropertySource(properties = ["ableCheckUserParticipation=false"])
class InteractionDomainServiceTest {
    @Autowired lateinit var interactionDomainService: InteractionDomainService

    @Autowired lateinit var interactionAdapter: InteractionAdapter

    @Test
    fun `분산락 적용시 동시요청에 올바르게 카운트가 증가해야한다`() {
        // given
        interactionAdapter.save(Interaction(InteractionType.HEART, 1L, 1L, 0L))
        val successCount = AtomicLong()

        // when
        CunCurrencyExecutorHelper.execute(
            { interactionDomainService.increment(1L, 1L, InteractionType.HEART) },
            successCount,
        )

        // then
        val interaction = interactionAdapter.queryInteraction(1L, 1L, InteractionType.HEART)
        assertThat(interaction.count).isEqualTo(successCount.toLong())
    }
}
