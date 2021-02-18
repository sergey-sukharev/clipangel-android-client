package dev.sukharev.clipangel.domain.channel

import com.google.firebase.messaging.FirebaseMessaging
import dev.sukharev.clipangel.data.local.repository.channel.ChannelRepository
import dev.sukharev.clipangel.data.remote.repository.channel.ChannelRemoteRepository
import dev.sukharev.clipangel.domain.channel.models.Channel
import dev.sukharev.clipangel.domain.channel.models.ChannelCredentials
import dev.sukharev.clipangel.domain.models.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class ChannelInteractorImpl(val channelRepository: ChannelRepository,
                            val channelRemoteRepository: ChannelRemoteRepository) : ChannelInteractor {


    @ExperimentalCoroutinesApi
    override suspend fun createChannel(credentials: ChannelCredentials): Flow<Result<EmptyResult>> = callbackFlow {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            GlobalScope.launch {
                channelRemoteRepository.createChannel(credentials, it.result ?: "").collect {
                    if (it.isSuccess()) {
                        channelRepository.create(it.asSuccess().value)
                        offer(Result.Success.Empty)
                        close()
                    } else if (it.isFailure()){
                        offer(Result.Failure.Error(it.asFailure().error))
                        close()
                    }
                }
            }
        }.addOnFailureListener {
            offer(Result.Failure.Error(it))
            close()
        }

        awaitClose()
    }

    @ExperimentalCoroutinesApi
    override suspend fun deleteChannel(id: String): Flow<Result<EmptyResult>> = callbackFlow {

    }

    @ExperimentalCoroutinesApi
    override suspend fun updateToken(channelId: String, token: String):
            Flow<Result<EmptyResult>> = callbackFlow {

    }
}