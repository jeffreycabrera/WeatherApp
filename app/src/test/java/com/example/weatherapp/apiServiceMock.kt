package com.example.weatherapp

import com.example.weatherapp.domain.model.Main
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGroup
import com.example.weatherapp.domain.model.WeatherX
import com.example.weatherapp.domain.network.WeatherService

class apiServiceMock : WeatherService {
    override suspend fun getWeatherGroup(
        group: String,
        appId: String,
        units: String
    ): WeatherGroup {
        return WeatherGroup(
            count = 3,
            list = listOf(
                WeatherDetails(
                    id = 1,
                    main = Main(123.1, 123.1, 123.1),
                    name = "test1",
                    weather = listOf(
                        WeatherX(
                            description = "description",
                            icon = "",
                            id = 1,
                            main = "main"
                        )
                    )
                ),
                WeatherDetails(
                    id = 2,
                    main = Main(123.1, 123.1, 123.1),
                    name = "test2",
                    weather = listOf(
                        WeatherX(
                            description = "description",
                            icon = "",
                            id = 1,
                            main = "main"
                        )
                    )
                ),
                WeatherDetails(
                    id = 3,
                    main = Main(123.1, 123.1, 123.1),
                    name = "test3",
                    weather = listOf(
                        WeatherX(
                            description = "description",
                            icon = "",
                            id = 1,
                            main = "main"
                        )
                    )
                )
            )
        )
    }

    override suspend fun getDetails(cityId: String, appId: String, units: String): WeatherDetails {
        return WeatherDetails(
            id = 3,
            main = Main(123.1, 123.1, 123.1),
            name = "test3",
            weather = listOf(
                WeatherX(
                    description = "description",
                    icon = "",
                    id = 1,
                    main = "main"
                )
            )
        )
    }
}
