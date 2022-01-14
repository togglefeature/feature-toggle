package ru.ezhov.featuretoggles.featuretoggle.domain

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Service
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineRepository
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggle
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggleId
import ru.ezhov.featuretoggles.featuretoggle.domain.model.NewFeatureToggle

@Service
class FeatureToggleService(
    private val conditionEngineRepository: ConditionEngineRepository,
    private val featureToggleRepository: FeatureToggleRepository
) {
    fun createNew(newToggle: NewFeatureToggle): Either<CreateFeatureToggleServiceException, FeatureToggleId> {
        val condition = newToggle.condition
        return if (condition != null) {
            isExistsConditionEngine(condition).flatMap { v ->
                if (v) {
                    create(newToggle)
                } else {
                    Either.Left(CreateFeatureToggleServiceException.NotFoundConditionEngine("Not found condition engine"))
                }
            }
        } else {
            create(newToggle)
        }
    }

    private fun isExistsConditionEngine(condition: ConditionEngineConfiguration):
            Either<CreateFeatureToggleServiceException, Boolean> {
        return conditionEngineRepository
            .by(condition.type, condition.language)
            .map { ce -> ce != null }
            .mapLeft { ex ->
                CreateFeatureToggleServiceException.SearchConditionEngine(
                    "Error when search condition engine",
                    ex
                )
            }
    }

    private fun create(newToggle: NewFeatureToggle): Either<CreateFeatureToggleServiceException, FeatureToggleId> =
        featureToggleRepository
            .byName((newToggle.name))
            .mapLeft { ex -> CreateFeatureToggleServiceException.Search("Error when search by name", ex) }
            .flatMap { ft ->
                if (ft != null) {
                    Either.Left(
                        CreateFeatureToggleServiceException
                            .AlreadyExistsByName("Feature toggle already exists by name '${ft.name}'")
                    )
                } else {
                    featureToggleRepository
                        .save(FeatureToggle.from(newToggle))
                        .mapLeft { ex ->
                            CreateFeatureToggleServiceException.Save(
                                "Error when save new feature toggle",
                                ex
                            )
                        }
                }
            }
}