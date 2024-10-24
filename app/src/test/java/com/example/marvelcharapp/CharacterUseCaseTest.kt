package com.example.marvelcharapp

import com.example.marvelcharapp.data.character.CharacterRepositoryImpl
import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.Failure
import com.example.marvelcharapp.domain.base.Success
import com.example.marvelcharapp.domain.character.usecase.CharacterUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CharacterUseCaseTest {

    private lateinit var characterUseCase: CharacterUseCase

    @Mock
    private lateinit var characterRepository: CharacterRepositoryImpl

    private val successCharacterList = Success(CharacterMock.characterListDTO)
    private val successCharacter = Success(CharacterMock.characterDTO1)
    private val failure = Failure(Error.UncompletedOperation("uncompleted operation"))


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        characterUseCase = CharacterUseCase(characterRepository)
    }

    @Test
    fun `when getCharacterList success characterRepository getCharacterList is called`() = runTest {
        `when`(characterRepository.getCharacterList(1)).thenReturn(successCharacterList)
        characterUseCase.getCharacterList(1)
        verify(characterRepository, times(1)).getCharacterList(1)
    }

    @Test
    fun `when getCharacterList success characterRepository getCharacterList returns success`() = runTest {
        `when`(characterRepository.getCharacterList(1)).thenReturn(successCharacterList)
        val result = characterUseCase.getCharacterList(1)
        Assert.assertEquals(successCharacterList, result)
    }

    @Test
    fun `when getCharacterList fails characterRepository getCharacterList returns failure`() = runTest {
        `when`(characterRepository.getCharacterList(1)).thenReturn(failure)
        val result = characterUseCase.getCharacterList(1)
        Assert.assertEquals(failure, result)
    }

    @Test
    fun `when getCharacter success characterRepository getCharacter is called`() = runTest {
        `when`(characterRepository.getCharacter("1")).thenReturn(successCharacter)
        characterUseCase.getCharacterList(1)
        verify(characterRepository, times(1)).getCharacterList(1)
    }

    @Test
    fun `when getCharacter success characterRepository getCharacter returns success`() = runTest {
        `when`(characterRepository.getCharacter("1")).thenReturn(successCharacter)
        val result = characterUseCase.getCharacter("1")
        Assert.assertEquals(successCharacter, result)
    }

    @Test
    fun `when getCharacter fails characterRepository getCharacter returns failure`() = runTest {
        `when`(characterRepository.getCharacter("1")).thenReturn(failure)
        val result = characterUseCase.getCharacter("1")
        Assert.assertEquals(failure, result)
    }
}