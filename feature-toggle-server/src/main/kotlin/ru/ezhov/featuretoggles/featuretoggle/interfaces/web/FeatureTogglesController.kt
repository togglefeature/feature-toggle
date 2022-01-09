package ru.ezhov.featuretoggles.featuretoggle.interfaces.web

import arrow.core.getOrHandle
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import ru.ezhov.featuretoggles.featuretoggle.domain.FeatureToggleRepository

private val logger = KotlinLogging.logger {}

@Controller
class FeatureTogglesController(
        private val featureToggleRepository: FeatureToggleRepository
) {
    @RequestMapping(value = ["/", "/feature-toggles"], method = [RequestMethod.GET])
    fun index(model: Model): String? {
        val toggles = featureToggleRepository
                .all()
                .getOrHandle { ex -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message) }

        logger.debug { toggles }

//        model.addAttribute("message", message)
        return "index"
    }
}