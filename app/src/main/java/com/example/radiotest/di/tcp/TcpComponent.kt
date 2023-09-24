package com.example.radiotest.di.tcp

import com.example.radiotest.presentation.TcpService
import dagger.Subcomponent

@TcpScope
@Subcomponent
interface TcpComponent {
    fun inject(tcpService: TcpService)
}