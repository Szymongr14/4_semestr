cmake_minimum_required(VERSION 3.28)
project(lab_8_monitor_using_messageQueue)

set(CMAKE_CXX_STANDARD 17)

add_executable(lab_8_monitor_using_messageQueue main_magazine.c
        monitor.c
        monitor.h
        magazine.c
        magazine.h
        main.c)
