package ObserverPattern

import java.util.*

/**
 * Created by mrneumann888@gmail.com
 */


interface Publisher {
    var listeners: LinkedList<Listener>

    fun addListener(obs: Listener){
        listeners.add(obs)
    }

    fun removeListener(obs: Listener){
        listeners.remove(obs)
    }

    fun notifyListeners()
}