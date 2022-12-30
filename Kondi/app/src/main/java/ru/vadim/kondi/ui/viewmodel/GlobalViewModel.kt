package ru.vadim.kondi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vadim.kondi.data.product.ProductRepository

import ru.vadim.kondi.data.period.PeriodRepository
import ru.vadim.kondi.domain.product.ProductItem
import ru.vadim.kondi.domain.work.WorkItem
import ru.vadim.kondi.domain.period.PeriodItem
import ru.vadim.kondi.utils.ScreenMode
import ru.vadim.kondi.utils.Utils
import javax.inject.Inject


@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val periodRepository: PeriodRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {





    //Application---------------------------------------

   



    private val _onExit = MutableLiveData(false)
    val onExit: LiveData<Boolean>
        get() = _onExit

    fun onExitApp(param: Boolean){
        _onExit.postValue(param)
    }



    //Products------------------------------------------

    private var _allProducts = productRepository.getProductList
    val allProducts: LiveData<List<ProductItem>>
        get() = _allProducts

    private var _productScreenMode = MutableLiveData<ScreenMode>()
    val productScreenMode: LiveData<ScreenMode>
        get() = _productScreenMode

    fun setProductScreenMode(mode: ScreenMode) {
        _productScreenMode.postValue(mode)
        Log.i("MYTAG", "product mode ${_productScreenMode.value}")
    }


    private fun parseToString(input: String?): String = input?.trim() ?: ""
    private fun parseToInt(input: String?): Int {
        return try {

            input?.toInt() ?: -1

        } catch (e: Exception) {
            -1
        }
    }



    fun validateInput(
        inputName: String,
        inputKilogram: String,
        inputGram: String,
        inputRubles: String,
        inputKopeck: String,
    ): Boolean {

        var result = true

        if (inputName.isBlank()) {
            result = false
        }

        if (inputKilogram.isBlank()) {
            result = false
        }

        if (inputGram.isBlank()) {
            result = false
        }

        if (inputRubles.isBlank()) {
            result = false
        }

        if (inputKopeck.isBlank()) {
            result = false
        }

        return result


    }





    fun addProduct(
        inputName: String?,
        inputKilogram: String?,
        inputGram: String?,
        inputRubles: String?,
        inputKopeck: String?,
    ) {

        val name = parseToString(inputName)
        val kilogram = parseToInt(inputKilogram)
        val gram = parseToInt(inputGram)
        val rubles = parseToInt(inputRubles)
        val kopeck = parseToInt(inputKopeck)


        val productItem = ProductItem(
            name, 1, kilogram, gram, rubles, kopeck
        )

        viewModelScope.launch(Dispatchers.IO) {
            productRepository.addProduct(productItem)
        }


    }

    fun deleteProduct(productItem: ProductItem) {
        viewModelScope.launch {
            productRepository.deleteProduct(productItem)
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteAllProducts()
        }
    }




    fun updateProduct(
        inputName: String?,
        inputKilogram: String?,
        inputGram: String?,
        inputRubles: String?,
        inputKopeck: String?,
        inputId: Int
    ) {

        val name = parseToString(inputName)
        val kilogram = parseToInt(inputKilogram)
        val gram = parseToInt(inputGram)
        val rubles = parseToInt(inputRubles)
        val kopeck = parseToInt(inputKopeck)


        val productItem = ProductItem(
            name, 1, kilogram, gram, rubles, kopeck, id = inputId
        )

        viewModelScope.launch(Dispatchers.IO) {
            productRepository.updateProduct (productItem)
        }




    }





    private val _selectedProduct: MutableLiveData<ProductItem>? = MutableLiveData()
    val selectedProduct: MutableLiveData<ProductItem>?
        get() = _selectedProduct

    fun selectProduct(productItemId: Int) {
        viewModelScope.launch {
            productRepository.getProduct(productItemId).collect { task ->
                _selectedProduct?.value = task
            }
        }
    }









    //WorksInParts --------------------------------------------

    private var _allWorksInParts = periodRepository.getPeriodList
    val allWorksInParts: LiveData<List<PeriodItem>>
        get() = _allWorksInParts


    private var _inPartsScreenMode = MutableLiveData<ScreenMode>()
    val worksInPartsScreenMode: LiveData<ScreenMode>
        get() = _inPartsScreenMode

    fun setWorksInPartsScreenMode(mode: ScreenMode) {
        _inPartsScreenMode.postValue(mode)
        Log.i("MYTAG", "inParts mode ${_inPartsScreenMode.value}")
    }

    fun addWorksInPartsItem(periodItem: PeriodItem){

        viewModelScope.launch {
            periodRepository.addPeriod(periodItem)
        }


    }



    fun addWorkToWorkInPartsItem(partId: Int, workItem: WorkItem){
        viewModelScope.launch {

           selectWorksInPartsItem(partId)



            Utils().executeAfterDelay(100) {

                val oldWIPI = selectedPeriodItem!!.value
                val oldWL = oldWIPI!!.worksList!!.toMutableList()

                val newWL =oldWL.toMutableList()
                newWL.add(workItem)

                val newWPI = oldWIPI.copy(
                    worksList = newWL

                )

                updateWorksInPartsItem(newWPI)

            }






        }
    }



    fun updateWorksInPartsItem(periodItem: PeriodItem) {


        viewModelScope.launch(Dispatchers.IO) {
            periodRepository.updatePeriod (periodItem)
        }




    }


    fun deleteWorksInPartsItem(periodItem: PeriodItem){
        viewModelScope.launch {
            periodRepository.deletePeriod(periodItem)
        }
    }




 fun deleteAllWorksInPartsItems(){

     viewModelScope.launch {
         periodRepository.deleteAllPeriods()

     }




 }



    private val _selectedPeriodItem: MutableLiveData<PeriodItem>? = MutableLiveData()
    val selectedPeriodItem: MutableLiveData<PeriodItem>?
        get() = _selectedPeriodItem

    fun selectWorksInPartsItem(worksInPartsItemItemId: Int) {
        viewModelScope.launch {
            periodRepository.getPeriod(worksInPartsItemItemId).collect { worksInPartsItem ->
                _selectedPeriodItem?.value = worksInPartsItem
            }
        }
    }



}