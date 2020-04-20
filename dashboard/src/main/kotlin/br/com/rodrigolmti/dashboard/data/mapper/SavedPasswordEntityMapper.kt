package br.com.rodrigolmti.dashboard.data.mapper

import br.com.rodrigolmti.core_android.Mapper
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.database.entity.SavedPasswordEntity
import javax.inject.Inject

class SavedPasswordEntityMapper @Inject constructor() :
    Mapper<SavedPasswordModel, SavedPasswordEntity> {

    override fun mapFrom(from: SavedPasswordModel): SavedPasswordEntity = SavedPasswordEntity(
        id = from.id ?: 0,
        label = from.label,
        login = from.login,
        password = from.password,
        strength = from.strength
    )
}