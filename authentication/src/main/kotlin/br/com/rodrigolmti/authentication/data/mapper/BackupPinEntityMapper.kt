package br.com.rodrigolmti.authentication.data.mapper

import br.com.rodrigolmti.core_android.Mapper
import br.com.rodrigolmti.database.entity.BackupPinEntity
import javax.inject.Inject

class BackupPinEntityMapper @Inject constructor() : Mapper<String, BackupPinEntity> {

    override fun mapFrom(from: String): BackupPinEntity = BackupPinEntity(
        pin = from
    )
}