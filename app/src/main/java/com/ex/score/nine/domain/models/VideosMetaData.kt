package com.ex.score.nine.domain.models

data class VideosMetaData(
    val full_name: String,
    val hash_tags: String,
    val image_url: String,
    val is_following: Boolean,
    val is_liked: Boolean,
    val is_saved: Boolean,
    val likes: Int,
    val notification_token: String,
    val title: String,
    val user_id: String,
    val username: String,
    val video_id: String,
    val video_key: String,
    val view_type: String
)