package com.example.showtracker.ui.addshow

import com.example.showtracker.domain.model.Show

sealed class AddShowEvent{
    class Insert(val show: Show) : AddShowEvent()
    class Delete(val show: Show) : AddShowEvent()
    object MessageShown : AddShowEvent()
}
