package ru.ezhov.featuretoggles.featuretoggle.domain

sealed class CreateFeatureToggleServiceException(
    message: String,
    cause: Exception? = null
) : Exception(message, cause) {
    class Save(message: String, cause: Exception) : CreateFeatureToggleServiceException(message)
    class Search(message: String, cause: Exception) : CreateFeatureToggleServiceException(message)
    class AlreadyExistsByName(message: String) : CreateFeatureToggleServiceException(message)
    class NotFoundConditionEngine(message: String) : CreateFeatureToggleServiceException(message)
    class SearchConditionEngine(message: String, cause: Exception) : CreateFeatureToggleServiceException(message)
}