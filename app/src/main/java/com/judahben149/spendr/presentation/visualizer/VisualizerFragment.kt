package com.judahben149.spendr.presentation.visualizer

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentVisualizerBinding
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.data.PieEntry
import com.judahben149.spendr.utils.extensions.animateToolBarTitle

@AndroidEntryPoint
class VisualizerFragment : Fragment() {

    private var _binding: FragmentVisualizerBinding? = null
    val binding get() = _binding!!

    private val viewModel: VisualizerViewModel by viewModels()

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVisualizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener { navController.popBackStack() }

        viewModel.getBalance()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isPieChartDataReady) {
                populatePieChart(state)
            }
        }
    }

    private fun populatePieChart(state: VisualizerUiState) {
        val pieEntryArray = arrayListOf<PieEntry>()
        val pieShades = arrayListOf<Int>()

        pieEntryArray.add(PieEntry(state.incomeBalance.toFloat(), "Income"))
        pieEntryArray.add(PieEntry(state.expenditureBalance.toFloat(), "Expenditure"))

        pieShades.add(Color.parseColor("#9DACB2"))
        pieShades.add(Color.parseColor("#86BBD8"))

        val entryPieSet = PieDataSet(pieEntryArray, "")
        val entryData = PieData(entryPieSet)

        entryPieSet.sliceSpace = 1f
        entryPieSet.colors = pieShades

        binding.pieChart.data = entryData
//                entryData.setValueTextColor(Color.WHITE)
        entryData.setValueTextSize(10f)

        binding.pieChart.animateY(700, Easing.EaseInOutQuad)
        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.description.isEnabled = false

        binding.pieChart.legend.isEnabled = true
        binding.pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        binding.pieChart.legend.horizontalAlignment =
            Legend.LegendHorizontalAlignment.CENTER
        binding.pieChart.legend.isWordWrapEnabled = true

        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}