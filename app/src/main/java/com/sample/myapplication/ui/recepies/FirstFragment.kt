package com.sample.myapplication.ui.recepies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sample.myapplication.R
import com.sample.myapplication.databinding.FragmentFirstBinding
import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.Category
import com.sample.myapplication.model.network.Recipe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Recipe>()

    @InternalCoroutinesApi
    private val viewModel by viewModels<RecepiesViewModel>()
    private lateinit var moviesAdapter: RecipeAdapter
    private val categoriesList = ArrayList<Category>()
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        init()
        subscribeRecipiesUi()
        subscribeCategoriesUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        rvRecipies.layoutManager = LinearLayoutManager(activity)
        moviesAdapter = RecipeAdapter(requireActivity(), list)
        rvRecipies.adapter = moviesAdapter

        rvCategories.layoutManager = GridLayoutManager(activity, 4)
        categoryAdapter = CategoriesAdapter(requireActivity(), categoriesList)
        rvCategories.adapter = categoryAdapter
    }

    @InternalCoroutinesApi
    private fun subscribeRecipiesUi() {
        viewModel.movieList.observe(
            requireActivity(),
            Observer { result ->

                when (result.status) {
                    Result.Status.SUCCESS -> {
                        result.data?.recipes?.let { list ->
                            txtRecipies.text = list.size.toString() + "recepies found near by"
                            moviesAdapter.updateData(list)
                        }
                        loading.visibility = View.GONE
                    }

                    Result.Status.ERROR -> {
                        result.message?.let {
                            showError(it)
                        }
                        loading.visibility = View.GONE
                    }

                    Result.Status.LOADING -> {
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    @InternalCoroutinesApi
    private fun subscribeCategoriesUi() {
        viewModel.categoryList.observe(
            requireActivity(),
            Observer { result ->

                when (result.status) {
                    Result.Status.SUCCESS -> {
                        result.data?.categories?.let { list ->
                            categoryAdapter.updateData(list)
                        }
                        loading.visibility = View.GONE
                    }

                    Result.Status.ERROR -> {
                        result.message?.let {
                            showError(it)
                        }
                        loading.visibility = View.GONE
                    }

                    Result.Status.LOADING -> {
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun showError(msg: String) {
        Snackbar.make(vParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}
