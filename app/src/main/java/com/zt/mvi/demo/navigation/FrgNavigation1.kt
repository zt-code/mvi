package com.zt.mvi.demo.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.base.lib.utils.ToastUtil
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgNavigation1Binding
import com.zt.mvi.demo.refresh.RefreshViewModel
import com.zt.mvi.demo.refresh.RefreshViewModelFactory
import com.zt.mvvm.base.frg.BaseFragment

class FrgNavigation1: BaseFragment<FrgNavigation1Binding>() {

    private var mNavController: NavHostController? = null
    private val viewModel: RefreshViewModel by viewModels() {
        RefreshViewModelFactory(
            "我是你大爷噢噢噢噢噢噢噢噢噢噢噢噢"
        )
    }


    override fun getContentLayout(): Int {
        return R.layout.frg_navigation1
    }

    override fun initView(savedInstanceState: Bundle?) {
        bind.bt.setOnClickListener {
            val bundle = FrgNavigation2Args("我是FrgNavigation1 传递过来的参数").toBundle();
            findNavController(this).navigate(R.id.navigation_frg2_dest, bundle)
        }

        /*val context = ContentProviderCompat.requireContext();
        // 1. 创建了一个导航控制器 NavController
        mNavController = NavHostController(context);
        // 2. 注册生命周期回调
        mNavController?.setLifecycleOwner(this);
        // 3. 注册返回事件分发器
        mNavController?.setOnBackPressedDispatcher(requireActivity().getOnBackPressedDispatcher());
        // 4. 创建 Fragment 导航器, 将其添加到 NavController 中管理
        onCreateNavController(mNavController!!);

        if (navState != null) {
            // Navigation controller state overrides arguments
            mNavController.restoreState(navState);
        } else {
            final Bundle args = getArguments();
            // 5. 为 NavController 注入导航图的 resId
            final int graphId = args != null ? args.getInt(KEY_GRAPH_ID) : 0;
            if (graphId != 0) {
                mNavController.setGraph(graphId);
            } else {
                mNavController.setMetadataGraph();
            }
        }*/

        //dataInit();
    }

    private fun dataInit(){
        val bundle = arguments
        if (bundle != null){
            val userName = arguments?.let { FrgNavigation1Args.fromBundle(it).taskId } ?: "没有"
            ToastUtil.getIns().show(context, userName)
        }
    }

    /*protected fun onCreateNavController(navController: NavController) {
        // 添加 DialogFragment 和  Fragment 的导航器
        navController.navigatorProvider.addNavigator(
            MyDialogFragmentNavigator(ContentProviderCompat.requireContext(), getChildFragmentManager())
        )
        navController.navigatorProvider.addNavigator(createFragmentNavigator())
    }*/


}