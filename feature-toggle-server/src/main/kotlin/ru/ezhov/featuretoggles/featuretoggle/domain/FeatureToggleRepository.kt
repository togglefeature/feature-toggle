package ru.ezhov.featuretoggles.featuretoggle.domain

import arrow.core.Either
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggle
import ru.ezhov.featuretoggles.featuretoggle.domain.model.FeatureToggleId

interface FeatureToggleRepository {
    fun all(): Either<FeatureToggleRepositoryException, List<FeatureToggle>>

    fun byId(id: String): Either<FeatureToggleRepositoryException, FeatureToggle?>

    fun byName(name: String): Either<FeatureToggleRepositoryException, FeatureToggle?>

    fun save(featureToggle: FeatureToggle): Either<FeatureToggleRepositoryException, FeatureToggleId>

    fun delete(id: String): Either<FeatureToggleRepositoryException, Unit>
}