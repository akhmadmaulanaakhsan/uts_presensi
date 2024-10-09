package com.example.pertemuan6

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val kehadiranList = arrayOf(
            "Hadir Tepat Waktu",
            "Sakit",
            "Terlambat",
            "Izin"
        )

        with(binding){
//            Get Array
            val monthList = resources.getStringArray(R.array.month)

//            Initiate
            var selectedTime ="${timePicker.hour}:${timePicker.minute}"
            val _tempCalendar : Calendar = Calendar.getInstance()
            _tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate = "${_tempCalendar.get(Calendar.DAY_OF_MONTH)} ${monthList[_tempCalendar.get(Calendar.MONTH)]} ${_tempCalendar.get(Calendar.YEAR)}"


//            Kehadiran Dropdown=======================================
            val adapterKehadiran = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                kehadiranList
            )
            kehadiranSpinner.adapter = adapterKehadiran

            // Sembunyikan EditText awalnya
            keteranganEdittext.visibility = View.GONE

//            Selected Kehadiran
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                        ) {
                            // Tampilkan EditText jika pilihan bukan "Hadir Tepat Waktu"
                            if (position != 0) {
                                keteranganEdittext.visibility = View.VISIBLE
                            } else {
                                keteranganEdittext.visibility = View.GONE
                            }
                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            submitbutton.setOnClickListener {
                // Ambil tanggal dari CalendarView
                datepicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val months = arrayOf(
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "Agustus",
                        "September",
                        "Oktober",
                        "November",
                        "Desember"
                    )
                    selectedDate = "$dayOfMonth ${months[month]} $year"
                }

                // Ambil waktu dari Timepicker
                val selectedTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    .format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePicker.hour)
                        set(Calendar.MINUTE, timePicker.minute)
                    }.time)

                // Tampilkan pesan ke Toast
                val presensiMessage = "Presensi berhasil $selectedDate jam $selectedTime"

                Toast.makeText(
                    this@MainActivity,
                    presensiMessage,
                    Toast.LENGTH_SHORT
                ).show()
                }

        }
    }
}