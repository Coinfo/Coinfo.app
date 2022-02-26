package app.coinfo.library.database.converter

import androidx.room.TypeConverter
import app.coinfo.library.database.enums.PortfolioType

class Converters {

    @TypeConverter
    fun toPortfolioType(value: String) = enumValueOf<PortfolioType>(value)

    @TypeConverter
    fun fromPortfolioType(value: PortfolioType) = value.name
}
