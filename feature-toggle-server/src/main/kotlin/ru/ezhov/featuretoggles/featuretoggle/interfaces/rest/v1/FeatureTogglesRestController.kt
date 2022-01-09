package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1

import arrow.core.getOrHandle
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineRepository
import ru.ezhov.featuretoggles.conditionengine.domain.model.Condition
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionConfiguration
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineDescription
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameter
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameterConfiguration
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParametersConfiguration
import ru.ezhov.featuretoggles.featuretoggle.domain.FeatureToggleRepository
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggle
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggleDescription
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggleInfo
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggleType
import ru.ezhov.featuretoggles.featuretoggle.domain.model.NewFeatureToggle
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionCheckerResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionConfigurationResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineCheckerRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineConfigurationRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineConfigurationResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineDescriptionRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineLanguageRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineLanguageResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineTypeRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEngineTypeResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.ConditionEnginesRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureToggleInfoRequestDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureToggleResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureToggleStateResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureToggleTypeResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureToggleTypesResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.FeatureTogglesResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.InputConditionParameterConfigurationResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.InputConditionParametersConfigurationResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.IsEnabledResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.NewFeatureToggleIdResponseDto
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.NewFeatureToggleRequestDto

@RestController
@RequestMapping(value = ["/api/"])
class FeatureTogglesRestController(
        private val conditionEngineRepository: ConditionEngineRepository,
        private val featureToggleRepository: FeatureToggleRepository
) {
    @RequestMapping(
            value = ["/v1/condition-engines"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun engines(): ConditionEnginesRequestDto {
        val engines = conditionEngineRepository.all().getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        }

        return ConditionEnginesRequestDto(conditionEngines = engines
                .map { e ->
                    ConditionEngineRequestDto(
                            type = e.type().toApiModel(),
                            language = e.language().toApiModel(),
                            description = e.description().toApiModel(),
                    )
                }
        )
    }

    @RequestMapping(
            value = ["/v1/feature-toggles"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun allFeatureToggles(): FeatureTogglesResponseDto {
        val toggles = featureToggleRepository.all().getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        }

        return FeatureTogglesResponseDto(featureToggles = toggles.map { ft -> ft.toApiModel() })
    }

    @RequestMapping(
            value = ["/v1/feature-toggles/{id}"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun featureToggle(@PathVariable("id") id: String): FeatureToggleResponseDto {
        val toggle = featureToggleRepository.byId(id).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Toggle with ID $id not found")

        return toggle.toApiModel()
    }

    @RequestMapping(
            value = ["/v1/feature-toggles"],
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createFeatureToggle(@RequestBody new: NewFeatureToggleRequestDto): NewFeatureToggleIdResponseDto {
        val newToggle = new.toDomainModel()

        if (newToggle.condition != null) {
            conditionEngineRepository
                    .by(newToggle.condition.type, newToggle.condition.language)
                    .getOrHandle { ex ->
                        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                    }
                    ?: throw ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Not found condition engine by type=${newToggle.condition.type} " +
                                    "language=${newToggle.condition.language}"
                    )
        }
        featureToggleRepository.save(FeatureToggle.from(newToggle)).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        }

        return NewFeatureToggleIdResponseDto(id = newToggle.id)
    }

    @RequestMapping(
            value = ["/v1/feature-toggles/{id}"],
            method = [RequestMethod.PUT],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun updateFeatureToggle(@PathVariable("id") id: String, @RequestBody info: FeatureToggleInfoRequestDto) {
        val toggle = featureToggleRepository.byId(id).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Toggle with ID $id not found")

        featureToggleRepository
                .save(toggle.update(info.toDomainModel()))
                .getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                }
    }

    @RequestMapping(
            value = ["/v1/feature-toggles/{id}/state"],
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateFeatureToggleState(@PathVariable("id") id: String): FeatureToggleStateResponseDto {
        val toggle = featureToggleRepository.byId(id).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Toggle with ID $id not found")

        val toggleChanged = toggle.changeState()

        featureToggleRepository
                .save(toggleChanged)
                .getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                }

        return FeatureToggleStateResponseDto(state = toggleChanged.enabled)
    }

    @RequestMapping(value = ["/v1/feature-toggles/{id}"], method = [RequestMethod.DELETE])
    fun deleteFeatureToggle(@PathVariable("id") id: String) {
        featureToggleRepository.byId(id).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Toggle with ID $id not found")

        featureToggleRepository
                .delete(id)
                .getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                }
    }

    @RequestMapping(
            value = ["/v1/condition-checker"],
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun checkCondition(@RequestBody conditionEngineConfiguration: ConditionEngineCheckerRequestDto):
            ConditionCheckerResponseDto {
        val type = conditionEngineConfiguration.type.toDomainModel()
        val language = conditionEngineConfiguration.language.toDomainModel()
        val conditionConfig = conditionEngineConfiguration.toDomainModel()

        val engine = conditionEngineRepository
                .by(
                        type = type,
                        language = language
                )
                .getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                }
                ?: throw ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Not found condition engine by type=${type} " +
                                "language=${language}"
                )

        val start = System.currentTimeMillis()
        val result = engine.isConditionFulfilled(conditionConfig).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        }
        val end = System.currentTimeMillis()

        return ConditionCheckerResponseDto(result = result, time = "${end - start} ms")
    }

    @RequestMapping(
            value = ["/v1/feature-toggles/{name}/is-active"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun isEnable(@PathVariable("name") name: String, @RequestParam allParams: Map<String, String>): IsEnabledResponseDto {
        val toggle = featureToggleRepository.byName(name).getOrHandle { ex ->
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        } ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Not found feature toggle by name=$name"
        )

        if (toggle.isActive()) {
            if (toggle.condition != null) {
                val c = toggle.condition
                val engine = conditionEngineRepository.by(type = c.type, language = c.language).getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                } ?: throw ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Not found condition engine by type=${c.type} " +
                                "language=${c.language}"
                )

                val parameters = c.parameters?.let { parameters ->
                    val inputParams = parameters.parameters.map { p ->
                        val paramValue = allParams[p.name]
                                ?: throw ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Value for parameter ${p.name} mast be exists"
                                )

                        InputConditionParameter(name = p.name, value = paramValue)
                    }

                    InputConditionParameters(parameters = inputParams)
                }

                val result = engine.isConditionFulfilled(
                        parameters = ConditionEngineParameters(
                                inputParameters = parameters,
                                condition = c.condition?.let { cc -> Condition(cc.body) }
                        )
                ).getOrHandle { ex ->
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
                }

                return IsEnabledResponseDto(result = result)
            } else {
                return IsEnabledResponseDto(result = true)
            }
        } else {
            return IsEnabledResponseDto(result = false)
        }
    }

    @RequestMapping(
            value = ["/v1/feature-toggles/types"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun types(): FeatureToggleTypesResponseDto = FeatureToggleTypesResponseDto()
}

private fun FeatureToggle.toApiModel() = FeatureToggleResponseDto(
        id = this.id,
        name = this.name,
        enabled = this.enabled,
        description = this.description.value,
        startDate = this.startDate,
        endDate = this.endDate,
        type = FeatureToggleTypeResponseDto.valueOf(this.type.name),
        condition = this.condition?.let { c ->
            ConditionEngineConfigurationResponseDto(
                    type = ConditionEngineTypeResponseDto.valueOf(c.type.name),
                    language = ConditionEngineLanguageResponseDto.valueOf(c.language.name),
                    parameters = c.parameters
                            ?.let { par ->
                                val pars = par.parameters.map { p ->
                                    InputConditionParameterConfigurationResponseDto(
                                            name = p.name,
                                            description = p.description
                                    )
                                }
                                InputConditionParametersConfigurationResponseDto(inputParameters = pars)
                            },
                    condition = c.condition?.let { con -> ConditionConfigurationResponseDto(body = con.body) }
            )
        },
)

private fun ConditionEngineTypeRequestDto.toDomainModel() = ConditionEngineType.valueOf(this.name)

private fun ConditionEngineLanguageRequestDto.toDomainModel() = ConditionEngineLanguage.valueOf(this.name)

private fun ConditionEngineType.toApiModel() = ConditionEngineTypeRequestDto.valueOf(this.name)

private fun ConditionEngineLanguage.toApiModel() = ConditionEngineLanguageRequestDto.valueOf(this.name)

private fun ConditionEngineDescription.toApiModel() = ConditionEngineDescriptionRequestDto(value = this.value)

private fun NewFeatureToggleRequestDto.toDomainModel() = NewFeatureToggle(
        name = this.name,
        enabled = this.enabled,
        startDate = this.startDate,
        endDate = this.endDate,
        description = FeatureToggleDescription(this.description),
        type = FeatureToggleType.valueOf(this.type.name),
        condition = this.condition?.let { c ->
            c.toDomainModel(type = c.type.toDomainModel(), language = c.language.toDomainModel())
        },
)

private fun ConditionEngineCheckerRequestDto.toDomainModel() = ConditionEngineParameters(
        inputParameters = this.parameters
                ?.let { ps ->
                    InputConditionParameters(
                            parameters = ps
                                    .inputParameters
                                    .map { p ->
                                        InputConditionParameter(
                                                name = p.name,
                                                value = p.value)
                                    }
                    )
                },
        condition = this.condition?.let { c -> Condition(body = c.body) }
)

private fun ConditionEngineConfigurationRequestDto.toDomainModel(
        type: ConditionEngineType,
        language: ConditionEngineLanguage,
) = ConditionEngineConfiguration(
        type = type,
        language = language,
        parameters = this.parameters
                ?.let { ps ->
                    InputConditionParametersConfiguration(
                            parameters = ps
                                    .inputParameters
                                    .map { p ->
                                        InputConditionParameterConfiguration(
                                                name = p.name,
                                                description = p.description)
                                    }
                    )
                },
        condition = this.condition?.let { c -> ConditionConfiguration(body = c.body) }
)

private fun FeatureToggleInfoRequestDto.toDomainModel() = FeatureToggleInfo(
        name = this.name,
        enabled = this.enabled,
        startDate = this.startDate,
        endDate = this.endDate,
        description = FeatureToggleDescription(this.description),
        type = FeatureToggleType.valueOf(this.type.name),
        condition = this.condition
                ?.let { c ->
                    c.toDomainModel(type = c.type.toDomainModel(), language = c.language.toDomainModel())
                },
)