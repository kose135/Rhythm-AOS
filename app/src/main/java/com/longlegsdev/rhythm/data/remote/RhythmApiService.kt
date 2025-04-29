package com.longlegsdev.rhythm.data.remote

import com.longlegsdev.rhythm.data.remote.model.TrackList
import com.longlegsdev.rhythm.data.remote.model.MusicInfo
import com.longlegsdev.rhythm.data.remote.model.MusicList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RhythmApiService {

    /* track */

    @GET("tracks")
    suspend fun getTrackList(
        @Query("page") page: Int,
        @Query("offset") offset: Int
    ): Response<TrackList>

    @GET("track/recommended")
    suspend fun getRecommendedTrackList(
        @Query("limit") limit: Int,
    ): Response<TrackList>

    @GET("track/{trackId}/music")
    suspend fun getTrackMusicList(
        @Path("trackId") trackId: Int
    ): Response<MusicList>

    @GET("track")
    suspend fun getTrackListById(
        @Query("ids") ids: String
    ): Response<TrackList>


    /* music */

    @GET("music/{musicId}")
    suspend fun getMusicInfo(
        @Path("musicId") musicId: Int,
    ): Response<MusicInfo>

    @GET("music/best")
    suspend fun getMusicBest(
        @Query("limit") limit: Int,
    ): Response<MusicList>

    @GET("music")
    suspend fun getMusicListById(
        @Query("ids") ids: String
    ): Response<MusicList>


}