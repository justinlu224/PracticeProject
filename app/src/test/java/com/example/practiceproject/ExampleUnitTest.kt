package com.example.practiceproject

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.junit.Test

import org.junit.Assert.*
import java.lang.Runnable
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test(){
        val i:Long = 1234567890
        println(java.lang.Long.toHexString(i))

        val rand = Random.nextBytes(2)


        println(rand)
        println(rand)

    }

    @Test
    fun main(){

        val addThree = {a: Int -> a + 3}
        println(addThree(4))
        println(addThree.double()(4))

    }

    fun ((Int) -> Int).double() = { a: Int ->
        println(this(a)) // 7  =  addThree(4)
        this(a) * 2
    }

     val ALIAS_COD_FME = "cod_fme"
     val ALIAS_COD_7_11 = "cod_711"
    val paymentMethodAlias = "cod_711"
    val isCodStoreOrder: Boolean
        get() = paymentMethodAlias == ALIAS_COD_FME || paymentMethodAlias == ALIAS_COD_7_11
    @Test
    fun or(){
        val i = "459.8595"
        val dd = BigDecimal(i).setScale(0,BigDecimal.ROUND_UP).toInt()
        val result = 460

        assertEquals(dd,result)

    }


    @Test
    fun coroutinesMain(){
//        runBlocking {
//            launch {
//                goodNumbers()
//            }
//            launch {//in same thread
////                for(i in channelForResult) {
////                    println("play something $i")
////                }
//            }
//        }

        runBlocking {
//            flowSomething.collect {
//                println("play something $it")
//            }
        }
    }

    val channelForResult = Channel<Int>()

    suspend fun goodNumbers(){
        listOf(9,5,2,7).forEach {
            delay(1000)
            println("play something $it")
            channelForResult.send(it)
        }
        channelForResult.close()
    }

    val flowSomething = flow {
        listOf(9, 5, 2, 7).forEach {
            delay(500)
            println("play something $it")
            emit(it)
        }
    }






    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
    val demoFlow = flow {
        listOf(9, 5, 2, 7).forEach {
            log("emit")
            emit(it)
        }
    }

    @Test
    fun main2() {
        runBlocking {
            demoFlow
                .flowOn(customDispatcher)
                .map {
                    log("become a string $it")
                    "become a string $it"
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    log("collect $it")
                }
        }
    }

    val customDispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            Thread(block).start()
        }
    }
}