package com.example.tryflows.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubscribeRepository {

    val counter:Flow<Int> = flow{

        var bombs = 1

        while (true){
            emit(bombs)
            bombs++
            Thread.sleep(1000)
        }
    }
}