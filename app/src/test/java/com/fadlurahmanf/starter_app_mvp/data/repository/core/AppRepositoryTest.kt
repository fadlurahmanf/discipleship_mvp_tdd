package com.fadlurahmanf.starter_app_mvp.data.repository.core

import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AppRepositoryTest{
    private lateinit var appRepository: AppRepository

    private var spMockBuilder = SPMockBuilder()

    @BeforeEach
    fun before(){
        appRepository = AppRepository(spMockBuilder.createContext())
    }

    @Test
    fun `params_language_return_true_value`(){
        appRepository.paramsLanguage = "SOMETHING"
        assertEquals("SOMETHING", appRepository.paramsLanguage)
    }

    @Test
    fun `if_prams_language_value_null_return_null`(){
        appRepository.paramsLanguage = null
        assertEquals(null, appRepository.paramsLanguage)
    }

    @Test
    fun `if_language_value_not_null_return_true_value`(){
        appRepository.languageResponse = LanguageResponse()
        assertEquals(true, appRepository.languageResponse != null)
    }

    @Test
    fun `if_language_null_return_null`(){
        appRepository.languageResponse = null
        assertEquals(null, appRepository.languageResponse)
    }
}