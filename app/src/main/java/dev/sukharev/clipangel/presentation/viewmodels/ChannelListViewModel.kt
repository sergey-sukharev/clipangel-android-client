package dev.sukharev.clipangel.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dev.sukharev.clipangel.domain.channel.ChannelInteractor
import dev.sukharev.clipangel.domain.channel.models.Channel
import dev.sukharev.clipangel.domain.channel.models.ChannelCredentials
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ChannelListViewModel(private val channelInteractor: ChannelInteractor): ViewModel() {

    init {
        println()
    }

    private var networkJob: Job? = null

    fun createChannel(credentials: ChannelCredentials) {
        networkJob = CoroutineScope(Dispatchers.IO).launch {
            channelInteractor.createChannel(credentials).collect {
                println()
            }
        }
    }

    fun getAllChannels(): Flow<List<Channel>> = flow {

    }

    override fun onCleared() {
        super.onCleared()
        networkJob?.cancel()
    }

}