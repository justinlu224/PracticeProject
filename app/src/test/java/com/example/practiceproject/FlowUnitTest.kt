package com.example.practiceproject

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.junit.Test

import org.junit.Assert.*
import java.lang.Runnable
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FlowUnitTest {

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



   // ==========

    //flow 是在同一條theard上執行
    @Test
    fun main() = runBlocking {

        val time = measureTimeMillis {
            flow {
                for (i in 1..5) {
                    delay(100)
                    emit(i)
                }
                println("for ${Thread.currentThread().name}")
            }
//                .flowOn(Dispatchers.IO)  如果使用flowOn 切換線程執行速度會加快
                .collect{
                delay(100)
                println(it)
                println("collect ${Thread.currentThread().name}")
            }
        }

        print("cost $time")
    }

    // channelFlow 在不同條theard上執行
    @ExperimentalCoroutinesApi
    @Test
    fun mainT() = runBlocking(Dispatchers.IO) {

        val time = measureTimeMillis{
            channelFlow {
                for (i in 1..5) {
                    delay(100)
                    send(i)
                }
                println("for ${Thread.currentThread().name}")
            }.collect{
                delay(100)
                println(it)
                println("collect ${Thread.currentThread().name}")
            }
        }

        print("cost $time")
    }

}