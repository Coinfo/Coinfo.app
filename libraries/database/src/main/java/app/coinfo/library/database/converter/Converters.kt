package app.coinfo.library.database.converter

import androidx.room.TypeConverter
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.database.enums.PortfolioType

class Converters {

    @TypeConverter
    fun toPortfolioType(value: String) = enumValueOf<PortfolioType>(value)

    @TypeConverter
    fun fromPortfolioType(value: PortfolioType) = value.name

    @TypeConverter
    fun toCurrency(value: String) = enumValueOf<Currency>(value)

    @TypeConverter
    fun fromCurrency(value: Currency) = value.uuid
}
