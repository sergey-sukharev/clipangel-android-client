package dev.sukharev.clipangel.data.remote.repository.channel

import dev.sukharev.clipangel.domain.channel.models.Channel
import dev.sukharev.clipangel.domain.channel.models.ChannelCredentials
import dev.sukharev.clipangel.domain.clip.Clip
import dev.sukharev.clipangel.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface ChannelRemoteRepository {
   suspend fun createChannel(channel: ChannelCredentials, deviceToken: String): Flow<Channel>
   suspend fun deleteChannel(id: String): Flow<Result<String>>
   suspend fun getChannelData(channelId: String)
   suspend fun getClipData(channelId: String): Flow<Result<Clip>>
}