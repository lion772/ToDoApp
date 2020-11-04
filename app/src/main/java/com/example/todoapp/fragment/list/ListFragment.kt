package com.example.todoapp.fragment.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        setUpListener(view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoViewModel.getAllData()
        setUpAdapter()
        setUpObservable()
    }

    fun setUpObservable() {
        toDoViewModel.data.observe(viewLifecycleOwner) { dataList ->
            dataList?.let {
                recyclerView.visibility = View.VISIBLE
                (recyclerView.adapter as ListAdapter).setData(dataList)
            }

        }
        toDoViewModel.dataLoadError.observe(viewLifecycleOwner) { error ->
            if(error){
                no_data_imageView.visibility = View.VISIBLE
                no_data_textView.text = getString(R.string.no_data_retrieved)
            } else{
                no_data_imageView.visibility = View.GONE
            }
        }
        toDoViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                listProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    no_data_textView.visibility = View.GONE
                    no_data_imageView.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpAdapter() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter()
        }
    }

    private fun setUpListener(view: View) {
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

}