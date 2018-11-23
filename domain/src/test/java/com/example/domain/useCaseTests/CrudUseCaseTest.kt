package com.example.domain.useCaseTests

import com.example.domain.repository.CrudRepository
import com.example.domain.usecase.CrudUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CrudUseCaseTest {

    var crudUseCase : CrudUseCase? = null

    @Mock
    val crudRepository : CrudRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        crudUseCase = CrudUseCase(crudRepository!!)
    }

    @Test
    fun testCRUD() {
        crudUseCase!!.complexReadWrite()
        verify(crudRepository)!!.complexReadWrite()
        verifyNoMoreInteractions(crudRepository)
    }
}