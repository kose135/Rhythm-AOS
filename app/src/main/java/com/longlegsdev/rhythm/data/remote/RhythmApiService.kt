package com.longlegsdev.rhythm.data.remote

import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.data.remote.model.ChannelList
import com.longlegsdev.rhythm.data.remote.model.MusicInfo
import com.longlegsdev.rhythm.data.remote.model.MusicList
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RhythmApiService {

    /* channel */

    @GET("channels")
    suspend fun getChannelList(
        @Query("page") page: Int,
        @Query("offset") offset: Int
    ): Response<ChannelList>

    @GET("channel/recommended")
    suspend fun getChannelRecommendedList(
        @Query("limit") limit: Int,
    ): Response<ChannelList>

    @GET("channel/{channelId}/musics")
    suspend fun getChannelMusicList(
        @Path("channelId") channelId: Int
    ): Response<MusicList>

    @POST("channel/{channelId}/like")
    suspend fun addChannelLike(
        @Path("channelId") channelId: Int
    ): Response<Apply>

    @DELETE("channel/{channelId}/unlike")
    suspend fun deleteChannelLike(
        @Path("channelId") channelId: Int
    ): Response<Apply>


    /* music */

    @GET("music/{musicId}")
    suspend fun getMusicInfo(
        @Path("musicId") musicId: Int,
    ): Response<MusicInfo>

    @GET("music/best")
    suspend fun getMusicBest(
        @Query("limit") limit: Int,
    ): Response<MusicList>

    @POST("music/{musicId}/like")
    suspend fun addMusicLike(
        @Path("musicId") musicId: Int
    ): Response<Apply>

    @DELETE("music/{musicId}/unlike")
    suspend fun deleteMusicLike(
        @Path("musicId") musicId: Int
    ): Response<Apply>


}