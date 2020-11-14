package com.jorbital.xkcdcviewer.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) = FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }

inline fun <T : ViewBinding> View.viewBinding(
    crossinline viewBindingFactory: (View) -> T
) = lazy(LazyThreadSafetyMode.NONE) { viewBindingFactory.invoke(this) }

/**
 * This class exists to avoid memory leaks.  Fragments live longer than their views, so the view binding
 * must be removed when fragment goes to backstack and readded when it returns.  This class gets all of
 * that logic out of the fragment so we can one-line our view bindings
 *
 * note: this class is taken from another one of my projects
 *
 * https://medium.com/@Zhuinden/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(owner = fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val immutableBinding = binding
        if (immutableBinding != null) {
            return immutableBinding
        }

        if (thisRef.view == null) {
            throwIllegalState(thisRef, property.name)
        }
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throwIllegalState(thisRef, property.name)
        }

        return viewBindingFactory(thisRef.requireView()).also { this@FragmentViewBindingDelegate.binding = it }
    }

    private fun throwIllegalState(fragmentReference: Fragment, propertyName: String) {
        val fragmentName = fragmentReference.javaClass.simpleName
        val exceptionMessage = "$fragmentName: You cannot access $propertyName when $fragmentName's view is destroyed.\n" +
                "Can you trigger this action with ViewModel LiveData? This way the action will not be observed outside of $fragmentName's view scope"
        throw IllegalStateException(exceptionMessage).apply { stackTrace = mutateStackTrace(stackTrace, fragmentReference) }
    }

    /**
     * Crashlytics groups crashes by method name and line number. Every IllegalStateException thrown here will be grouped as the same crash
     * regardless of which Fragment caused it. That huge bucket of crashes will reopen when a new leak is introduced.
     * To get around this we mutate the stack trace to put the last execution point of the Fragment at the top of the stack trace.
     */
    private fun mutateStackTrace(stackTrace: Array<StackTraceElement>?, fragmentReference: Fragment): Array<StackTraceElement> {
        val fullyQualifiedFragmentName = fragmentReference.javaClass.name
        val lastExecutionPointInFragment = stackTrace?.firstOrNull { it.className == fullyQualifiedFragmentName }
        return if (lastExecutionPointInFragment != null) {
            mutableListOf(lastExecutionPointInFragment).apply { addAll(stackTrace) }.toTypedArray()
        } else stackTrace ?: emptyArray()
    }
}