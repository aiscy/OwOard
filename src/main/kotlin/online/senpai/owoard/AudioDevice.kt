/*
 * This file is part of the OwOard distribution (https://github.com/aiscy/OwOard).
 * Copyright (c) 2020 Maxim Valeryevich Pavlov.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package online.senpai.owoard

import javax.json.Json
import javax.json.JsonObject

data class AudioDevice(
        val interfaceName: String,
        val deviceId: String,
        val deviceDescription: String
) : Jsonable {
    override fun toJson(): JsonObject {
        return Json.createObjectBuilder()
                .apply {
                    add("interfaceName", interfaceName)
                    add("deviceId", deviceId)
                    add("deviceDescription", deviceDescription)
                }
                .build()
    }

    companion object {
        fun fromJson(json: JsonObject): AudioDevice {
            return AudioDevice(
                    interfaceName = json.getString("interfaceName"),
                    deviceId = json.getString("deviceId"),
                    deviceDescription = json.getString("deviceDescription")
            )
        }
    }
}
