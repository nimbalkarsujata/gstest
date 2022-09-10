package com.gs.test.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.gs.test.repository.MockDataRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestItemsDetailViewModel {
    private lateinit var dataViewModel: ItemDetailViewModel
    private lateinit var dataRepository: MockDataRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dataRepository = MockDataRepository()
        dataViewModel = ItemDetailViewModel(SavedStateHandle(), dataRepository)

    }

    @Test
    fun getDataNotNull() = runBlocking {
        val item = dataRepository.getDataByDate(date = "2022-09-10")
        assert(item.title == "Title")
    }

}