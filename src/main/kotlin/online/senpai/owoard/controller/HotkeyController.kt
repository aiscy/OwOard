/*
 * This file is part of the OwOard distribution (https://github.com/aiscy/OwOard).
 * Copyright (c) 2019 Maxim Valeryevich Pavlov.
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

package online.senpai.owoard.controller

import javafx.application.Platform
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import online.senpai.owoard.view.AudioTile
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.Delegates

class HotkeyController : Controller() {
    private var enableConsumingEvents: Boolean by Delegates.notNull()
    private var shiftDown = false
    private var ctrlDown = false
    private var altDown = false
    private var metaDown = false
    private val map = mutableMapOf<KeyCombination, AudioTile>()


    init {
        Logger.getLogger(GlobalScreen::class.java.`package`.name).apply {
            level = Level.OFF
            useParentHandlers = false
        }
    }

    fun initialize(consumeEvents: Boolean = false) {
        enableConsumingEvents = consumeEvents
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
            override fun nativeKeyTyped(nativeKeyEvent: NativeKeyEvent) {
                Platform.runLater {
                    val keyEvent = nativeKeyEvent.toFxKeyEvent(KeyEvent.KEY_TYPED,
                            shiftDown,
                            ctrlDown,
                            altDown,
                            metaDown
                    )
                    println(keyEvent)
                    map.forEach { (keyCombination, _) ->
                        if (keyCombination.match(keyEvent)) {
                            fireTile(keyCombination)
                            return@runLater
                        }
                    }

//                    fireTile(nativeKeyEvent.)
//                    Event.fireEvent(Event.NULL_SOURCE_TARGET, keyEvent)
                }
            }

            override fun nativeKeyPressed(nativeKeyEvent: NativeKeyEvent) {
                Platform.runLater {
                    when (nativeKeyEvent.keyCode) {
                        NativeKeyEvent.VC_SHIFT -> !shiftDown
                        NativeKeyEvent.VC_CONTROL -> !ctrlDown
                        NativeKeyEvent.VC_ALT -> !altDown
                        NativeKeyEvent.VC_META -> !metaDown
                    }
                    val keyEvent: KeyEvent = nativeKeyEvent.toFxKeyEvent(
                            KeyEvent.KEY_PRESSED
                            /*shiftDown,
                            ctrlDown,
                            altDown,
                            metaDown*/
                    )
                    Event.fireEvent(Event.NULL_SOURCE_TARGET, keyEvent)
                }
            }

            override fun nativeKeyReleased(nativeKeyEvent: NativeKeyEvent) {
                Platform.runLater {
                    when (nativeKeyEvent.keyCode) {
                        NativeKeyEvent.VC_SHIFT -> !shiftDown
                        NativeKeyEvent.VC_CONTROL -> !ctrlDown
                        NativeKeyEvent.VC_ALT -> !altDown
                        NativeKeyEvent.VC_META -> !metaDown
                    }
                    val keyEvent: KeyEvent = nativeKeyEvent.toFxKeyEvent(
                            KeyEvent.KEY_RELEASED
                            /*shiftDown,
                            ctrlDown,
                            altDown,
                            metaDown*/
                    )
                    Event.fireEvent(Event.NULL_SOURCE_TARGET, keyEvent)
                }
            }
        })
    }

    fun destroy() {
        if (GlobalScreen.isNativeHookRegistered()) {
            GlobalScreen.unregisterNativeHook()
        }
    }

    fun registerTile(tile: AudioTile, key: KeyCombination) {
        map[key] = tile
    }

    fun fireTile(key: KeyCombination) {
        map[key]?.playButton?.fire()
    }
}

fun NativeKeyEvent.toFxKeyEvent(
        eventType: EventType<KeyEvent>,
        shiftDown: Boolean = false,
        ctrlDown: Boolean = false,
        altDown: Boolean = false,
        metaDown: Boolean = false
): KeyEvent {
    val keyCode: KeyCode = when (this.keyCode) {
        NativeKeyEvent.VC_ESCAPE -> KeyCode.ESCAPE
        NativeKeyEvent.VC_F1 -> KeyCode.F1
        NativeKeyEvent.VC_F2 -> KeyCode.F2
        NativeKeyEvent.VC_F3 -> KeyCode.F3
        NativeKeyEvent.VC_F4 -> KeyCode.F4
        NativeKeyEvent.VC_F5 -> KeyCode.F5
        NativeKeyEvent.VC_F6 -> KeyCode.F6
        NativeKeyEvent.VC_F7 -> KeyCode.F7
        NativeKeyEvent.VC_F8 -> KeyCode.F8
        NativeKeyEvent.VC_F9 -> KeyCode.F9
        NativeKeyEvent.VC_F10 -> KeyCode.F10
        NativeKeyEvent.VC_F11 -> KeyCode.F11
        NativeKeyEvent.VC_F12 -> KeyCode.F12
        NativeKeyEvent.VC_F13 -> KeyCode.F13
        NativeKeyEvent.VC_F14 -> KeyCode.F14
        NativeKeyEvent.VC_F15 -> KeyCode.F15
        NativeKeyEvent.VC_F16 -> KeyCode.F16
        NativeKeyEvent.VC_F17 -> KeyCode.F17
        NativeKeyEvent.VC_F18 -> KeyCode.F18
        NativeKeyEvent.VC_F19 -> KeyCode.F19
        NativeKeyEvent.VC_F20 -> KeyCode.F20
        NativeKeyEvent.VC_F21 -> KeyCode.F21
        NativeKeyEvent.VC_F22 -> KeyCode.F22
        NativeKeyEvent.VC_F23 -> KeyCode.F23
        NativeKeyEvent.VC_F24 -> KeyCode.F24

        NativeKeyEvent.VC_BACKQUOTE -> KeyCode.BACK_QUOTE
        NativeKeyEvent.VC_0 -> KeyCode.DIGIT0
        NativeKeyEvent.VC_1 -> KeyCode.DIGIT1
        NativeKeyEvent.VC_2 -> KeyCode.DIGIT2
        NativeKeyEvent.VC_3 -> KeyCode.DIGIT3
        NativeKeyEvent.VC_4 -> KeyCode.DIGIT4
        NativeKeyEvent.VC_5 -> KeyCode.DIGIT5
        NativeKeyEvent.VC_6 -> KeyCode.DIGIT6
        NativeKeyEvent.VC_7 -> KeyCode.DIGIT7
        NativeKeyEvent.VC_8 -> KeyCode.DIGIT8
        NativeKeyEvent.VC_9 -> KeyCode.DIGIT9

        NativeKeyEvent.VC_MINUS -> KeyCode.MINUS
        NativeKeyEvent.VC_EQUALS -> KeyCode.EQUALS
        NativeKeyEvent.VC_BACKSPACE -> KeyCode.BACK_SPACE
        NativeKeyEvent.VC_TAB -> KeyCode.TAB
        NativeKeyEvent.VC_CAPS_LOCK -> KeyCode.CAPS

        NativeKeyEvent.VC_A -> KeyCode.A
        NativeKeyEvent.VC_B -> KeyCode.B
        NativeKeyEvent.VC_C -> KeyCode.C
        NativeKeyEvent.VC_D -> KeyCode.D
        NativeKeyEvent.VC_E -> KeyCode.E
        NativeKeyEvent.VC_F -> KeyCode.F
        NativeKeyEvent.VC_G -> KeyCode.G
        NativeKeyEvent.VC_H -> KeyCode.H
        NativeKeyEvent.VC_I -> KeyCode.I
        NativeKeyEvent.VC_J -> KeyCode.J
        NativeKeyEvent.VC_K -> KeyCode.K
        NativeKeyEvent.VC_L -> KeyCode.L
        NativeKeyEvent.VC_M -> KeyCode.M
        NativeKeyEvent.VC_N -> KeyCode.N
        NativeKeyEvent.VC_O -> KeyCode.O
        NativeKeyEvent.VC_P -> KeyCode.P
        NativeKeyEvent.VC_Q -> KeyCode.Q
        NativeKeyEvent.VC_R -> KeyCode.R
        NativeKeyEvent.VC_S -> KeyCode.S
        NativeKeyEvent.VC_T -> KeyCode.T
        NativeKeyEvent.VC_U -> KeyCode.U
        NativeKeyEvent.VC_V -> KeyCode.V
        NativeKeyEvent.VC_W -> KeyCode.W
        NativeKeyEvent.VC_X -> KeyCode.X
        NativeKeyEvent.VC_Y -> KeyCode.Y
        NativeKeyEvent.VC_Z -> KeyCode.Z
        NativeKeyEvent.VC_OPEN_BRACKET -> KeyCode.OPEN_BRACKET
        NativeKeyEvent.VC_CLOSE_BRACKET -> KeyCode.CLOSE_BRACKET
        NativeKeyEvent.VC_BACK_SLASH -> KeyCode.BACK_SLASH
        NativeKeyEvent.VC_SEMICOLON -> KeyCode.SEMICOLON
        NativeKeyEvent.VC_QUOTE -> KeyCode.QUOTE
        NativeKeyEvent.VC_ENTER -> KeyCode.ENTER
        NativeKeyEvent.VC_COMMA -> KeyCode.COMMA
        NativeKeyEvent.VC_PERIOD -> KeyCode.PERIOD
        NativeKeyEvent.VC_SLASH -> KeyCode.SLASH
        NativeKeyEvent.VC_SPACE -> KeyCode.SPACE

        NativeKeyEvent.VC_SHIFT -> KeyCode.SHIFT
        NativeKeyEvent.VC_CONTROL -> KeyCode.CONTROL
        NativeKeyEvent.VC_ALT -> KeyCode.ALT
        NativeKeyEvent.VC_META -> KeyCode.META
        NativeKeyEvent.VC_CONTEXT_MENU -> KeyCode.CONTEXT_MENU

        else -> KeyCode.UNDEFINED
    }
    val unicodeCharacter: String = if (eventType == KeyEvent.KEY_TYPED) {
        this.keyChar.toString()
    } else {
        KeyEvent.CHAR_UNDEFINED
    }
    return KeyEvent(
            eventType,
            unicodeCharacter,
            "",
            keyCode,
            shiftDown,
            ctrlDown,
            altDown,
            metaDown
    )
}
