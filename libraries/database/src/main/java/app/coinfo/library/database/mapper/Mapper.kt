package app.coinfo.library.database.mapper

import app.coinfo.library.database.entity.Portfolio
import app.coinfo.library.database.model.PortfolioData

internal val PortfolioData.asPortfolioEntity
    get() = Portfolio(displayName = name, source = source, date = data)
