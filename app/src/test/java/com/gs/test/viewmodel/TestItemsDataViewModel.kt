package com.gs.test.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gs.test.data.model.Item
import com.gs.test.repository.MockDataRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestItemsDataViewModel {
    private lateinit var dataViewModel: ItemsDataViewModel
    private lateinit var dataRepository: MockDataRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dataRepository = MockDataRepository()
        dataViewModel = ItemsDataViewModel(dataRepository)

    }

    @Test
    fun getDataNotNull() = runBlocking {
        val itemList = dataRepository.getData()
        assert(!itemList.results.isNullOrEmpty())
    }

    @Test
    fun fetchBasedOnDateSelection() = runBlocking {
        dataViewModel.fetchBasedOnDateSelection("2022/09/09")
        assert(dataViewModel.getItemsLiveData().value != null)
    }

    @Test
    fun updateDateSelection() = runBlocking {
        dataViewModel.updateValue(Item(
            overview = "Overview",
            date = "2022/09/09",
            url = "",
            title = "Title",
            media_type = "image"
        ))
        assert(dataViewModel.getItemsLiveData().value != null)
    }



}