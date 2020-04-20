package br.com.rodrigolmti.dashboard.data.mapper

import br.com.rodrigolmti.core_android.Mapper
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.database.entity.SavedPasswordEntity
import javax.inject.Inject

class SavedPasswordModelMapper @Inject constructor() :
    Mapper<SavedPasswordEntity, SavedPasswordModel> {

    override fun mapFrom(from: SavedPasswordEntity): SavedPasswordModel = SavedPasswordModel(
        id = from.id,
        label = from.label,
        login = from.login,
        password = from.password,
        strength = from.strength
    )
}