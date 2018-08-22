package br.com.brunoalmeida.baseproject.common.util

import io.reactivex.subjects.BehaviorSubject

class RxBus {
    companion object {
        val bus : BehaviorSubject<Any> = BehaviorSubject.create()
    }
}