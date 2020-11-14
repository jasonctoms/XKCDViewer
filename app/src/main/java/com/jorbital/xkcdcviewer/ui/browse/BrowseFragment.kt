package com.jorbital.xkcdcviewer.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jorbital.xkcdcviewer.R

class BrowseFragment : Fragment() {

    private lateinit var browseViewModel: BrowseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        browseViewModel =
            ViewModelProvider(this).get(BrowseViewModel::class.java)
        val root = inflater.inflate(R.layout.browse_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        browseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}