package es.iessaladillo.pedrojoya.stroop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "player",
    indices = [
        Index(
            name = "PLAYER_NICKNAME_UNIQUE",
            value = ["nickname"],
            unique = true
        )
    ]
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "avatarId")
    var avatarId: Int
)