package com.stash.detekt

import com.stash.detekt.rules.CompanionObjectPosition
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class RuleSetProvider : RuleSetProvider {

    override val ruleSetId: String = "stash-rules"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                CompanionObjectPosition()
            )
        )
    }
}
