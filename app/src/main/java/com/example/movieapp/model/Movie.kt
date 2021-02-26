package com.example.movieapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// Contains structure of elements in API response
// Class must be a Parcel (extend Parcelable) to be able to pass data object using Navigation args
// Idea: the system writes data as parcelable then reads it later on

data class TMDBResponse (
    val dates: DatePeriods?,
    val page: Number?,

    @SerializedName("results")
    val movieList: List<TMDBMovie>?,

    @SerializedName("total_pages")
    val totalPages: Number?,

    @SerializedName("total_results")
    val totalResults: Number?

): Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("dates"),
        TODO("page"),
        TODO("movieList"),
        TODO("totalPages"),
        TODO("totalResults")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TMDBResponse> {
        override fun createFromParcel(parcel: Parcel): TMDBResponse {
            return TMDBResponse(parcel)
        }

        override fun newArray(size: Int): Array<TMDBResponse?> {
            return arrayOfNulls(size)
        }
    }
}


data class DatePeriods (
    @SerializedName("minimum")
    val startPeriod: String?,
    @SerializedName("maximum")
    val endPeriod: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(startPeriod)
        parcel.writeString(endPeriod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatePeriods> {
        override fun createFromParcel(parcel: Parcel): DatePeriods {
            return DatePeriods(parcel)
        }

        override fun newArray(size: Int): Array<DatePeriods?> {
            return arrayOfNulls(size)
        }
    }
}

data class TMDBMovie (
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val genre_ids: List<Number>?,
    val id: Number?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?, // will add this to fragment detail
    val popularity: Number?,
    var poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Number?,

    @SerializedName("vote_count")
    val voteCount: Number?

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        TODO("genre_ids"),
        TODO("id"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("popularity"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        TODO("voteAverage"),
        TODO("voteCount")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeString(title)
        parcel.writeValue(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TMDBMovie> {
        override fun createFromParcel(parcel: Parcel): TMDBMovie {
            return TMDBMovie(parcel)
        }

        override fun newArray(size: Int): Array<TMDBMovie?> {
            return arrayOfNulls(size)
        }
    }
}