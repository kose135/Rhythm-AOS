package com.longlegsdev.rhythm.data.remote

import com.longlegsdev.rhythm.data.remote.response.ApplyResponse
import com.longlegsdev.rhythm.data.remote.response.ChannelListResponse
import com.longlegsdev.rhythm.data.remote.response.MusicInfoResponse
import com.longlegsdev.rhythm.data.remote.response.MusicListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RhythmApiService {

    /* channel */

    @GET("channels")
    suspend fun getChannelList(
        @Query("page") page: Int,
        @Query("offset") offset: Int
    ): Response<ChannelListResponse>

    @GET("channel/recommended")
    suspend fun getChannelRecommendedList(
        @Query("limit") limit: Int,
    ): Response<ChannelListResponse>

    @GET("channel/{channelId}/musics")
    suspend fun getChannelMusicList(
        @Path("channelId") channelId: String
    ): Response<MusicListResponse>

    @POST("channel/{channelId}/like")
    suspend fun addChannelLike(
        @Path("channelId") channelId: String
    ): Response<ApplyResponse>

    @DELETE("channel/{channelId}/unlike")
    suspend fun deleteChannelLike(
        @Path("channelId") channelId: String
    ): Response<ApplyResponse>


    /* music */

    @GET("music/{musicId}")
    suspend fun getMusicInfo(
        @Path("musicId") musicId: Int,
    ): Response<MusicInfoResponse>

    @GET("music/best")
    suspend fun getMusicBest(
        @Query("limit") limit: Int,
    ): Response<MusicListResponse>

    @POST("music/{musicId}/like")
    suspend fun addMusicLike(
        @Path("musicId") musicId: String
    ): Response<ApplyResponse>

    @DELETE("music/{musicId}/unlike")
    suspend fun deleteMusicLike(
        @Path("musicId") musicId: String
    ): Response<ApplyResponse>


}