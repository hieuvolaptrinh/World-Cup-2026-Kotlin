package com.worldcup.app.data.repository

import com.worldcup.app.data.local.dao.LoginPasswordDao
import com.worldcup.app.data.local.entity.LoginPassword
import com.worldcup.app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dao: LoginPasswordDao
) : LoginRepository {

    override suspend fun getAll(): List<LoginPassword> {
        return dao.getAll()
    }

    override suspend fun getByName(name: String): List<LoginPassword> {
        return dao.getItemByName(name)
    }

    override suspend fun insert(item: LoginPassword) {
        dao.insert(item)
    }

    override suspend fun insertAll(items: List<LoginPassword>) {
        dao.insertAll(items)
    }

    override suspend fun update(item: LoginPassword) {
        dao.update(item)
    }

    override suspend fun updateAll(items: List<LoginPassword>) {
        dao.updateAll(items)
    }

    override suspend fun delete(item: LoginPassword) {
        dao.delete(item)
    }
}