package com.kku.pharm.project.dmathere.ui.sideEffect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R


class SideEffectFragment : Fragment() {
    companion object {
        fun newInstance(): SideEffectFragment {
            val fragment = SideEffectFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_side_effect, container, false)
    }
}