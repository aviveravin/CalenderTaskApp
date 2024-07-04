package com.example.calendertaskapp.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendertaskapp.R
import com.example.calendertaskapp.adapter.TaskListAdapter
import com.example.calendertaskapp.databinding.FragmentTaskListBinding
import com.example.calendertaskapp.model.DeleteTaskRequest
import com.example.calendertaskapp.model.TaskModel
import com.example.calendertaskapp.utils.ViewState
import com.example.calendertaskapp.viewmodel.TaskViewModel
import com.example.calendertaskapp.viewmodel.TaskViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment(), TaskListAdapter.OnDeleteClickListener {

    private var _binding : FragmentTaskListBinding? = null
    private val binding get() = _binding

    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var viewModelFactory: TaskViewModelFactory

    private val taskViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ConstraintLayout? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskListBinding.inflate(inflater)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.nav_graph_host_fragment
        ) as NavHostFragment

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskListAdapter = TaskListAdapter(this)
        binding?.rvTaskList?.apply {
            layoutManager = linearLayoutManager
            adapter = taskListAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }

        binding?.btnAddTask?.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_taskListFragment_to_monthViewFragment)
        }


        taskViewModel.getTaskList(
            123
        )

        setObservers()
    }

    private fun setObservers() {
        taskViewModel.getAllTaskFlow.asLiveData()
            .observe(requireActivity()) { viewState ->
                when (viewState) {
                    is ViewState.Loading -> {
                        Log.d("is ViewState.ErrorsData ->", "setObservers: Loading...")

                    }

                    is ViewState.Success -> {
                        val taskList = viewState.data.tasks
                        taskListAdapter.updateData(taskList)
                        Log.d("aviveravin", "task list $taskList")
                    }

                    is ViewState.NetworkFailed -> {
                        Log.d("aviveravin", "setObservers: Network failed")
                        Toast.makeText(
                            requireContext(),
                            "Constants.NO_INTERNET_MESSAGE",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ViewState.ErrorsData -> {
                        Log.d("aviveravin", "setObservers: ${viewState.errorData}")
                        // Handle the error state here, you can access the error message using viewState.errorMessage
                    }

                    else -> {
                        Log.d("aviveravin", "else: $viewState")
                    }
                }
            }

        //Delete task

        taskViewModel.deleteTaskFlow.asLiveData()
            .observe(requireActivity()) { viewState ->
                when (viewState) {
                    is ViewState.Loading -> {
                        Log.d("is ViewState.ErrorsData ->", "setObservers: Loading...")

                    }

                    is ViewState.Success -> {
                        Log.d("aviveravin", "delete")
                        taskViewModel.getTaskList(123)
                        Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
                    }

                    is ViewState.NetworkFailed -> {
                        Log.d("aviveravin", "setObservers: Network failed")
                        Toast.makeText(
                            requireContext(),
                            "Constants.NO_INTERNET_MESSAGE",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ViewState.ErrorsData -> {
                        Log.d("aviveravin", "setObservers: ${viewState.errorData}")
                        // Handle the error state here, you can access the error message using viewState.errorMessage
                    }

                    else -> {
                        Log.d("aviveravin", "else: $viewState")
                    }
                }
            }
    }

    override fun onDeleteClick(task: TaskModel) {
        taskViewModel.deleteTask(
            userId = 123,
            taskId = task.task_id
        )

    }

}