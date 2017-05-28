package Controller

/**
 * Created by mrneumann
 */
interface Controller{
    fun chooseModel(path:String)
    fun validateFormat(path:String): Boolean
}