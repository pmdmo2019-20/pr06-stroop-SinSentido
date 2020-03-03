package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.ui.ranking.RankingAdapter
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.ranking_fragment.*


class AssistantFragment : Fragment(R.layout.assistant_fragment), AssistantAdapter.OnItemClickListener {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    val listAdapter: AssistantAdapter = AssistantAdapter().also {
        it.setOnItemClickListener(this)
    }

    private fun setupViews(view: View){
        setupToolbar()
        setupViewPager()
        setupTabLayout()

    }

    private fun setupViewPager() {
        viewPager.adapter = listAdapter
    }


    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun setupTabLayout(){
        TabLayoutMediator(tabLayout, viewPager){_, _ -> }.attach()
    }

    override fun onClick() {
        activity!!.onBackPressed()
    }

}
